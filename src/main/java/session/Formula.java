package session;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import report.ReportParagraph;
import report.ReportTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Formula {
    private final FrameLocator frameLocator;
    private final WebclientSession webclientSession;
    private final ELOAction eloAction;
    private void selectTab(String tabName) {
        if (!tabName.equals("")) {
            System.out.println("tabname="+ tabName);
            try {
                frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
            } catch (Exception e) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + tabName + " cannot be selected</span>");
            }
        }
    }
    public void click(Locator locator) {
        try {
            BaseFunctions.click(locator);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + "Control not clickable" + "</span>");
        }
    }
    public boolean inputTextField(String name, String text) {
        try {
            BaseFunctions.type(frameLocator.locator("[name=\"" + name + "\"]"), text);
            return true;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not editable</span>");
            return false;
        }
    }
    private boolean inputCheckBox(String name, String value) {
        try {
            return BaseFunctions.select(frameLocator.locator("[name=\"" + name + "\"]"), Boolean.valueOf(value));
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not clickable</span>");
            return false;
        }
    }
    private boolean inputRadioButton(String name) {
        try {
            return BaseFunctions.check(frameLocator.getByRole(AriaRole.RADIO, new FrameLocator.GetByRoleOptions().setName(name).setExact(true)));
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not selectable</span>");
            return false;
        }
    }
    private boolean inputRedactorField(String placeHolder, String text) {
        try {
            return BaseFunctions.fillRedactorFieldByPlaceholder(frameLocator, placeHolder, text);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + placeHolder + " not editable</span>");
            return false;
        }
    }
    private boolean inputDynKwlField(String name, String text) {
        try {
            return BaseFunctions.inputDynKwlField(webclientSession.getReportParagraphs(),frameLocator, name, text);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + text + " in " + name + " not selectable</span>");
            return false;
        }
    }
    private boolean inputKwlField(String name, String text) {
        try {
            return BaseFunctions.inputKwlField(webclientSession.getReportParagraphs(),frameLocator, name, text);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + text + " in " + name + " not selectable</span>");
            return false;
        }
    }
    private void initTabPage(List<ELOControl> initTabPage) {
        for (ELOControl control: initTabPage) {
            if (control.getType() == ELOControlType.RADIO) {
                inputRadioButton(control.getSelector());
            }
        }
    }
    private boolean inputControl(ELOControl control, int tabIndex, int index) {
        boolean checkData = true;

        String value = control.getValue();

        if (control.getValue().equals("#nowdate#")) {
            Date nowDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            value = simpleDateFormat.format(nowDate);
        }

        String selector = control.getSelector();
        if (tabIndex > 0) {
            selector = selector + tabIndex;
        }
        switch(control.getType()) {
            case TEXT -> checkData = inputTextField(selector, value);
            case DYNKWL -> checkData = inputDynKwlField(selector, value);
            case CHECKBOX -> checkData =  inputCheckBox(selector, value);
            case RADIO -> checkData =  inputRadioButton(selector);
            case REDACTOR -> checkData =  inputRedactorField(selector, value);
            case KWL -> checkData =  inputKwlField(selector, value);
        }
        if(!checkData) {
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotMessageEloControlCheckData(control), BaseFunctions.getScreenShotFileName(eloAction, selector) + index + ".png");
        }
        return checkData;
    }
    private boolean inputControls(List<ELOControl> controls, int index) {
        boolean checkData = true;
        for (ELOControl control: controls) {
            if(!inputControl(control, 0, index)) {
                checkData = false;
            }
        }
        return checkData;
    }
    private void clickAddLineButton(ELOTable eloTable) {
        try {
            if (!eloTable.getSelectorTable().equals("")) {
                BaseFunctions.click(frameLocator.locator("#"+ eloTable.getSelectorTable()).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(eloTable.getAddLineButtonName())));
            } else {
                BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(eloTable.getAddLineButtonName())));
            }
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + eloTable.getAddLineButtonName() + " not clickable</span>");
        }
        BaseFunctions.sleep();
    }
    private boolean inputControlsTable(ELOTable eloTable, int index) {
        boolean checkData = true;
        int tabIndex = 1;
        for (List<ELOControl> tableLine: eloTable.getTable()) {
            if (tabIndex > 1) {
                clickAddLineButton(eloTable);
            }
            for (ELOControl control: tableLine) {
                if(!inputControl(control, tabIndex, index)) {
                    checkData = false;
                }
            }
            tabIndex++;
        }
        return checkData;
    }
    private boolean inputControlsTables(List<ELOTable> tables, int index) {
        boolean checkData = true;
        for(ELOTable eloTable: tables) {
            if(!inputControlsTable(eloTable, index )) {
                checkData = false;
            }
        }
        return checkData;
    }
    private boolean isEmptyValueControl(Locator locator) {
        String inputValue = locator.inputValue();
        return inputValue.equals("");
    }
    private boolean checkMandatoryControls() {
        boolean checkData = true;

        Locator rows = frameLocator.locator("[eloverify=\"" + "notemptyforward" + "\"]");
        int count = rows.count();

        System.out.println("*".repeat(10) + " MandatoryControls " + "*".repeat(10));
        System.out.println("rows.count(): " + count);

        List<String> tableCols = new ArrayList<>();
        tableCols.add("Feld");
        tableCols.add("Wert");
        List<List<String>> tableCells = new ArrayList<>();

        List<ReportParagraph> reportParagraphsEmptyValueControls = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " getAttribute(\"name\") " + rows.nth(i).getAttribute("name"));
            System.out.println("Row: " + i + " inputValue() " + rows.nth(i).inputValue());
            System.out.println("Row: " + i + " " + rows.nth(i));

            List<String> tableLineCells = new ArrayList<>();
            String name = rows.nth(i).getAttribute("name");
            String value = rows.nth(i).inputValue();
            if( isEmptyValueControl(rows.nth(i)) && !name.endsWith("1")) {
                tableLineCells.add("<span>" + name + "</span>");
                tableLineCells.add("<span>" + value + "</span>");
                BaseFunctions.reportMessage(reportParagraphsEmptyValueControls, "<span>MandatoryControl " + name + " not filled</span>");
                checkData = false;
            } else {
                tableLineCells.add(name);
                tableLineCells.add(value);
            }
            tableCells.add(tableLineCells);
        }
        System.out.println("*".repeat(10) + " MandatoryControls " + "*".repeat(10));

        ReportTable reportTable = new ReportTable(tableCols, tableCells);
        BaseFunctions.reportMessageAndTable(webclientSession.getReportParagraphs(), "Pflichtfelder", reportTable);
        webclientSession.getReportParagraphs().addAll(reportParagraphsEmptyValueControls);

        return checkData;
    }
    private boolean checkValueKwlField(String name, String value) {
        try {
            Locator locator = frameLocator.locator("[name=\"" + name + "\"]");
            boolean checkData = BaseFunctions.checkValueControl(locator, value);
            if(!checkData) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>Inhalt von " + name + " = " + locator.inputValue() + " stimmt nicht mit " + value +  " überein</span>");
            }
            return checkData;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not avaible</span>");
            return false;
        }
    }
    private boolean checkValueRedactorField(String name, String value) {
        try {
            Locator locator = frameLocator.locator("[name=\"" + name + "\"]");
            value = "<p>" + value + "</p>";
            boolean checkData = BaseFunctions.checkValueControl(locator, value);
            if(!checkData) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>Inhalt von " + name + "=" + locator.inputValue() + " stimmt nicht mit " + value +  " überein</span>");
            }
            return checkData;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not avaible</span>");
            return false;
        }
    }
    private boolean checkValueRadioButton(String name, String value) {
        try {
            Locator locator = frameLocator.locator("[name=\"" + name + "\"]");
            boolean checkData = BaseFunctions.checkValueRadioButton(locator, value);
            if(!checkData) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>Inhalt von " + name + " = " + locator.inputValue() + " stimmt nicht mit " + value +  " überein</span>");
            }
            return checkData;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not avaible</span>");
            return false;
        }
    }
    private boolean checkValueCheckBox(String name, String value) {
        try {
            Locator locator = frameLocator.locator("[name=\"" + name + "\"]");
            if (value.equalsIgnoreCase("true")) {
                value = "1";
            } else {
                value = "0";
            }
            boolean checkData = BaseFunctions.checkValueControl(locator, value);
            if(!checkData) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>Inhalt von " + name + " = " + locator.inputValue() + " stimmt nicht mit " + value +  " überein</span>");
            }
            return checkData;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not avaible</span>");
            return false;
        }
    }
    private boolean checkValueDynKwlField(String name, String value) {
        try {
            Locator locator = frameLocator.locator("[name=\"" + name + "\"]");
            boolean checkData = BaseFunctions.checkValueControl(locator, value);
            if(!checkData) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>Inhalt von " + name + " = " + locator.inputValue() + " stimmt nicht mit " + value +  " überein</span>");
            }
            return checkData;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not avaible</span>");
            return false;
        }
    }
    private boolean checkValueTextField(String name, String text) {
        try {
            Locator locator = frameLocator.locator("[name=\"" + name + "\"]");
            boolean checkData = BaseFunctions.checkValueControl(locator, text);
            if(!checkData) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>Inhalt von " + name + " = " + locator.inputValue() + " stimmt nicht mit " + text +  " überein</span>");
            }
            return checkData;
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not avaible</span>");
            return false;
        }
    }
    private boolean checkValueControl(ELOControl checkValueControl, int index) {
        boolean checkData = true;

        String selector = checkValueControl.getSelector();
        switch(checkValueControl.getType()) {
            case TEXT -> checkData = checkValueTextField(selector, checkValueControl.getValue());
            case DYNKWL -> checkData = checkValueDynKwlField(selector, checkValueControl.getValue());
            case CHECKBOX -> checkData =  checkValueCheckBox(selector, checkValueControl.getValue());
            case RADIO -> checkData =  checkValueRadioButton(selector, checkValueControl.getValue());
            case REDACTOR -> checkData =  checkValueRedactorField(selector, checkValueControl.getValue());
            case KWL -> checkData =  checkValueKwlField(selector, checkValueControl.getValue());
        }
        if(!checkData) {
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotMessageEloControlCheckValue(checkValueControl), BaseFunctions.getScreenShotFileName(eloAction, selector) + index + " checkValue.png");
        }
        return checkData;
    }
    private void reportCheckValueControls(List<ELOControl> checkValueControls) {
        if (checkValueControls.isEmpty()) {
            return;
        }
        List<String> tableCols = new ArrayList<>();
        tableCols.add("Feld");
        tableCols.add("aktueller Wert");
        tableCols.add("erwarteter Wert");

        List<List<String>> tableCells = new ArrayList<>();

        for (ELOControl checkValueControl : checkValueControls) {
            List<String> tableLineCells = new ArrayList<>();
            String field = checkValueControl.getSelector();
            String expValue = checkValueControl.getValue();
            String actValue;
            try {
                boolean checkData = false;
                Locator locator = frameLocator.locator("[name=\"" + field + "\"]");
                if (checkValueControl.getType() == ELOControlType.CHECKBOX) {
                    if (expValue.equalsIgnoreCase("true")) {
                        checkData = BaseFunctions.checkValueControl(locator, "1");
                    } else if (expValue.equalsIgnoreCase("false")){
                        checkData = BaseFunctions.checkValueControl(locator, "0");
                    }
                } else if (checkValueControl.getType() == ELOControlType.REDACTOR) {
                    checkData = BaseFunctions.checkValueControl(locator, "<p>" + expValue + "</p>");
                } else if (checkValueControl.getType() == ELOControlType.RADIO) {
                    checkData = BaseFunctions.checkValueRadioButton(locator, expValue);
                } else {
                    checkData = BaseFunctions.checkValueControl(locator, expValue);
                }
                actValue = "";
                if (locator.count() == 1) {
                    actValue = locator.inputValue();
                }
                if (checkValueControl.getType() == ELOControlType.CHECKBOX) {
                    if (actValue.equalsIgnoreCase("1")) {
                        actValue = "true";
                    } else {
                        actValue = "false";
                    }
                } else if (checkValueControl.getType() == ELOControlType.REDACTOR) {
                    actValue = actValue.replace("<p>", "");
                    actValue = actValue.replace("</p>", "");
                } else if (checkValueControl.getType() == ELOControlType.RADIO) {
                    int count = locator.count();
                    System.out.println("*".repeat(80));
                    System.out.println("rows.count(): " + count);
                    for (int i = 0; i < count; ++i) {
                        System.out.println("Row: " + i + " getAttribute(\"autovalidval\") " + locator.nth(i).getAttribute("autovalidval"));
                        System.out.println("Row: " + i + " textContent() " + locator.nth(i).textContent());
                        System.out.println("Row: " + i + " inputValue() " + locator.nth(i).inputValue());
                        System.out.println("Row: " + i + " innerTest() " + locator.nth(i).innerText());
                        System.out.println("Row: " + i + " innerHTML() " + locator.nth(i).innerHTML());
                        System.out.println("Row: " + i + " " + locator.nth(i));

                        String autovalidval = locator.nth(i).getAttribute("autovalidval");
                        String inputValue = locator.nth(i).inputValue();
                        if (autovalidval != null) {
                            if (autovalidval.equals(inputValue)) {
                                actValue = inputValue;
                            }
                        }
                    }
                }
                if(!checkData) {
                    tableLineCells.add("<span>" + field + "</span>");
                    tableLineCells.add("<span>" + actValue + "</span>");
                    tableLineCells.add("<span>" + expValue + "</span>");
                } else {
                    tableLineCells.add(field);
                    tableLineCells.add(actValue);
                    tableLineCells.add(expValue);
                }
            } catch (Exception e) {
                tableLineCells.add("<span>" + field + "</span>");
                tableLineCells.add("<span>" + "undefiniert" + "</span>");
                tableLineCells.add("<span>" + expValue + "</span>");
            }
            tableCells.add(tableLineCells);
        }

        ReportTable reportTable = new ReportTable(tableCols, tableCells);
        BaseFunctions.reportMessageAndTable(webclientSession.getReportParagraphs(), "Feldwertprüfung", reportTable);
    }
    private boolean checkValueControls(List<ELOControl> checkValueControls, int index) {
        reportCheckValueControls(checkValueControls);
        boolean checkData = true;
        for (ELOControl checkValueControl : checkValueControls) {
            if(!checkValueControl(checkValueControl, index)) {
                checkData = false;
            }
        }
        return checkData;
    }
    public Formula(FrameLocator frameLocator, WebclientSession webclientSession, ELOAction eloAction) {
        this.frameLocator = frameLocator;
        this.webclientSession = webclientSession;
        this.eloAction = eloAction;
    }
    public boolean inputData(List<TabPage> tabPages, int index) {
        boolean checkData = true;
        for (TabPage tabPage: tabPages) {
            System.out.println("Tabpage: " + tabPage);

            String tabName = tabPage.getTabName();

            selectTab(tabName);
            initTabPage(tabPage.getInitTabPage());
            if(!inputControls(tabPage.getControls(), index)) {
                checkData = false;
            }
            if(!inputControlsTables(tabPage.getTables(), index)) {
                checkData = false;
            }
            if(!checkValueControls(tabPage.getCheckValueControls(), index)) {
                checkData = false;
            }
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotMessageTabPage(tabName), BaseFunctions.getScreenShotFileName(eloAction, tabName) + index + ".png");
        }
        if(!checkMandatoryControls()) {
            checkData = false;
        }
        return checkData;
    }
    public void quit(String formulaSaveButton, int index) {
        BaseFunctions.sleep();
        try {
            click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(formulaSaveButton)));
        } catch (Exception e) {
            BaseFunctions.reportScreenshot(webclientSession, "<span>" + "Formular cannot be saved" + "</span>", BaseFunctions.getScreenShotFileName(eloAction, "Formula") + index + ".png");
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + "Formular cannot be saved" + "</span>");
        }
    }
    @Override
    public String toString() {
        return "Formula{" +
                "frameLocator=" + frameLocator +
                '}';
    }
}