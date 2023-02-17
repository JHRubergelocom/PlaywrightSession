package session;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;

public class Formula {
    private final FrameLocator frameLocator;
    public Formula(FrameLocator frameLocator) {
        this.frameLocator = frameLocator;
    }
    public void selectTab(String tabName) {
        if (!tabName.equals("")) {
            System.out.println("tabname="+ tabName);
            frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
        }
    }
    public void save(String formulaSaveButton) {
        BaseFunctions.sleep();
        click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(formulaSaveButton)));
    }
    public void click(Locator locator) {
        BaseFunctions.click(locator);
    }
    public void inputTextField(String name, String text, boolean timeout) {
        BaseFunctions.type(frameLocator.locator("[name=\"" + name + "\"]"), text, timeout);
    }
    private void inputCheckBox(String name, String value) {
        BaseFunctions.select(frameLocator.locator("[name=\"" + name + "\"]"), Boolean.valueOf(value));
    }
    private void inputRadioButton(String name) {
        BaseFunctions.check(frameLocator.getByRole(AriaRole.RADIO, new FrameLocator.GetByRoleOptions().setName(name)));
    }
    private void inputRedactorField(String placeHolder, String text) {
        BaseFunctions.fillRedactorFieldByPlaceholder(frameLocator, placeHolder, text);
    }
    private void inputKwlField(String selector, String text) {
        BaseFunctions.selectkwlitem(frameLocator, frameLocator.locator("[inpname=\"" + selector + "\"]"), text);
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
        if (!eloTable.getSelectorTable().equals("")) {
            BaseFunctions.click(frameLocator.locator("#"+ eloTable.getSelectorTable()).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(eloTable.getAddLineButtonName())));
        } else {
            BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(eloTable.getAddLineButtonName())));
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
        }
    }
    @Override
    public String toString() {
        return "Formula{" +
                "frameLocator=" + frameLocator +
                '}';
    }
}