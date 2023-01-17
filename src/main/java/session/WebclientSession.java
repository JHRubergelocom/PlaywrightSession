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
    public FrameLocator getFrameLocator() {
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
                              ELOActionFormulaData eloActionFormulaData,
                              ELOActionDef eloActionDef) {
        Action action = new Action(this,
                eloActionDef);
        action.startFormula();
        System.out.println("actionName " +actionName);
        FrameLocator frameLocator = getFrameLocator();
        Formula formula = new Formula(frameLocator, eloActionFormulaData.getSelectorAssignmentMeeting(), eloActionFormulaData.getSelectorAssignmentPool());
        formula.inputData(tabPages);
        formula.save();
        BaseFunctions.sleep();
    }
    private void selectSolutionTile() {
        BaseFunctions.click(page.locator(selectorSolutionTile));
    }
    public void  selectSolutionsFolder() {
        Locator rows = page.getByText(selectorSolutionsFolder);

        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).isVisible()) {
                System.out.println("      Row: " + i + "getAttribute(\"class\") " + rows.nth(i).getAttribute("class"));
                System.out.println("      Row: " + i + "click() " + rows.nth(i));

                if (rows.nth(i).getAttribute("class").contains("color")) {
                    BaseFunctions.click(rows.nth(i));
                    break;
                }
            }
        }
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
