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

    public void selectTab(String tabName, AssignmentStatus assignment) {
        if (!tabName.equals("")) {
            frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
        }
        selectAssignment(assignment);
    }

    public void save() {
        BaseFunctions.sleep();
        click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("OK")));
    }


    public void click(Locator locator) {
        BaseFunctions.click(locator);
    }

    public void inputTextField(String name, String text, boolean timeout) {
        BaseFunctions.type(frameLocator.locator("[name=\"" + name + "\"]"), text, timeout);
    }

    private void inputCheckBox(String name, Boolean value) {
        BaseFunctions.select(frameLocator.locator("[name=\"" + name + "\"]"), value);
    }

    public void inputTextFields(Map<String, String> fields) {
        for (Map.Entry<String,String> entry: fields.entrySet()) {
            inputTextField(entry.getKey(), entry.getValue(), false);
        }
    }

    private void inputCheckBoxes(Map<String, Boolean> checkboxes) {
        for (Map.Entry<String,Boolean> entry: checkboxes.entrySet()) {
            inputCheckBox(entry.getKey(), entry.getValue());
        }
    }



    public void inputTextFieldTable(List<Map<String, String>> table, String addLineButtonName) {
        int index = 1;
        for (Map<String,String> tableLine: table) {
            if (index > 1) {
                click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(addLineButtonName)));
                BaseFunctions.sleep();
            }
            for (Map.Entry<String,String> entry: tableLine.entrySet()) {
                inputTextField(entry.getKey() + index, entry.getValue(), true);
            }
            index++;
        }
    }

    private void selectAssignment(AssignmentStatus assignment) {
        switch (assignment) {
            case MEETING -> {
                BaseFunctions.click(frameLocator.locator("xpath=//*[@id=\"part_550_toggle_assignment\"]/tr[4]/td[2]/div/input[2]"));
            }
        }
        System.out.println("selectassigment assignment" + assignment);
    }


    public void inputData(Map<String, TabPage> tabpages) {
        for (Map.Entry<String,TabPage> entry: tabpages.entrySet()) {
            // System.out.println("Key Tabpage: " + entry.getKey());
            // System.out.println("Value Tabpage: " + entry.getValue());

            String tabName = entry.getKey();
            TabPage tabPage = entry.getValue();

            selectTab(tabName, tabPage.getAssignment());
            inputTextFields(tabPage.getFields());
            inputTextFieldTable(tabPage.getTable(), tabPage.getAddLineButtonName());
            inputCheckBoxes(tabPage.getCheckboxes());
        }
    }

}
