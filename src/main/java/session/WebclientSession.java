package session;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import de.elo.ix.client.IXConnection;
import eloix.ELOIxConnection;
import eloix.RepoUtils;
import report.HtmlReport;
import report.ReportData;
import report.ReportParagraph;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WebclientSession {
    private Page page;
    private final Playwright playwright;
    private BrowserContext browserContext;
    private final String selectorSolutionTile;
    private final String selectorSolutionsFolder;
    private final List<ReportParagraph> reportParagraphs;
    public Page getPage() {
        return page;
    }
    public List<ReportParagraph> getReportParagraphs() {
        return reportParagraphs;
    }
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
    private void setPlaywrightConfigParameter(PlaywrightConfig playwrightConfig) {

        final BrowserType chromium = playwright.chromium();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        if (playwrightConfig.isNotHeadless()) {
            launchOptions.setHeadless(false);
        }

        final Browser browser = launch(chromium, launchOptions);

        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
        if (playwrightConfig.isRecordVideo()) {
            newContextOptions.setRecordVideoDir(Paths.get(""));
        }

        browserContext = createContext(browser, newContextOptions);

        Tracing.StartOptions startOptions = new Tracing.StartOptions();
        if (playwrightConfig.isScreenShots()) {
            startOptions.setScreenshots(true);
        }
        if (playwrightConfig.isSnapShots()) {
            startOptions.setSnapshots(true);
        }
        if (playwrightConfig.isSources()) {
            startOptions.setSources(true);
        }

        page = startContextPage(browserContext, startOptions);

    }
    private WebclientSession(PlaywrightConfig playwrightConfig, ELOSolutionArchiveData eloSolutionArchiveData) {
        this.selectorSolutionTile = eloSolutionArchiveData.getSelectorSolutionTile();
        this.selectorSolutionsFolder = eloSolutionArchiveData.getSelectorSolutionsFolder();
        this.reportParagraphs = new ArrayList<>();

        playwright = Playwright.create();
        setPlaywrightConfigParameter(playwrightConfig);
    }
    private void click(Locator locator) {
        try {
            BaseFunctions.click(locator);
        } catch (Exception e) {
            BaseFunctions.reportMessage(reportParagraphs, "<span>" + "Control not clickable" + "</span>");
        }
    }
    private void type(Locator locator, String text) {
        try {
            BaseFunctions.type(locator, text, false);
        } catch (Exception e) {
            BaseFunctions.reportMessage(reportParagraphs, "<span>" + "Control not clickable" + "</span>");
        }
    }
    private void login(LoginData loginData) {
        page.navigate("http://" + loginData.getStack() + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        Locator locator = page.locator("[name=\"" + "locale" + "\"]");
        locator.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Deutsch")).click();
        type(page.getByPlaceholder(loginData.getTextUserName().getSelector()), loginData.getTextUserName().getValue());
        type(page.getByPlaceholder(loginData.getTextPassword().getSelector()), loginData.getTextPassword().getValue());

        BaseFunctions.reportScreenshot(this, "Login Webclient", "loginwebclient.png");
        click(page.getByText(loginData.getButtonLogin().getSelector()));

        selectSolutionTile();
    }
    private FrameLocator getFrameLocator(String frameName) {
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
    private void startSelectionDialogItem(ELOAction eloAction) {
        if (!eloAction.getSelectionDialogItem().equals("")) {
            System.out.println("selectionDialogItem = "+ eloAction.getSelectionDialogItem());
            BaseFunctions.sleep();
            BaseFunctions.reportScreenshot(this, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, "selectionDialogItem") + ".png");
            page.getByText(eloAction.getSelectionDialogItem()).click();
        }
    }
    private Optional<FrameLocator> startExternalFormular(ELOAction eloAction) {
        System.out.println("eloActionDef " + eloAction.getEloActionDef());
        System.out.println("selectionDialogItem " + eloAction.getSelectionDialogItem());
        BaseFunctions.sleep();
        if (!selectRibbon(eloAction)) {
            return Optional.empty();
        }
        BaseFunctions.sleep();
        if(!selectMenu(eloAction)) {
            return Optional.empty();
        }

        BaseFunctions.sleep();
        if (!selectButton(eloAction)) {
            return Optional.empty();
        }

        BaseFunctions.sleep();
        startSelectionDialogItem(eloAction);
        BaseFunctions.sleep();

        return  Optional.of(getFrameLocator("iframe"));
    }
    private Optional<FrameLocator> startViewerFormular(ELOAction eloAction) {
        BaseFunctions.sleep();
        Locator rows = page.locator("xpath=//*[@class=\"x-btn-button\"]");
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        if (count == 0) {
            return Optional.empty();
        }
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).textContent().equals("Formular")) {
                rows.nth(i).click();
            }
        }
        startSelectionDialogItem(eloAction);
        BaseFunctions.sleep();

        return Optional.of(getFrameLocator("FormularViewer"));
    }
    private Optional<FrameLocator> startFormula(ELOAction eloAction) {
        switch (eloAction.getFormulaType()) {
            case EXTERNAL -> { return startExternalFormular(eloAction);}
            case VIEWER -> { return startViewerFormular(eloAction);}
        }
        return Optional.empty();
    }
    private void executeAction(ELOAction eloAction,
                               Map<String, TabPage> tabPages) {
        selectSolutionsFolder();
        if(selectEntryByPath(eloAction.getEntryPath())) {
            Optional<FrameLocator> frameLocatorOptional = startFormula(eloAction);
            if (frameLocatorOptional.isPresent()) {
                Formula formula = new Formula(frameLocatorOptional.get(), this, eloAction);
                formula.inputData(tabPages);
                formula.save(eloAction.getFormulaSaveButton());
            }
        }
        BaseFunctions.sleep();
    }
    private void selectSolutionTile() {
        BaseFunctions.click(page.locator(selectorSolutionTile));
    }
    private void selectSolutionsFolder() {
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, selectorSolutionsFolder, "class", "color");
        optionalLocator.ifPresent(Locator::click);
    }
    private Optional<Locator> selectFolder(String folder) {
        return BaseFunctions.selectByTextAttribute(page, folder, "class", "color");
    }
    private boolean selectEntryByPath(String path) {
        if (path.equals("")) {
            return true;
        }
        try {
            String[] folders = path.split("/");
            if(folders.length > 0) {
                if (selectFolder(folders[0]).isPresent()) {
                    for (int i = 0; i < folders.length-1; i++) {
                        BaseFunctions.sleep();
                        if (selectFolder(folders[i+1]).isEmpty()) {
                            try {
                                if(selectFolder(folders[i]).isPresent()) {
                                    selectFolder(folders[i]).get().dblclick();
                                    System.out.println("selectFolder parentfolder " + folders[i] + " dblclick");
                                } else {
                                    BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectFolder parentfolder " + folders[i] + " not available" + "</span>");
                                    return false;
                                }
                            } catch (Exception e) {
                                BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectFolder parentfolder " + folders[i] + " not available" + "</span>");
                                return false;
                            }
                        }
                        try {
                            selectFolder(folders[i+1]).get().dblclick();
                            System.out.println("selectFolder folder " + folders[i+1] + " dblclick");
                        } catch (Exception e) {
                            BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectFolder folder " + folders[i+1] + " not available" + "</span>");
                            return false;
                        }
                    }
                } else {
                    System.err.println("selectorFolder folder=" + folders[0] + " of path=" + path + "is not available");
                    BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectorFolder folder=" + folders[0] + " of path=" + path + "is not available" + "</span>");
                    return false;
                }
            } else {
                System.err.println("selectorFolder path=" + path + "is empty");
                BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectorFolder path=" + path + "is empty" + "</span>");
                return false;
            }
        } catch (Exception e) {
            BaseFunctions.reportMessage(reportParagraphs, "<span>" + e.getMessage() + "</span>");
            return false;
        }
        return true;
    }
    private boolean selectRibbon(ELOAction eloAction){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, eloAction.getEloActionDef().getSelectorRibbon(), "id", "button");
        if(optionalLocator.isPresent()) {
            BaseFunctions.reportScreenshot(this, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, "Ribbon") + ".png");
            optionalLocator.get().click();
            return true;
        }
        BaseFunctions.reportMessage(reportParagraphs, "<span>Ribbon " + eloAction.getEloActionDef().getSelectorRibbon() + " nicht gefunden!</span>");
        return false;
    }
    private boolean selectMenu(ELOAction eloAction){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, eloAction.getEloActionDef().getSelectorMenu(), "id", "button");
        if (optionalLocator.isPresent()) {
            BaseFunctions.reportScreenshot(this, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, "Menu") + ".png");
            optionalLocator.get().click();
            return true;
        }
        BaseFunctions.reportMessage(reportParagraphs, "<span>Menu " + eloAction.getEloActionDef().getSelectorMenu() + " nicht gefunden!</span>");
        return false;
    }
    private boolean selectButton(ELOAction eloAction){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, eloAction.getEloActionDef().getSelectorButton(), "id", "comp");
        if (optionalLocator.isPresent()) {
            BaseFunctions.reportScreenshot(this, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, "Button") + ".png");
            optionalLocator.get().click();
            return true;
        }
        BaseFunctions.reportMessage(reportParagraphs, "<span>Button " + eloAction.getEloActionDef().getSelectorButton() + " nicht gefunden!</span>");
        return false;
    }
    private void close() {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace_" + browserContext.browser().browserType().name() + ".zip")));
        browserContext.close();
        playwright.close();
    }
    private void deleteData(DataConfig dataConfig) throws Exception {
        // Ix Connect
        IXConnection ixConn = ELOIxConnection.getIxConnection(dataConfig.getLoginData());
        System.out.println("IxConn: " + ixConn);

        // Delete arcpath
        for (String arcPath: dataConfig.getEloDeleteData().getArcPaths()) {
            RepoUtils.DeleteSord(ixConn, arcPath);
        }

        ixConn.close();
    }
    private static void showReport(List<ReportParagraph> reportParagraphs) {
        ReportData reportData = new ReportData("Report Test", reportParagraphs);
        String htmlDoc = HtmlReport.createReport(reportData);
        HtmlReport.showReport(htmlDoc);
    }
    public static void execute(String jsonDataConfigFile, String jsonPlaywrightConfigFile) {
        WebclientSession ws = null;
        List<ReportParagraph> reportParagraphs = new ArrayList<>();
        try {
            final DataConfig dataConfig = BaseFunctions.readDataConfig(jsonDataConfigFile);
            final PlaywrightConfig playwrightConfig = BaseFunctions.readPlaywrightConfig(jsonPlaywrightConfigFile);

            // Execute DataConfig
            ws = new WebclientSession(playwrightConfig, dataConfig.getEloSolutionArchiveData());
            ws.login(dataConfig.getLoginData());
            for (ELOAction eloAction: dataConfig.getEloActionData().getEloActions()) {
                Map<String, TabPage> tabPages = eloAction.getTabPages();

                // Execute Action
                ws.executeAction(eloAction, tabPages);
            }

            if (playwrightConfig.isPause()) {
                ws.getPage().pause();
            }

            // Delete Data
            List<String> arcPaths = dataConfig.getEloDeleteData().getArcPaths();
            if(arcPaths.size() > 0) {
                ws.deleteData(dataConfig);
            }
            BaseFunctions.reportMessage(ws.getReportParagraphs(), "Test war erfolgreich!");

        } catch ( Exception e) {
            System.err.println(e.getMessage());
            if (ws != null) {
                BaseFunctions.reportScreenshot(ws,"<span>" + e.getMessage() + "</span>", "exeception.png");
                BaseFunctions.reportMessage(ws.getReportParagraphs(), "<span>Test ist fehlgeschlagen!</span>");
            } else {
                BaseFunctions.reportMessage(reportParagraphs, "<span>" + e.getMessage() + "</span>");
                BaseFunctions.reportMessage(reportParagraphs, "<span>Test ist fehlgeschlagen!</span>");
            }
        } finally {
            if (ws != null) {
                showReport(ws.getReportParagraphs());
                ws.close();
            } else {
                showReport(reportParagraphs);
            }
        }
    }
    @Override
    public String toString() {
        return "WebclientSession{" +
                "page=" + page +
                ", playwright=" + playwright +
                ", selectorSolutionTile='" + selectorSolutionTile + '\'' +
                ", selectorSolutionsFolder='" + selectorSolutionsFolder + '\'' +
                '}';
    }
}
