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
    public Formula(FrameLocator frameLocator, WebclientSession webclientSession, ELOAction eloAction) {
        this.frameLocator = frameLocator;
        this.webclientSession = webclientSession;
        this.eloAction = eloAction;
    }
    public void selectTab(String tabName) {
        if (!tabName.equals("")) {
            System.out.println("tabname="+ tabName);
            try {
                frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
            } catch (Exception e) {
                BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + tabName + " cannot be selected</span>");
            }
        }
    }
    public void save(String formulaSaveButton) {
        BaseFunctions.sleep();
        try {
            click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(formulaSaveButton)));
        } catch (Exception e) {
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotFileName(eloAction, ""), BaseFunctions.getScreenShotFileName(eloAction, "Formula") + ".png");
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + "Formular cannot be saved" + "</span>");
        }
    }
    public void click(Locator locator) {
        try {
            BaseFunctions.click(locator);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + "Control not clickable" + "</span>");
        }
    }
    public void inputTextField(String name, String text, boolean timeout) {
        try {
            BaseFunctions.type(frameLocator.locator("[name=\"" + name + "\"]"), text, timeout);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not editable</span>");
        }
    }
    private void inputCheckBox(String name, String value) {
        try {
            BaseFunctions.select(frameLocator.locator("[name=\"" + name + "\"]"), Boolean.valueOf(value));
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not clickable</span>");
        }
    }
    private void inputRadioButton(String name) {
        try {
            BaseFunctions.check(frameLocator.getByRole(AriaRole.RADIO, new FrameLocator.GetByRoleOptions().setName(name)));
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + name + " not selectable</span>");
        }
    }
    private void inputRedactorField(String placeHolder, String text) {
        try {
            BaseFunctions.fillRedactorFieldByPlaceholder(frameLocator, placeHolder, text);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + placeHolder + " not editable</span>");
        }
    }
    private void inputKwlField(String selector, String text) {
        try {
            BaseFunctions.selectkwlitem(frameLocator, frameLocator.locator("[inpname=\"" + selector + "\"]"), text);
        } catch (Exception e) {
            BaseFunctions.reportMessage(webclientSession.getReportParagraphs(), "<span>" + selector + " or" + text + " not selectable</span>");
        }
    }
    private void initTabPage(List<ELOControl> initTabPage) {
        for (ELOControl control: initTabPage) {
            if (control.getType() == ELOControlType.RADIO) {
                inputRadioButton(control.getSelector());
            }
        }
    }
    private void inputControls(List<ELOControl> controls) {
        for (ELOControl control: controls) {
            switch(control.getType()) {
                case TEXT -> inputTextField(control.getSelector(), control.getValue(), false);
                case DYNKWL -> inputTextField(control.getSelector(), control.getValue(), true);
                case CHECKBOX -> inputCheckBox(control.getSelector(), control.getValue());
                case RADIO -> inputRadioButton(control.getSelector());
                case REDACTOR -> inputRedactorField(control.getSelector(), control.getValue());
                case KWL -> inputKwlField(control.getSelector(), control.getValue());
            }
        }
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
    private void inputControlsTable(ELOTable eloTable) {
        int index = 1;
        for (List<ELOControl> tableLine: eloTable.getTable()) {
            if (index > 1) {
                clickAddLineButton(eloTable);
            }
            for (ELOControl control: tableLine) {
                switch(control.getType()) {
                    case TEXT -> inputTextField(control.getSelector() + index, control.getValue(), false);
                    case DYNKWL -> inputTextField(control.getSelector() + index, control.getValue(), true);
                    case CHECKBOX -> inputCheckBox(control.getSelector() + index, control.getValue());
                    case RADIO -> inputRadioButton(control.getSelector() + index);
                    case REDACTOR -> inputRedactorField(control.getSelector() + index, control.getValue());
                    case KWL -> inputKwlField(control.getSelector() + index, control.getValue());
                }
            }
            index++;
        }
    }
    private void inputControlsTables(List<ELOTable> tables) {
        for(ELOTable eloTable: tables) {
            inputControlsTable(eloTable);
        }
    }
    public void inputData(Map<String, TabPage> tabpages) {
        for (Map.Entry<String,TabPage> entry: tabpages.entrySet()) {
            System.out.println("Key Tabpage: " + entry.getKey());
            System.out.println("Value Tabpage: " + entry.getValue());

            String tabName = entry.getKey();
            TabPage tabPage = entry.getValue();

            selectTab(tabName);
            initTabPage(tabPage.getInitTabPage());
            inputControls(tabPage.getControls());
            inputControlsTables(tabPage.getTables());
            BaseFunctions.reportScreenshot(webclientSession, BaseFunctions.getScreenShotFileName(eloAction, tabName), BaseFunctions.getScreenShotFileName(eloAction, tabName) + ".png");
        }
    }
    @Override
    public String toString() {
        return "Formula{" +
                "frameLocator=" + frameLocator +
                '}';
    }
}