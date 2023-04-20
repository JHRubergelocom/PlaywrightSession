package session;

import com.google.gson.Gson;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import report.ReportParagraph;
import report.ReportTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BaseFunctions {
    private static final long millis = 5000;
    public static String getTimeStamp() {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String value = simpleDateFormat.format(nowDate);

        System.out.println("Zeitstempel: " + value);
        return value;
    }
    public static String getTestReportDir() {
        return "testreport_" + getTimeStamp() + "/";
    }
    public static void sleep() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static Locator type(Locator textbox, String inputText) {
        textbox.click();
        textbox.clear();
        textbox.fill(inputText);
        textbox.press("Tab");
        return textbox;
    }
    private static void selectkwlitem(FrameLocator frameLocator, Locator kwllist, String kwlitem) {
        kwllist.click();
        frameLocator.getByRole(AriaRole.CELL, new FrameLocator.GetByRoleOptions().setName(kwlitem)).click();
    }
    public static void click(Locator locator) {
        locator.click();
    }
    public static boolean select(Locator checkbox, Boolean value) {
        if (value) {
            if (!checkbox.isChecked()) {
                checkbox.click();
            }
        } else {
            if (checkbox.isChecked()) {
                checkbox.click();
            }
        }
        return true;
    }
    public static boolean check(Locator radiobutton) {
        radiobutton.check();
        return true;
    }
    public static Optional<Locator> selectByTextAttribute(Page page, String text, String attributeKey, String attributeValue) {
        Locator rows = page.getByText(text, new Page.GetByTextOptions().setExact(true));
        int count = rows.count();
        /*
        if (!matchexact) {
            if (count == 0) {
                rows = page.getByText(text, new Page.GetByTextOptions());
                count = rows.count();
            }
        }
         */
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " innerText() " + rows.nth(i).innerText());
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
    public static Optional<Locator> selectByTextAttributeNotExact(Page page, String text, String attributeKey, String attributeValue) {
        Locator rows = page.getByText(text, new Page.GetByTextOptions());
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " innerText() " + rows.nth(i).innerText());
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
    public static boolean fillRedactorFieldByPlaceholder(FrameLocator frameLocator, String placeHolder, String text) {
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
                    return true;
                }
            }
        }
        System.err.println("fillRedactorFieldByPlaceholder: " + placeHolder + " nicht gefunden!");
        return false;
    }
    private static ReportTable getDynKwlTable(FrameLocator frameLocator) {
        Locator rows = frameLocator.locator("xpath=//*[@class=\"" + "dynamicswl" +  "\"]");
        int count = rows.count();

        System.out.println("-".repeat(100));
        System.out.println("rows.count(): " + count);

        List<String> tableCols = new ArrayList<>();
        List<List<String>> tableCells = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            String textContent = rows.nth(i).textContent();
            String innerHTML = rows.nth(i).innerHTML();
            String innerText = rows.nth(i).innerText();

            System.out.println("Row: " + i + " textContent() " + textContent);
            System.out.println("Row: " + i + " innerHTML() " + innerHTML);
            System.out.println("Row: " + i + " innerText() " + innerText);

            String[] lines = innerText.split("\n");
            boolean firstLine = true;
            for (String line: lines) {
                List<String> tableLineCells = new ArrayList<>();
                String[] cells = line.split("\t");
                for (String cell: cells) {
                    if (firstLine) {
                        tableCols.add(cell);
                    } else {
                        tableLineCells.add(cell);
                    }
                }
                if (!firstLine) {
                    tableCells.add(tableLineCells);
                }
                firstLine = false;
            }
        }
        System.out.println("-".repeat(100));

        return new ReportTable(tableCols, tableCells);

    }
    private static ReportTable getEloUserTable(FrameLocator frameLocator, String autocomplete) {
        Locator rows = frameLocator.locator("xpath=//*[@id=\"" + "sel" + autocomplete +  "\"]").locator("xpath=//*[@class=\"afnormitem\"]");
        int count = rows.count();

        System.out.println("-".repeat(100));
        System.out.println("rows.count(): " + count);

        List<String> tableCols = new ArrayList<>();
        List<List<String>> tableCells = new ArrayList<>();

        tableCols.add("ELO User / Groups");
        for (int i = 0; i < count; ++i) {
            String textContent = rows.nth(i).textContent();
            String innerHTML = rows.nth(i).innerHTML();
            String innerText = rows.nth(i).innerText();

            System.out.println("Row: " + i + " textContent() " + textContent);
            System.out.println("Row: " + i + " innerHTML() " + innerHTML);
            System.out.println("Row: " + i + " innerText() " + innerText);

            List<String> tableLineCells = new ArrayList<>();
            tableLineCells.add(innerText);
            tableCells.add(tableLineCells);
        }
        System.out.println("-".repeat(100));

        return new ReportTable(tableCols, tableCells);

    }
    private static Locator getKeywordList(FrameLocator frameLocator) {
        Locator keywordlist = frameLocator.locator("xpath=//*[@class=\"keywordlist\"]");
        System.out.println("+".repeat(100));
        System.out.println("keywordlist.textContent() " + keywordlist.textContent());
        System.out.println("keywordlist.innerHTML() " +  keywordlist.innerHTML());
        System.out.println("keywordlist " +  keywordlist);
        System.out.println("+".repeat(100));
        return keywordlist;
    }
    private static boolean checkDynKwlTable(List<ReportParagraph> reportParagraphs, ReportTable reportTable,  String text) {
        if (reportTable.getTableCells().isEmpty()) {
            reportMessage(reportParagraphs, "<span>" + text + " not selectable in dynkwl</span>");
            return false;
        } else if (reportTable.getTableCells().size() > 1) {
            reportMessageAndTable(reportParagraphs, "<span>" + text + " ambiguous in dynkwl</span>", reportTable);
            return false;
        }
        return true;
    }
    private static boolean checkKwlTable(List<ReportParagraph> reportParagraphs, ReportTable reportTable, String text) {
        boolean checkOK = false;
        for (List<String> tableLineCells: reportTable.getTableCells()) {
            for (String cell: tableLineCells) {
                if (cell.equals(text)) {
                    checkOK = true;
                    break;
                }
            }
        }
        if (!checkOK) {
            reportMessage(reportParagraphs, "<span>" + text + " not selectable in kwl</span>");
        }
        return checkOK;
    }
    public static boolean inputDynKwlField(List<ReportParagraph> reportParagraphs, FrameLocator frameLocator, String name, String text) {
        Locator locator = type(frameLocator.locator("[name=\"" + name + "\"]"), text);
        sleep();

        String autocomplete = locator.getAttribute("id");
        System.out.println("autocomplete = " + autocomplete);

        Locator keywordlist = getKeywordList(frameLocator);

        String innerHTML = keywordlist.innerHTML();
        if (innerHTML.contains("dynamicswl")) {
            ReportTable reportTable = getDynKwlTable(frameLocator);
            return checkDynKwlTable(reportParagraphs, reportTable, text);
        } else {
            ReportTable reportTable = getEloUserTable(frameLocator, autocomplete);
            return checkKwlTable(reportParagraphs, reportTable, text);
        }
    }
    public static boolean inputKwlField(List<ReportParagraph> reportParagraphs, FrameLocator frameLocator, String name, String text) {
        selectkwlitem(frameLocator, frameLocator.locator("[inpname=\"" + name + "\"]"), text);
        ReportTable reportTable = getDynKwlTable(frameLocator);
        return checkKwlTable(reportParagraphs, reportTable, text);
    }
    public static boolean checkValueEqualControl(Locator control, String value) {
        String inputValue = control.inputValue();
        return inputValue.equals(value);
    }
    public static boolean checkValueExistControl(Locator control, ELOControlType type) {
        String inputValue = control.inputValue();
        if (type == ELOControlType.REDACTOR) {
            return !inputValue.equals("<p></p>");
        }
        return !inputValue.isEmpty();
    }
    public static boolean checkValueRadioButton(Locator rows, String value) {
        int count = rows.count();
        System.out.println("*".repeat(80));
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " getAttribute(\"autovalidval\") " + rows.nth(i).getAttribute("autovalidval"));
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " inputValue() " + rows.nth(i).inputValue());
            System.out.println("Row: " + i + " innerTest() " + rows.nth(i).innerText());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));

            String autovalidval = rows.nth(i).getAttribute("autovalidval");
            String inputValue = rows.nth(i).inputValue();
            if (autovalidval != null) {
                if (autovalidval.equals(inputValue)) {
                    if (value.equals(inputValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
    public static String getScreenShotMessageEloControlCheckData(ELOControl control) {
        return "<span>CheckData von " + control.getSelector() + " " + control.getValue() + " fehlgeschlagen</span>";
    }
    public static String getScreenShotMessageEloControlCheckValue(ELOCheckValueControl checkValueControl) {
        return "<span>CheckValue von " + checkValueControl.getSelector() + " " + checkValueControl.getValue() + " fehlgeschlagen</span>";
    }
    public static String getScreenShotMessageTabPage(String tabName) {
        return "TabPage " + tabName;
    }
    public static void reportScreenshot(WebclientSession webclientSession,  String message, String screenshot) {
        webclientSession.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(webclientSession.getReportPath() + screenshot)));

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
    public static void reportMessageAndTable(List<ReportParagraph> reportParagraphs, String message, ReportTable reportTable) {

        List<String> reportHeader = new ArrayList<>();
        reportHeader.add(message);

        List<String> reportText = new ArrayList<>();

        ReportParagraph reportParagraph = new ReportParagraph(reportHeader, reportText, reportTable);

        reportParagraphs.add(reportParagraph);
    }
}
