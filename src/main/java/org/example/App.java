package org.example;

import session.*;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /*
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(5000));
            Page page = browser.newPage();
            page.navigate("http://playwright.dev");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
            System.out.println(page.title());
        }
        */

        // Read DataConfig
        final DataConfig dataConfig = BaseFunctions.readDataConfig("DataConfig.json");

        // Execute DataConfig
        WebclientSession ws = new WebclientSession(dataConfig.getEloSolutionArchiveData());
        ws.login(dataConfig.getLoginData());

        for (ELOAction eloAction: dataConfig.getEloActionData().getEloActions()) {
            Map<String, TabPage> tabPages = eloAction.getTabPages();

            // Execute Action
            ws.executeAction(eloAction, tabPages);
        }

        ws.getPage().pause();
        ws.close();

    }
}
