package session;

import com.microsoft.playwright.Locator;

public class BaseFunctions {
    private static final long millis = 3000;

    public static void sleep() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void type(Locator textbox, String inputText, boolean timeout) {
        textbox.click();
        textbox.clear();
        textbox.fill(inputText);
        textbox.press("Tab");
        if (timeout) {
            sleep();
        }
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
