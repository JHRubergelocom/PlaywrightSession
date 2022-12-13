package session;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Formula {
    private final Action action;

    public Formula(Action action) {
        this.action = action;
    }

    public void selectTab(String tabName, String startElement, AssignmentStatus assignment) {
        if (!tabName.equals("")) {
            action.getFrameLocator().getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
        }
        selectAssignment(assignment);
        System.out.println("ZZZZ BaseFunctions.clickable(frame, startElement) " + startElement + " ZZZZ");
    }

    public void save() {
        action.getFrameLocator().getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("OK")).click();
    }


    public void click(Locator locator) {
        BaseFunctions.click(locator);
    }

    public void inputTextField(String name, String text) {
        BaseFunctions.type(action.getFrameLocator().locator("[name=\"" + name + "\"]"), text);
    }

    private void inputCheckBox(String name, Boolean value) {
        BaseFunctions.select(action.getFrameLocator().locator("[name=\"" + name + "\"]"), value);
    }

    public void inputTextFields(Map<String, String> fields) {
        for (Map.Entry<String,String> entry: fields.entrySet()) {
            inputTextField(entry.getKey(), entry.getValue());
        }
    }

    private void inputCheckBoxes(Map<String, Boolean> checkboxes) {
        for (Map.Entry<String,Boolean> entry: checkboxes.entrySet()) {
            inputCheckBox(entry.getKey(), entry.getValue());
        }
    }



    public void inputTextFieldTable(List<Map<String, String>> table, String addLineButtonXpath) {
        int index = 1;
        for (Map<String,String> tableLine: table) {
            if (index > 1) {
                click(action.getFrameLocator().locator("xpath="+ addLineButtonXpath));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (Map.Entry<String,String> entry: tableLine.entrySet()) {
                inputTextField(entry.getKey() + index, entry.getValue());
            }
            index++;
        }
    }

    private String getStartElement(TabPage tabPage) {
        String startElement = "";

        if(tabPage.getFields().size() > 0) {
            Optional<String> firstKey = tabPage.getFields().keySet().stream().findFirst();
            if (firstKey.isPresent()) {
                startElement = firstKey.get();
            }
        } else if(tabPage.getTable().size() > 0) {
            Optional<Map<String, String>> firstElement = tabPage.getTable().stream().findFirst();
            if (firstElement.isPresent()) {
                Optional<String> firstKey = firstElement.get().keySet().stream().findFirst();
                if (firstKey.isPresent()) {
                    startElement = firstKey.get();
                    startElement = startElement + "1";
                }
            }
        } else if(tabPage.getCheckboxes().size() > 0) {
            Optional<String> firstKey = tabPage.getCheckboxes().keySet().stream().findFirst();
            if (firstKey.isPresent()) {
                startElement = firstKey.get();
            }
        }
        return startElement;
    }

    private void selectAssignment(AssignmentStatus assignment) {
        switch (assignment) {
            case MEETING -> {
                BaseFunctions.click(action.getFrameLocator().locator("xpath=//*[@id=\"part_550_toggle_assignment\"]/tr[4]/td[2]/div/input[2]"));
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

            String startElement = getStartElement(tabPage);
            selectTab(tabName, startElement, tabPage.getAssignment());
            inputTextFields(tabPage.getFields());
            inputTextFieldTable(tabPage.getTable(), tabPage.getAddLineButtonXpath());
            inputCheckBoxes(tabPage.getCheckboxes());

        }
    }

}
