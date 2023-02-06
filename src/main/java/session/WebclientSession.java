package session;

import com.microsoft.playwright.*;

import java.util.Map;
import java.util.Optional;

public class WebclientSession {
    private final Page page;
    private final Playwright playwright;
    private final String selectorSolutionTile;
    private final String selectorSolutionsFolder;

    private Page getPage() {
        return page;
    }

    private WebclientSession(ELOSolutionArchiveData eloSolutionArchiveData) {
        this.selectorSolutionTile = eloSolutionArchiveData.getSelectorSolutionTile();
        this.selectorSolutionsFolder = eloSolutionArchiveData.getSelectorSolutionsFolder();
        playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();
    }

    private void click(Locator locator) {
        BaseFunctions.click(locator);
    }

    private void type(Locator locator, String text) {
        BaseFunctions.type(locator, text, false);
    }

    private void login(LoginData loginData) {
        page.navigate("http://" + loginData.getStack() + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        type(page.getByPlaceholder(loginData.getTextUserName().getSelector()), loginData.getTextUserName().getValue());
        type(page.getByPlaceholder(loginData.getTextPassword().getSelector()), loginData.getTextPassword().getValue());
        click(page.getByText(loginData.getButtonLogin().getSelector()));

        selectSolutionTile();
    }

    private FrameLocator getFrameLocator() {
        String selector = "";
        getPage().mainFrame().content();
        for (Frame frame : getPage().frames()) {
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
        BaseFunctions.sleep();
        selectRibbonMenu(eloActionDef.getSelectorRibbon());
        BaseFunctions.sleep();
        selectRibbonMenu(eloActionDef.getSelectorMenu());
        BaseFunctions.sleep();
        selectButton(eloActionDef.getSelectorButton());
        BaseFunctions.sleep();

    }

    private void executeAction(ELOAction eloAction,
                               Map<String, TabPage> tabPages) {

        selectSolutionsFolder();
        // TODO Get Frame External Formula or Viewer Formular

        startFormula(eloAction.getEloActionDef());

        System.out.println("eloAction.getEloActionDef() " + eloAction.getEloActionDef());
        FrameLocator frameLocator = getFrameLocator();
        Formula formula = new Formula(frameLocator);
        formula.inputData(tabPages);
        formula.save(eloAction.getFormulaSaveButton());
        BaseFunctions.sleep();
    }

    private void selectSolutionTile() {
        BaseFunctions.click(page.locator(selectorSolutionTile));
    }
    private void selectSolutionsFolder() {
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, selectorSolutionsFolder, "class", "color");
        optionalLocator.ifPresent(Locator::click);
    }
    private Optional<Locator> selectFolder(String folder) {
        return BaseFunctions.selectByTextAttribute(page, folder, "class", "color");
    }
    private void selectEntryByPath(String path) {
        String[] folders = path.split("/");

        if(folders.length > 0) {
            if (selectFolder(folders[0]).isPresent()) {
                if (selectFolder(folders[0]).get().isVisible()) {
                    for (int i = 0; i < folders.length-1; i++) {
                        BaseFunctions.sleep();
                        if (selectFolder(folders[i+1]).isPresent()) {
                            if (!selectFolder(folders[i+1]).get().isVisible()) {
                                if (selectFolder(folders[i]).isPresent()) {
                                    selectFolder(folders[i]).get().dblclick();
                                    System.out.println("selectFolder parentfolder " + folders[i] + " dblclick");
                                }
                            }
                            selectFolder(folders[i+1]).get().dblclick();
                            System.out.println("selectFolder folder " + folders[i+1] + " dblclick");
                        }
                    }
                } else {
                    System.err.println("selectorFolder folder=" + folders[0] + " of path=" + path + "is not available");
                }
            } else {
                System.err.println("selectorFolder folder=" + folders[0] + " of path=" + path + "is not available");
            }
        } else {
            System.err.println("selectorFolder path=" + path + "is empty");
        }
    }
    private void selectRibbonMenu(String selectorRibbonMenu){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, selectorRibbonMenu, "id", "button");
        optionalLocator.ifPresent(Locator::click);
    }
    private void selectButton(String selectorButton){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, selectorButton, "id", "comp");
        optionalLocator.ifPresent(Locator::click);
    }
    private void close() {
        playwright.close();
    }
    public static void execute(String jsonFile, boolean setPause) {
        final DataConfig dataConfig = BaseFunctions.readDataConfig(jsonFile);

        // Execute DataConfig
        WebclientSession ws = new WebclientSession(dataConfig.getEloSolutionArchiveData());
        ws.login(dataConfig.getLoginData());

        for (ELOAction eloAction: dataConfig.getEloActionData().getEloActions()) {
            Map<String, TabPage> tabPages = eloAction.getTabPages();

            // Execute Action
            ws.executeAction(eloAction, tabPages);
        }

        if (setPause) {
            ws.getPage().pause();
        }
        ws.close();
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
