package session;

import com.microsoft.playwright.Locator;

public class BaseFunctions {

    public static void type(Locator textbox, String inputText) {
        textbox.click();
        textbox.clear();
        textbox.fill(inputText);
        textbox.press("Tab");
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
}
