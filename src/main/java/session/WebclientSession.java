package session;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

public class WebclientSession {
    private Page page;
    private final Playwright playwright;
    private BrowserContext browserContext;
    private final String selectorSolutionTile;
    private final String selectorSolutionsFolder;
    private Page getPage() {
        return page;
    }
    private Browser launch(BrowserType browserType, BrowserType.LaunchOptions options) {
        return browserType.launch(options);
    }
    private BrowserContext createContext(Browser browser, Browser.NewContextOptions options) {
        return browser.newContext(options);
    }
    private Page startContextPage(BrowserContext browserContext, Tracing.StartOptions startOptions) {
        browserContext.tracing().start(startOptions);
        return browserContext.newPage();
    }
    private void setPlaywrightConfigParameter(PlaywrightConfig playwrightConfig) {

        final BrowserType chromium = playwright.chromium();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        if (playwrightConfig.isNotHeadless()) {
            launchOptions.setHeadless(false);
        }

        final Browser browser = launch(chromium, launchOptions);

        Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
        if (playwrightConfig.isRecordVideo()) {
            newContextOptions.setRecordVideoDir(Paths.get(""));
        }

        browserContext = createContext(browser, newContextOptions);

        Tracing.StartOptions startOptions = new Tracing.StartOptions();
        if (playwrightConfig.isScreenShots()) {
            startOptions.setScreenshots(true);
        }
        if (playwrightConfig.isSnapShots()) {
            startOptions.setSnapshots(true);
        }
        if (playwrightConfig.isSources()) {
            startOptions.setSources(true);
        }

        page = startContextPage(browserContext, startOptions);

    }
    private WebclientSession(PlaywrightConfig playwrightConfig, ELOSolutionArchiveData eloSolutionArchiveData) {
        this.selectorSolutionTile = eloSolutionArchiveData.getSelectorSolutionTile();
        this.selectorSolutionsFolder = eloSolutionArchiveData.getSelectorSolutionsFolder();

        playwright = Playwright.create();
        setPlaywrightConfigParameter(playwrightConfig);
    }
    private void click(Locator locator) {
        BaseFunctions.click(locator);
    }
    private void type(Locator locator, String text) {
        BaseFunctions.type(locator, text, false);
    }
    private void login(LoginData loginData) {
        page.navigate("http://" + loginData.getStack() + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        Locator locator = page.locator("[name=\"" + "locale" + "\"]");
        locator.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Deutsch")).click();
        type(page.getByPlaceholder(loginData.getTextUserName().getSelector()), loginData.getTextUserName().getValue());
        type(page.getByPlaceholder(loginData.getTextPassword().getSelector()), loginData.getTextPassword().getValue());
        click(page.getByText(loginData.getButtonLogin().getSelector()));

        selectSolutionTile();
    }
    private FrameLocator getFrameLocator(String frameName) {
        String selector = "";
        page.mainFrame().content();
        for (Frame frame: page.frames()) {
            System.out.println("Frame.name " + frame.name());
            if (frame.name().contains(frameName)) {
                selector = "#" + frame.name();
            }
        }
        FrameLocator frameLocator = page.frameLocator(selector);
        System.out.println("selector " + selector);
        System.out.println("frameLocator " + frameLocator);
        return frameLocator;
    }
    private void startSelectionDialogItem(String selectionDialogItem) {
        if (!selectionDialogItem.equals("")) {
            System.out.println("selectionDialogItem = "+ selectionDialogItem);
            BaseFunctions.sleep();
            page.getByText(selectionDialogItem).click();
        }

    }
    private FrameLocator startExternalFormular(ELOAction eloAction) {
        System.out.println("eloActionDef " + eloAction.getEloActionDef());
        System.out.println("selectionDialogItem " + eloAction.getSelectionDialogItem());
        BaseFunctions.sleep();
        selectRibbonMenu(eloAction.getEloActionDef().getSelectorRibbon());
        BaseFunctions.sleep();
        selectRibbonMenu(eloAction.getEloActionDef().getSelectorMenu());
        BaseFunctions.sleep();
        selectButton(eloAction.getEloActionDef().getSelectorButton());
        BaseFunctions.sleep();
        startSelectionDialogItem(eloAction.getSelectionDialogItem());
        BaseFunctions.sleep();

        return getFrameLocator("iframe");
    }
    private FrameLocator startViewerFormular(ELOAction eloAction) {
        BaseFunctions.sleep();
        Locator rows = page.locator("xpath=//*[@class=\"x-btn-button\"]");
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).textContent().equals("Formular")) {
                rows.nth(i).click();
            }
        }
        startSelectionDialogItem(eloAction.getSelectionDialogItem());
        BaseFunctions.sleep();

        return getFrameLocator("FormularViewer");
    }
    private FrameLocator startFormula(ELOAction eloAction) {
        switch (eloAction.getFormulaType()) {
            case EXTERNAL -> { return startExternalFormular(eloAction);}
            case VIEWER -> { return startViewerFormular(eloAction);}
        }
        return page.frameLocator("");
    }
    private void executeAction(ELOAction eloAction,
                               Map<String, TabPage> tabPages) {

        selectSolutionsFolder();
        selectEntryByPath(eloAction.getEntryPath());

        FrameLocator frameLocator = startFormula(eloAction);
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
    private Locator selectFolder(String folder) {
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, folder, "class", "color");
        return optionalLocator.orElse(page.locator(""));
    }
    private void selectEntryByPath(String path) {
        if (path.equals("")) {
            return;
        }
        String[] folders = path.split("/");
        if(folders.length > 0) {
            if (selectFolder(folders[0]).isVisible()) {
                for (int i = 0; i < folders.length-1; i++) {
                    BaseFunctions.sleep();
                    if (!selectFolder(folders[i+1]).isVisible()) {
                        selectFolder(folders[i]).dblclick();
                        System.out.println("selectFolder parentfolder " + folders[i] + " dblclick");
                    }
                    selectFolder(folders[i+1]).dblclick();
                    System.out.println("selectFolder folder " + folders[i+1] + " dblclick");

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
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace_" + browserContext.browser().browserType().name() + ".zip")));
        browserContext.close();
        playwright.close();
    }
    public static void execute(String jsonDataConfigFile, String jsonPlaywrightConfigFile) throws Exception {
        final DataConfig dataConfig = BaseFunctions.readDataConfig(jsonDataConfigFile);
        final PlaywrightConfig playwrightConfig = BaseFunctions.readPlaywrightConfig(jsonPlaywrightConfigFile);

        // Execute DataConfig
        WebclientSession ws = new WebclientSession(playwrightConfig, dataConfig.getEloSolutionArchiveData());
        ws.login(dataConfig.getLoginData());

        for (ELOAction eloAction: dataConfig.getEloActionData().getEloActions()) {
            Map<String, TabPage> tabPages = eloAction.getTabPages();

            // Execute Action
            ws.executeAction(eloAction, tabPages);
        }

        if (playwrightConfig.isPause()) {
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
