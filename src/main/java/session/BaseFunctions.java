package session;

import com.google.gson.Gson;
import com.microsoft.playwright.Locator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    public static DataConfig readDataConfig(String jsonFileName) {
        Gson gson = new Gson();
        DataConfig dataConfig;

        System.out.println("Reading " + jsonFileName);
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader(jsonFileName))) {
            dataConfig = gson.fromJson(br, DataConfig.class);
            System.out.println(dataConfig);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(100));
        return dataConfig;
    }
}
