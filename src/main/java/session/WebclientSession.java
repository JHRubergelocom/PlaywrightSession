package session;

import com.microsoft.playwright.*;

import java.util.Map;

public class WebclientSession {
    private final Page page;
    private final Playwright playwright;
    private final String selectorSolutionTile;
    private final String selectorSolutionsFolder;
    public Page getPage() {
        return page;
    }
    public WebclientSession(String selectorSolutionTile, String selectorSolutionsFolder) {
        this.selectorSolutionTile = selectorSolutionTile;
        this.selectorSolutionsFolder = selectorSolutionsFolder;
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
        BaseFunctions.type(locator, text, false);
    }

    public void login(String stack, String userName, String password, String selectorUsername, String selectorPassword, String selectorLoginButton) {
        Login login = new Login(this, stack, selectorUsername, selectorPassword, selectorLoginButton);
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

    public void executeAction(String actionName,
                              Map<String, TabPage> tabPages,
                              String selectorAssignmentMeeting,
                              String selectorAssignmentPool,
                              String selectorRibbonNew,
                              String selectorMenuMeeting,
                              String selectorMenuMeetingPremium,
                              String selectorButtonCreateMeetingBoard,
                              String selectorButtonCreateMeetingBoardPremium,
                              String selectorButtonCreateMeeting,
                              String selectorButtonCreateMeetingPremium,
                              String selectorButtonCreateMeetingItem,
                              String selectorButtonCreateMeetingItemPremium,
                              String selectorButtonCreateMeetingItemPool,
                              String selectorButtonCreateMeetingItemPoolPremium) {
        Action action = new Action(this,
                                    actionName,
                                    selectorRibbonNew,
                                    selectorMenuMeeting,
                                    selectorMenuMeetingPremium,
                                    selectorButtonCreateMeetingBoard,
                                    selectorButtonCreateMeetingBoardPremium,
                                    selectorButtonCreateMeeting,
                                    selectorButtonCreateMeetingPremium,
                                    selectorButtonCreateMeetingItem,
                                    selectorButtonCreateMeetingItemPremium,
                                    selectorButtonCreateMeetingItemPool,
                                    selectorButtonCreateMeetingItemPoolPremium);
        action.startFormula();
        System.out.println("actionName " +actionName);
        FrameLocator frameLocator = getFrameLocator();
        Formula formula = new Formula(frameLocator, selectorAssignmentMeeting, selectorAssignmentPool);
        formula.inputData(tabPages);
        formula.save();
        BaseFunctions.sleep();
    }

    private void selectSolutionTile() {

        BaseFunctions.click(page.locator(selectorSolutionTile));
    }

    public void  selectSolutionsFolder() {
        BaseFunctions.click(page.locator(selectorSolutionsFolder));
    }

    public void close() {
        playwright.close();
    }

}
