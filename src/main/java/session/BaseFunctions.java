package session;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BaseFunctions {

    public static void type(Locator locator, String inputText) {
        Locator element = locator;
        element.clear();
        element.fill(inputText);
        element.press("Tab");
    }

    public static void click(Locator locator) {
        locator.click();
    }

    public static void select(Locator locator, Boolean value) {
        Locator checkbox = locator.getByRole(AriaRole.CHECKBOX);
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

    public static void clickable(Page page, String name) {
        page.locator("[name=" + name + "]").isVisible();
    }

}
