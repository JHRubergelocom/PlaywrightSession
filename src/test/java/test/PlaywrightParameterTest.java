package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import report.ReportParagraph;
import session.BaseFunctions;
import session.PlaywrightConfig;
import session.WebclientSession;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaywrightParameterTest {
    private Browser launch(BrowserType browserType, BrowserType.LaunchOptions options) {
        return browserType.launch(options);
    }
    private BrowserContext createContext(Browser browser, Browser.NewContextOptions options) {
        return browser.newContext(options);
    }
    private Page startContextPage(BrowserContext browserContext, Tracing.StartOptions startOptions) {
        browserContext.tracing().start(startOptions);
        return browserContext.newPage();
    }
    private FrameLocator getFrameLocator(Page page, String frameName) {
        String selector = "";
        page.mainFrame().content();
        for (Frame frame: page.frames()) {
            System.out.println("Frame.name " + frame.name());
            if (frame.name().contains(frameName)) {
                selector = "#" + frame.name();
            }
        }
        FrameLocator frameLocator = page.frameLocator(selector);
        System.out.println("selector " + selector);
        System.out.println("frameLocator " + frameLocator);
        return frameLocator;

    }
    private void selectTab(FrameLocator frameLocator, String tabName) {
        if (!tabName.equals("")) {
            System.out.println("tabname="+ tabName);
            frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
        }
    }
    public void clickButton(FrameLocator frameLocator, String name) {
        BaseFunctions.sleep();
        BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(name)));
    }
    private Locator selectFolder(Page page, String folder) {
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, folder, "class", "color");
        return optionalLocator.orElseGet(() -> page.locator(""));
    }
    private void selectEntryByPath(Page page, String path) {
        String[] folders = path.split("/");

        if(folders.length > 0) {
            if (selectFolder(page, folders[0]).isVisible()) {
                for (int i = 0; i < folders.length-1; i++) {
                    BaseFunctions.sleep();
                    if (!selectFolder(page, folders[i+1]).isVisible()) {
                        selectFolder(page, folders[i]).dblclick();
                        System.out.println("selectFolder parentfolder " + folders[i] + " dblclick");
                    }
                    selectFolder(page, folders[i+1]).dblclick();
                    System.out.println("selectFolder folder " + folders[i+1] + " dblclick");

                }
            } else {
                System.err.println("selectorFolder folder=" + folders[0] + " of path=" + path + "is not available");
            }
        } else {
            System.err.println("selectorFolder path=" + path + "is empty");
        }
    }
    private void clickAddLineButton(FrameLocator frameLocator, String selectorTable, String addLineButtonName) {
        if (!selectorTable.equals("")) {
            BaseFunctions.click(frameLocator.locator("#"+ selectorTable).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(addLineButtonName)));
        } else {
            BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(addLineButtonName)));
        }
        BaseFunctions.sleep();
    }
    private void executePage(Page page) {
        page.navigate("http://" + "ruberg-hr.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        Locator locator = page.locator("[name=\"" + "locale" + "\"]");
        locator.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Deutsch")).click();
        page.getByPlaceholder("Name").fill("0");
        page.getByPlaceholder("Passwort").fill("elo");
        page.getByText("Anmelden").click();

        // Kachel "Solutions" klicken
        page.locator("xpath=//*[@title=\"Solutions\"]").click();

        // Ordner "Solutions" auswählen
        selectEntryByPath(page,"Solutions");

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Button "Neuer Mitarbeiter" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neuer Mitarbeiter", "id", "comp");
        optionalLocator.ifPresent(Locator::click);

        // Get Frame External Formula
        BaseFunctions.sleep();
        FrameLocator frameLocator = getFrameLocator(page,"iframe");
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_FIRSTNAME" + "\"]"), "Hans");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_LASTNAME" + "\"]"), "Hansen");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_PERSONNELNO" + "\"]"), "12345");

        List<ReportParagraph> reportParagraphs = new ArrayList<>();
        BaseFunctions.inputDynKwlField(reportParagraphs, frameLocator, "IX_GRP_HR_PERSONNEL_ELOUSERID", "Jan Eichner");
        BaseFunctions.inputDynKwlField(reportParagraphs, frameLocator, "IX_GRP_HR_PERSONNEL_RESPONSIBLE", "Bodo Kraft");
        BaseFunctions.inputDynKwlField(reportParagraphs, frameLocator, "IX_GRP_HR_PERSONNEL_SUPERIOR", "Gerd Baum");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_DATEOFJOINING" + "\"]"), "20220101");

        // Formular speichern
        clickButton(frameLocator, "OK");

        // Ordner "Solutions" auswählen
        selectEntryByPath(page,"Solutions");

        // Ordner "Hans Hansens" auswählen
        selectEntryByPath(page,"Solutions/Personalmanagement/Personalakten/H/Hansen, Hans");

        // Viewer Formular auswählen
        System.out.println("*".repeat(10) + " Viewer Formular auswählen " + "*".repeat(10));

        Locator rows = page.locator("xpath=//*[@class=\"x-btn-button\"]");
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).textContent().equals("Formular")) {
                rows.nth(i).click();
            }
        }
        System.out.println("*".repeat(10) + " Viewer Formular auswählen " + "*".repeat(10));

        // FrameLocator Viewer Formular
        frameLocator = getFrameLocator(page,"FormularViewer");

        // Tabpage auswählen
        String tabName = "Persönlich";
        selectTab(frameLocator, tabName);

        // Beschreibung eintragen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_TITLE" + "\"]"), "Doktor H");

        // Tabellenfelder füllen
        String addLineButtonName = "Eintrag hinzufügen";
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_FIRSTNAME1" + "\"]"), "Hugo");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_LASTNAME1" + "\"]"), "Egon");

        clickAddLineButton(frameLocator, "part_240_family_members", addLineButtonName);

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_FIRSTNAME2" + "\"]"), "Sandra");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_LASTNAME2" + "\"]"), "Renz");


        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_FIRSTNAME1" + "\"]"), "Heinrich");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_LASTNAME1" + "\"]"), "Müller");

        clickAddLineButton(frameLocator, "part_250_emergency_contacts", "Eintrag hinzufügen");

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_FIRSTNAME2" + "\"]"), "Sebastian");
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_LASTNAME2" + "\"]"), "Schulz");

        // Stichwortlistenfeld Geschlecht füllen
        System.out.println("*".repeat(10) + " Stichwortlistenfeld Geschlecht füllen " + "*".repeat(10));

        locator = frameLocator.locator("[inpname=\"" + "IX_GRP_HR_PERSONNEL_GENDER" + "\"]");
        locator.click();
        frameLocator.getByRole(AriaRole.CELL, new FrameLocator.GetByRoleOptions().setName("Männlich")).click();

        System.out.println("*".repeat(10) + " Stichwortlistenfeld Geschlecht füllen " + "*".repeat(10));

        // Speichern
        clickButton(frameLocator, "Speichern");

        // page.pause(); // Start Codegen

    }
    private void closeContext(BrowserContext browserContext) {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get(BaseFunctions.getReportPath() + "trace_" + browserContext.browser().browserType().name() + ".zip")));
        browserContext.close();
    }
    private PlaywrightConfig createPlaywrightConfig() {
        // Playwright Config Parameter

        final boolean notHeadless = true;
        final boolean recordVideo = true;
        final boolean screenShots = true;
        final boolean snapShots = true;
        final boolean sources = true;
        final boolean pause = true;

        return new PlaywrightConfig(notHeadless, recordVideo, screenShots, snapShots, sources, pause);

    }
    @ParameterizedTest
    @ValueSource(strings = {"PlaywrightConfig.json"})
    public void CreatePlaywrightConfigJson(String jsonFile) {
        // Create PlaywrightConfig
        final PlaywrightConfig playwrightConfig = createPlaywrightConfig();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(playwrightConfig);
        System.out.println(json);

        // Save DataConfig
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read PlaywrightConfig
        gson = new Gson();

        System.out.println("Reading " + jsonFile);
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            PlaywrightConfig playwrightConfig1 = gson.fromJson(br, PlaywrightConfig.class);
            System.out.println(playwrightConfig1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(100));
    }
    @Test
    public void testPlaywrightParameter() {
        Playwright playwright = Playwright.create();

        BrowserType browserType = playwright.chromium();
        // BrowserType browserType = playwright.firefox();
        // BrowserType browserType = playwright.webkit();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);

        Browser browser = launch(browserType, launchOptions);

        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
        newContextOptions.setRecordVideoDir(Paths.get(BaseFunctions.getReportPath()));

        BrowserContext browserContext = createContext(browser, newContextOptions);

        Tracing.StartOptions startOptions = new Tracing.StartOptions();
        startOptions.setScreenshots(true)
                .setSnapshots(true)
                .setSources(true);

        Page page = startContextPage(browserContext, startOptions);
        executePage(page);

        closeContext(browserContext);

        playwright.close();
    }
    @ParameterizedTest
    @ValueSource(strings = {"DataConfigHr.json"})
    public void TestSession(String jsonFile) {
        WebclientSession.execute(jsonFile, "PlaywrightConfig.json");
    }
}
