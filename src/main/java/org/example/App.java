package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import session.*;

import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(5000));
            Page page = browser.newPage();
            page.navigate("http://playwright.dev");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
            System.out.println(page.title());
        }

        // Create DataConfig
        // final DataConfig dataConfig = createDataConfig();
        final DataConfig dataConfig = BaseFunctions.readDataConfig("DataConfigTest.json");

        // Execute DataConfig
        WebclientSession ws = new WebclientSession(dataConfig.getEloSolutionArchiveData());
        ws.login(dataConfig.getLoginData());

        for (SortedMap.Entry<Integer, ELOAction> entryEloAction: dataConfig.getEloActionData().getEloActions().entrySet()) {
            ELOAction eloAction = entryEloAction.getValue();
            String actionName = eloAction.getActionName();
            Map<String, TabPage> tabPages = eloAction.getTabPages();

            // Get Action Definition
            ELOActionDef eloActionDef = dataConfig.getEloActionDefData().getEloActionDefs().get(actionName);

            // Execute Action
            ws.selectSolutionsFolder();
            ws.executeAction(actionName, tabPages, dataConfig.getEloActionFormularData(), eloActionDef);
        }

        ws.getPage().pause();
        ws.close();

    }
}
