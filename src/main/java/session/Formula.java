package session;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Formula {
    private Page page;
    private String frameSelector;

    public Formula(Page page, String frameSelector) {
        this.page = page;
        this.frameSelector = frameSelector;
    }

    public void selectTab(String tabName, String startElement, AssignmentStatus assignment) {
        if (!tabName.equals("")) {
            page.frame(frameSelector).getByRole(AriaRole.LINK, new Frame.GetByRoleOptions().setName("Mitglieder")).click();
        }
        selectAssignment(assignment);
        BaseFunctions.clickable(page, startElement);
        System.out.println("ZZZZ BaseFunctions.clickable(frame, startElement) " + startElement + " ZZZZ");
    }

    public void save() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK")).click();
    }


    public void click(Locator locator) {
        BaseFunctions.click(locator);
    }

    public void inputTextField(String name, String text) {
        BaseFunctions.type(page.locator("[name=" + name + "]"), text);
    }

    private void inputCheckBox(String name, Boolean value) {
        BaseFunctions.select(page.locator("[name=" + name + "]"), value);
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
                click(page.locator("xpath="+ addLineButtonXpath));
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
                BaseFunctions.click(page.locator("xpath=//*[@id=\"part_550_toggle_assignment\"]/tr[4]/td[2]/div/input[2]"));
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
