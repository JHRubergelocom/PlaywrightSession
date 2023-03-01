package session;

import com.google.gson.Gson;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import report.ReportParagraph;
import report.ReportTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseFunctions {
    private static final long millis = 5000;
    public static void sleep() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void type(Locator textbox, String inputText, boolean timeout) {
        textbox.click();
        textbox.clear();
        textbox.fill(inputText);
        textbox.press("Tab");
        if (timeout) {
            sleep();
        }
    }
    public static void selectkwlitem(FrameLocator frameLocator, Locator kwllist, String kwlitem) {
        kwllist.click();
        frameLocator.getByRole(AriaRole.CELL, new FrameLocator.GetByRoleOptions().setName(kwlitem)).click();
    }
    public static void click(Locator locator) {
        locator.click();
    }
    public static void select(Locator checkbox, Boolean value) {
        if (value) {
            if (!checkbox.isChecked()) {
                checkbox.click();
            }
        } else {
            if (checkbox.isChecked()) {
                checkbox.click();
            }
        }
    }
    public static void check(Locator radiobutton) {
        radiobutton.check();
    }
    public static Optional<Locator> selectByTextAttribute(Page page, String text, String attributeKey, String attributeValue) {
        Locator rows = page.getByText(text, new Page.GetByTextOptions().setExact(true));
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).isVisible()) {
                System.out.println("      Row: " + i + "getAttribute(" + attributeKey + ") " + rows.nth(i).getAttribute(attributeKey));
                if (rows.nth(i).getAttribute(attributeKey).contains(attributeValue)) {
                    return Optional.of(rows.nth(i));
                }
            }
        }
        System.err.println("selectByTextAttribute: " + text + " nicht gefunden!");
        return Optional.empty();
    }
    public static void fillRedactorFieldByPlaceholder(FrameLocator frameLocator, String placeHolder, String text) {
        Locator rows = frameLocator.getByPlaceholder(placeHolder);
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));

            if (rows.nth(i).isVisible()) {
                if (rows.nth(i).innerHTML().contains("<p></p>")) {
                    rows.nth(i).fill(text);
                    return;
                }
            }
        }
        System.err.println("fillRedactorFieldByPlaceholder: " + placeHolder + " nicht gefunden!");
    }
    public static DataConfig readDataConfig(String jsonDataConfigFileName) {
        Gson gson = new Gson();
        DataConfig dataConfig;

        System.out.println("Reading " + jsonDataConfigFileName);
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader(jsonDataConfigFileName))) {
            dataConfig = gson.fromJson(br, DataConfig.class);
            System.out.println(dataConfig);
        } catch ( Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(100));
        return dataConfig;
    }
    public static PlaywrightConfig readPlaywrightConfig(String jsonPlaywrightConfigFileName) {
        Gson gson = new Gson();
        PlaywrightConfig playwrightConfig;

        System.out.println("Reading " + jsonPlaywrightConfigFileName);
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader(jsonPlaywrightConfigFileName))) {
            playwrightConfig = gson.fromJson(br, PlaywrightConfig.class);
            System.out.println(playwrightConfig);
        } catch ( Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(100));
        return playwrightConfig;

    }
    public static String getScreenShotFileName(ELOAction eloAction, String tabName) {
        if (eloAction.getFormulaType() == FormulaType.VIEWER) {
            return eloAction.getEntryPath().replace("/", " ") + " " + tabName;
        }
        return eloAction.getEloActionDef().getSelectorRibbon() + " " +
                eloAction.getEloActionDef().getSelectorMenu() + " " +
                eloAction.getEloActionDef().getSelectorButton() + " " +
                eloAction.getSelectionDialogItem() + " " +
                tabName;
    }

    public static void reportScreenshot(WebclientSession webclientSession,  String message, String screenshot) {
        webclientSession.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshot)));

        List<String> reportHeader = new ArrayList<>();
        reportHeader.add(message);

        List<String> reportText = new ArrayList<>();
        reportText.add("<img src=\"" + screenshot + "\" alt=\"" + screenshot + " is missing\" loading=\"lazy\">");

        ReportTable reportTable = new ReportTable(new ArrayList<>(), new ArrayList<>());
        ReportParagraph reportParagraph = new ReportParagraph(reportHeader, reportText, reportTable);

        webclientSession.getReportParagraphs().add(reportParagraph);
    }
    public static void reportMessage(List<ReportParagraph> reportParagraphs, String message) {

        List<String> reportHeader = new ArrayList<>();
        reportHeader.add(message);

        List<String> reportText = new ArrayList<>();

        ReportTable reportTable = new ReportTable(new ArrayList<>(), new ArrayList<>());
        ReportParagraph reportParagraph = new ReportParagraph(reportHeader, reportText, reportTable);

        reportParagraphs.add(reportParagraph);
    }

}
