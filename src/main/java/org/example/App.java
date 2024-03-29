package org.example;

import session.WebclientSession;

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
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(BaseFunctions.getReportPath() + "example.png")));
            System.out.println(page.title());
        }
        */
        String jsonDataConfigFile = args[0];
        String jsonPlaywrightConfigFile = args[1];
        WebclientSession.execute(jsonDataConfigFile, jsonPlaywrightConfigFile);
    }
}
