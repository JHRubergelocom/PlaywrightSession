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
    public WebclientSession(ELOSolutionArchiveData eloSolutionArchiveData) {
        this.selectorSolutionTile = eloSolutionArchiveData.getSelectorSolutionTile();
        this.selectorSolutionsFolder = eloSolutionArchiveData.getSelectorSolutionsFolder();
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
    public void login(LoginData loginData) {
        Login login = new Login(this, loginData.getStack(), loginData.getTextUserName().getSelector(), loginData.getTextPassword().getSelector(), loginData.getButtonLogin().getSelector());
        login.typeUsername(loginData.getTextUserName().getValue());
        login.typePassword(loginData.getTextPassword().getValue());
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

    private void startFormula(ELOActionDef eloActionDef) {
        selectRibbonMenu(eloActionDef.getSelectorRibbon());
        selectRibbonMenu(eloActionDef.getSelectorMenu());
        BaseFunctions.sleep();
        selectButton(eloActionDef.getSelectorButton());
        BaseFunctions.sleep();

    }

    public void executeAction(ELOAction eloAction,
                              Map<String, TabPage> tabPages) {

        selectSolutionsFolder();
        startFormula(eloAction.getEloActionDef());

        System.out.println("eloAction.getEloActionDef() " + eloAction.getEloActionDef());
        FrameLocator frameLocator = getFrameLocator();
        Formula formula = new Formula(frameLocator);
        formula.inputData(tabPages);
        formula.save();
        BaseFunctions.sleep();
    }
    private void selectSolutionTile() {
        BaseFunctions.click(page.locator(selectorSolutionTile));
    }
    private void selectSolutionsFolder() {
        BaseFunctions.selectByTextAttribute(page, selectorSolutionsFolder, "class", "color");
    }
    private void selectRibbonMenu(String selectorRibbonMenu){
        BaseFunctions.selectByTextAttribute(page, selectorRibbonMenu, "id", "button");
    }
    private void selectButton(String selectorButton){
        BaseFunctions.selectByTextAttribute(page, selectorButton, "id", "comp");
    }


    public void close() {
        playwright.close();
    }

    @Override
    public String toString() {
        return "WebclientSession{" +
                "page=" + page +
                ", playwright=" + playwright +
                ", selectorSolutionTile='" + selectorSolutionTile + '\'' +
                ", selectorSolutionsFolder='" + selectorSolutionsFolder + '\'' +
                '}';
    }
}
