package session;

import com.microsoft.playwright.*;

import java.util.Map;

public class WebclientSession {
    private final Page page;
    private final Playwright playwright;

    public Page getPage() {
        return page;
    }

    public WebclientSession() {
        playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();
    }

    public void visit(String url) {
        page.navigate(url);
    }

    public void click(Locator locator) {
        BaseFunctions.click(locator);
    }

    public void type(Locator locator, String text) {
        BaseFunctions.type(locator, text);
    }

    public void login(String stack, String userName, String password) {
        Login login = new Login(this, stack);
        login.typeUsername(userName);
        login.typePassword(password);
        login.clickLoginButton();
        selectSolutionTile();
    }

    private FrameLocator getFrameLocator() {
        String selector = "";
        getPage().mainFrame().content();
        for (Frame frame: getPage().frames()) {
            System.out.println("Frame.name " + frame.name());
            if (frame.name().contains("iframe")) {
                selector = "#" + frame.name();
            }
        }
        FrameLocator frameLocator = getPage().frameLocator(selector);
        System.out.println("selector " + selector);
        System.out.println("frameLocator " + frameLocator);

        return frameLocator;
    }

    public void executeAction(String actionName, Map<String, TabPage> tabPages) {
        Action action = new Action(this, actionName);
        action.startFormula();
        System.out.println("actionName " +actionName);
        FrameLocator frameLocator = getFrameLocator();
        Formula formula = new Formula(frameLocator);
        formula.inputData(tabPages);
        formula.save();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void selectSolutionTile() {

        BaseFunctions.click(page.locator("xpath=//*[@id=\"tile-1013\"]"));
    }

    public void  selectSolutionsFolder() {
        BaseFunctions.click(page.locator("xpath=//*[@id=\"treeview-1061-record-1\"]"));
    }

    public void close() {
        playwright.close();
    }

}
