package session;

import com.google.gson.Gson;
import com.microsoft.playwright.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;

public class BaseFunctions {
    private static final long millis = 5000;
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
    public static void check(Locator radiobutton) {
        radiobutton.check();
    }
    public static Optional<Locator> selectByTextAttribute(Page page, String text, String attributeKey, String attributeValue) {
        Locator rows = page.getByText(text, new Page.GetByTextOptions().setExact(true));
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).isVisible()) {
                System.out.println("      Row: " + i + "getAttribute(" + attributeKey + ") " + rows.nth(i).getAttribute(attributeKey));
                if (rows.nth(i).getAttribute(attributeKey).contains(attributeValue)) {
                    return Optional.of(rows.nth(i));
                }
            }
        }
        System.err.println("selectByTextAttribute: " + text + " nicht gefunden!");
        return Optional.empty();
    }
    public static void fillRedactorFieldByPlaceholder(FrameLocator frameLocator, String placeHolder, String text) {
        Locator rows = frameLocator.getByPlaceholder(placeHolder);
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));

            if (rows.nth(i).isVisible()) {
                if (rows.nth(i).innerHTML().contains("<p></p>")) {
                    rows.nth(i).fill(text);
                    return;
                }
            }
        }
        System.err.println("fillRedactorFieldByPlaceholder: " + placeHolder + " nicht gefunden!");
    }
    public static DataConfig readDataConfig(String jsonFileName) {
        Gson gson = new Gson();
        DataConfig dataConfig = new DataConfig();

        System.out.println("Reading " + jsonFileName);
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader(jsonFileName))) {
            dataConfig = gson.fromJson(br, DataConfig.class);
            System.out.println(dataConfig);
        } catch ( Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("-".repeat(100));
        return dataConfig;
    }
}
