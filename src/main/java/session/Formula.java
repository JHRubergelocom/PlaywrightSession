package session;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;

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
            return BaseFunctions.check(frameLocator.getByRole(AriaRole.RADIO, new FrameLocator.GetByRoleOptions().setName(name)));
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
    private boolean inputDynKwlField(String name, String text, boolean checkValue) {
        try {
            return BaseFunctions.inputDynKwlField(webclientSession.getReportParagraphs(),frameLocator, name, text, checkValue);
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
    private boolean inputControl(ELOControl control, int index) {
        boolean checkData = true;

        String selector = control.getSelector();
        if (index > 0) {
            selector = selector + index;
        }
        switch(control.getType()) {
            case TEXT -> checkData = inputTextField(selector, control.getValue());
            case DYNKWL -> checkData = inputDynKwlField(selector, control.getValue(), control.isCheckValue());
            case CHECKBOX -> checkData =  inputCheckBox(selector, control.getValue());
            case RADIO -> checkData =  inputRadioButton(selector);
            case REDACTOR -> checkData =  inputRedactorField(selector, control.getValue());
            case KWL -> checkData =  inputKwlField(selector, control.getValue());
        }
        if(!checkData) {
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, selector) + ".png");
        }
        return checkData;
    }
    private boolean inputControls(List<ELOControl> controls) {
        boolean checkData = true;
        for (ELOControl control: controls) {
            if(!inputControl(control, 0)) {
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
    private boolean inputControlsTable(ELOTable eloTable) {
        boolean checkData = true;
        int index = 1;
        for (List<ELOControl> tableLine: eloTable.getTable()) {
            if (index > 1) {
                clickAddLineButton(eloTable);
            }
            for (ELOControl control: tableLine) {
                if(!inputControl(control, index)) {
                    checkData = false;
                }
            }
            index++;
        }
        return checkData;
    }
    private boolean inputControlsTables(List<ELOTable> tables) {
        boolean checkData = true;
        for(ELOTable eloTable: tables) {
            if(!inputControlsTable(eloTable)) {
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
    public boolean inputData(Map<String, TabPage> tabpages) {
        boolean checkData = true;
        for (Map.Entry<String,TabPage> entry: tabpages.entrySet()) {
            System.out.println("Key Tabpage: " + entry.getKey());
            System.out.println("Value Tabpage: " + entry.getValue());

            String tabName = entry.getKey();
            TabPage tabPage = entry.getValue();

            selectTab(tabName);
            initTabPage(tabPage.getInitTabPage());
            if(!inputControls(tabPage.getControls())) {
                checkData = false;
            }
            if(!inputControlsTables(tabPage.getTables())) {
                checkData = false;
            }
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotFileName(eloAction, tabName), BaseFunctions.getScreenShotFileName(eloAction, tabName) + ".png");
        }
        return checkData;
    }
    public void quit(String formulaSaveButton) {
        BaseFunctions.sleep();
        try {
            click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(formulaSaveButton)));
        } catch (Exception e) {
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, "Formula") + ".png");
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