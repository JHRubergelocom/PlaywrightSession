package session;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import de.elo.ix.client.IXConnection;
import de.elo.ix.client.WFDiagram;
import eloix.ELOIxConnection;
import eloix.RepoUtils;
import eloix.RfUtils;
import eloix.WfUtils;
import report.HtmlReport;
import report.ReportData;
import report.ReportParagraph;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WebclientSession {
    private Page page;
    private final Playwright playwright;
    private BrowserContext browserContext;
    private final DataConfig dataConfig;
    private final List<ReportParagraph> reportParagraphs;
    private final String reportPath;
    public Page getPage() {
        return page;
    }
    public String getReportPath() {
        return reportPath;
    }
    public List<ReportParagraph> getReportParagraphs() {
        return reportParagraphs;
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
            newContextOptions.setRecordVideoDir(Paths.get(getReportPath()));
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
    private WebclientSession(PlaywrightConfig playwrightConfig, DataConfig dataConfig, String reportPath) {
        this.dataConfig = dataConfig;
        this.reportParagraphs = new ArrayList<>();
        this.reportPath = reportPath;

        playwright = Playwright.create();
        setPlaywrightConfigParameter(playwrightConfig);
    }
    private void click(Locator locator) {
        try {
            BaseFunctions.click(locator);
        } catch (Exception e) {
            BaseFunctions.reportMessage(reportParagraphs, "<span>" + "Control not clickable" + "</span>");
        }
    }
    private void type(Locator locator, String text) {
        try {
            BaseFunctions.type(locator, text);
        } catch (Exception e) {
            BaseFunctions.reportMessage(reportParagraphs, "<span>" + "Control not clickable" + "</span>");
        }
    }
    private void login(LoginData loginData) {
        page.navigate("http://" + loginData.getStack() + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        Locator locator = page.locator("[name=\"" + "locale" + "\"]");
        locator.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Deutsch")).click();
        type(page.getByPlaceholder(loginData.getTextUserName().getSelector()), loginData.getTextUserName().getValue());
        type(page.getByPlaceholder(loginData.getTextPassword().getSelector()), loginData.getTextPassword().getValue());

        BaseFunctions.reportScreenshot(this, "Login Webclient", "loginwebclient.png");
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
    private void startSelectionDialogItem(ELOAction eloAction, int index) {
        if (!eloAction.getSelectionDialogItem().equals("")) {
            System.out.println("selectionDialogItem = "+ eloAction.getSelectionDialogItem());
            BaseFunctions.sleep();
            BaseFunctions.reportScreenshot(this, eloAction.getSelectionDialogItem(), BaseFunctions.getScreenShotFileName(eloAction, "selectionDialogItem") + index + ".png");
            page.getByText(eloAction.getSelectionDialogItem()).click();
        }
    }
    private Optional<FrameLocator> startExternalFormular(ELOAction eloAction, int index) {
        System.out.println("eloActionDef " + eloAction.getEloActionDef());
        System.out.println("selectionDialogItem " + eloAction.getSelectionDialogItem());
        BaseFunctions.sleep();
        if (!selectRibbon(eloAction, index)) {
            return Optional.empty();
        }
        BaseFunctions.sleep();
        if(!selectMenu(eloAction, index)) {
            return Optional.empty();
        }

        BaseFunctions.sleep();
        if (!selectButton(eloAction, index)) {
            return Optional.empty();
        }

        BaseFunctions.sleep();
        startSelectionDialogItem(eloAction, index);
        BaseFunctions.sleep();

        return  Optional.of(getFrameLocator("iframe"));
    }
    private Optional<FrameLocator> startViewerFormular(ELOAction eloAction, int index) {
        BaseFunctions.sleep();
        Locator rows = page.locator("xpath=//*[@class=\"x-btn-button\"]");
        int count = rows.count();
        System.out.println("rows.count(): " + count);
        if (count == 0) {
            return Optional.empty();
        }
        for (int i = 0; i < count; ++i) {
            System.out.println("Row: " + i + " textContent() " + rows.nth(i).textContent());
            System.out.println("Row: " + i + " innerHTML() " + rows.nth(i).innerHTML());
            System.out.println("Row: " + i + " " + rows.nth(i));
            if (rows.nth(i).textContent().equals("Formular")) {
                rows.nth(i).click();
            }
        }
        startSelectionDialogItem(eloAction, index);
        BaseFunctions.sleep();

        return Optional.of(getFrameLocator("FormularViewer"));
    }
    private Optional<FrameLocator> startFormula(ELOAction eloAction, int index) {
        switch (eloAction.getFormulaType()) {
            case EXTERNAL -> { return startExternalFormular(eloAction, index);}
            case VIEWER -> { return startViewerFormular(eloAction, index);}
        }
        return Optional.empty();
    }
    private boolean executeAction(ELOAction eloAction,
                               List<TabPage> tabPages, int index) {
        boolean checkData = true;
        selectEntryByPath(dataConfig.getEloSolutionArchiveData().getSelectorSolutionsFolder() +
                "/" +
                dataConfig.getLoginData().getTextUserName().getValue());

        if(selectEntryByPath(eloAction.getEntryPath())) {
            Optional<FrameLocator> frameLocatorOptional = startFormula(eloAction, index);
            if (frameLocatorOptional.isPresent()) {
                Formula formula = new Formula(frameLocatorOptional.get(), this, eloAction);
                if(formula.inputData(tabPages, index)) {
                    formula.quit(eloAction.getFormulaSaveButton(), index);
                } else {
                    if(!eloAction.getFormulaCancelButton().equals("")) {
                        formula.quit(eloAction.getFormulaCancelButton(), index);
                        BaseFunctions.reportScreenshot(this, "<span>" + "Formular cannot be saved" + "</span>", BaseFunctions.getScreenShotFileName(eloAction, "Formula" + index) + ".png");
                        BaseFunctions.reportMessage(reportParagraphs, "<span>" + "Formular cannot be saved" + "</span>");
                    }
                    checkData = false;
                }
            }
        } else {
            checkData = false;
        }
        BaseFunctions.sleep();
        return checkData;
    }
    private void selectSolutionTile() {
        BaseFunctions.click(page.locator(dataConfig.getEloSolutionArchiveData().getSelectorSolutionTile()));
    }
    private Optional<Locator> selectFolder(String folder) {
        return BaseFunctions.selectByTextAttribute(page, folder, "class", "color");
    }
    private boolean selectEntryByPath(String path) {
        if (path.equals("")) {
            return true;
        }
        try {
            String[] folders = path.split("/");
            if(folders.length > 0) {
                if (selectFolder(folders[0]).isPresent()) {
                    for (int i = 0; i < folders.length-1; i++) {
                        BaseFunctions.sleep();
                        if (selectFolder(folders[i+1]).isEmpty()) {
                            try {
                                if(selectFolder(folders[i]).isPresent()) {
                                    selectFolder(folders[i]).get().dblclick();
                                    System.out.println("selectFolder parentfolder " + folders[i] + " dblclick");
                                } else {
                                    BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectFolder parentfolder " + folders[i] + " not available" + "</span>");
                                    return false;
                                }
                            } catch (Exception e) {
                                BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectFolder parentfolder " + folders[i] + " not available" + "</span>");
                                return false;
                            }
                        }
                        try {
                            selectFolder(folders[i+1]).get().dblclick();
                            System.out.println("selectFolder folder " + folders[i+1] + " dblclick");
                        } catch (Exception e) {
                            BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectFolder folder " + folders[i+1] + " not available" + "</span>");
                            return false;
                        }
                    }
                } else {
                    System.err.println("selectorFolder folder=" + folders[0] + " of path=" + path + "is not available");
                    BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectorFolder folder=" + folders[0] + " of path=" + path + "is not available" + "</span>");
                    return false;
                }
            } else {
                System.err.println("selectorFolder path=" + path + "is empty");
                BaseFunctions.reportMessage(reportParagraphs, "<span>" + "selectorFolder path=" + path + "is empty" + "</span>");
                return false;
            }
        } catch (Exception e) {
            BaseFunctions.reportMessage(reportParagraphs, "<span>" + e.getMessage() + "</span>");
            return false;
        }
        return true;
    }
    private boolean selectRibbon(ELOAction eloAction, int index){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, eloAction.getEloActionDef().getSelectorRibbon(), "id", "button");
        if(optionalLocator.isPresent()) {
            BaseFunctions.reportScreenshot(this, eloAction.getEloActionDef().getSelectorRibbon(), BaseFunctions.getScreenShotFileName(eloAction, "Ribbon" + index) + ".png");
            optionalLocator.get().click();
            return true;
        }
        BaseFunctions.reportMessage(reportParagraphs, "<span>Ribbon " + eloAction.getEloActionDef().getSelectorRibbon() + " nicht gefunden!</span>");
        return false;
    }
    private boolean selectMenu(ELOAction eloAction, int index){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, eloAction.getEloActionDef().getSelectorMenu(), "id", "button");
        if (optionalLocator.isPresent()) {
            BaseFunctions.reportScreenshot(this, eloAction.getEloActionDef().getSelectorMenu(), BaseFunctions.getScreenShotFileName(eloAction, "Menu" + index) + ".png");
            optionalLocator.get().click();
            return true;
        }
        BaseFunctions.reportMessage(reportParagraphs, "<span>Menu " + eloAction.getEloActionDef().getSelectorMenu() + " nicht gefunden!</span>");
        return false;
    }
    private boolean selectButton(ELOAction eloAction, int index){
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, eloAction.getEloActionDef().getSelectorButton(), "id", "comp");
        if (optionalLocator.isEmpty()) {
            optionalLocator = BaseFunctions.selectByTextAttributeNotExact(page, eloAction.getEloActionDef().getSelectorButton(), "id", "comp");
        }
        if (optionalLocator.isPresent()) {
            BaseFunctions.reportScreenshot(this, eloAction.getEloActionDef().getSelectorButton(), BaseFunctions.getScreenShotFileName(eloAction, "Button" + index) + ".png");
            optionalLocator.get().click();
            return true;
        }
        BaseFunctions.reportMessage(reportParagraphs, "<span>Button " + eloAction.getEloActionDef().getSelectorButton() + " nicht gefunden!</span>");
        return false;
    }
    private void close() {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get(getReportPath() + "trace_" + browserContext.browser().browserType().name() + ".zip")));
        browserContext.close();
        playwright.close();
    }
    private void deleteData() throws Exception {
        // Ix Connect
        IXConnection ixConn = ELOIxConnection.getIxConnection(dataConfig.getLoginData(), true);
        System.out.println("IxConn: " + ixConn);

        // Delete arcpath
        for (String arcPath: dataConfig.getEloDeleteData().getArcPaths()) {
            RepoUtils.DeleteSord(ixConn, arcPath);
        }

        ixConn.close();
    }
    private void forwardWorkflow() throws Exception {
        // Ix Connect
        IXConnection ixConn = ELOIxConnection.getIxConnection(dataConfig.getLoginData(), false);
        System.out.println("IxConn: " + ixConn);

        // Forward Workflow
        for (String toNodeName: dataConfig.getEloForwardWorkflow().getToNodesName()) {
            WfUtils.forwardWorkflow(ixConn, toNodeName);
        }

        ixConn.close();
    }
    private void removeFinishedWorkflows() throws Exception {
        // Ix Connect
        IXConnection ixConn = ELOIxConnection.getIxConnection(dataConfig.getLoginData(), true);
        System.out.println("IxConn: " + ixConn);

        // Remove Workflows
        List<WFDiagram> finishedWorkflows = WfUtils.getFinishedWorkflows(ixConn);
        WfUtils.removeFinishedWorkflows(ixConn, finishedWorkflows);

        ixConn.close();
    }
    private void executeRF() throws Exception {
        // Ix Connect
        IXConnection ixConn = ELOIxConnection.getIxConnection(dataConfig.getLoginData(), true);
        System.out.println("IxConn: " + ixConn);

        // Execute RF
        for (ELORf eloRf: dataConfig.getEloExecuteRf().getEloRfs()) {
            RfUtils.executeRF(ixConn, eloRf);
        }

        ixConn.close();

    }
    private static void showReport(String reportPath, List<ReportParagraph> reportParagraphs) {
        ReportData reportData = new ReportData("Report Test", reportParagraphs);
        String htmlDoc = HtmlReport.createReport(reportData);
        HtmlReport.showReport(reportPath, htmlDoc);
    }
    public static void execute(String jsonDataConfigFile, String jsonPlaywrightConfigFile) {
        boolean checkData = true;
        WebclientSession ws = null;
        List<ReportParagraph> reportParagraphs = new ArrayList<>();
        try {
            final DataConfig dataConfig = BaseFunctions.readDataConfig(jsonDataConfigFile);
            final PlaywrightConfig playwrightConfig = BaseFunctions.readPlaywrightConfig(jsonPlaywrightConfigFile);

            // Execute DataConfig
            ws = new WebclientSession(playwrightConfig, dataConfig, BaseFunctions.getTestReportDir());
            try {
                ws.login(dataConfig.getLoginData());
            } catch ( Exception e) {
                throw new Exception("Login fehlgeschlagen!");
            }
            int index = 1;
            for (ELOAction eloAction: dataConfig.getEloActionData().getEloActions()) {
                List<TabPage> tabPages = eloAction.getTabPages();

                // Execute Action
                if(!ws.executeAction(eloAction, tabPages, index)) {
                    checkData = false;
                }
                index++;
            }

            if (playwrightConfig.isPause()) {
                ws.getPage().pause();
            }

            // Delete Data
            List<String> arcPaths = dataConfig.getEloDeleteData().getArcPaths();
            if(arcPaths.size() > 0) {
                ws.deleteData();

                // Remove Workflows
                ws.removeFinishedWorkflows();
            }

            // Forward Workflow
            List<String> toNodesName = dataConfig.getEloForwardWorkflow().getToNodesName();
            if(toNodesName.size() > 0) {
                ws.forwardWorkflow();
            }

            // Execute RF
            List<ELORf> eloRfs = dataConfig.getEloExecuteRf().getEloRfs();
            if(eloRfs.size() > 0) {
                ws.executeRF();
            }

            if(checkData) {
                BaseFunctions.reportMessage(ws.getReportParagraphs(), "Test war erfolgreich!");
            } else {
                BaseFunctions.reportMessage(ws.getReportParagraphs(), "<span>Test ist fehlgeschlagen!</span>");
            }

        } catch ( Exception e) {
            System.err.println(e.getMessage());
            if (ws != null) {
                BaseFunctions.reportScreenshot(ws,"<span>" + e.getMessage() + "</span>", "exeception.png");
                BaseFunctions.reportMessage(ws.getReportParagraphs(), "<span>Test ist fehlgeschlagen!</span>");
            } else {
                BaseFunctions.reportMessage(reportParagraphs, "<span>" + e.getMessage() + "</span>");
                BaseFunctions.reportMessage(reportParagraphs, "<span>Test ist fehlgeschlagen!</span>");
            }
        } finally {
            if (ws != null) {
                showReport(ws.getReportPath(), ws.getReportParagraphs());
                ws.close();
            } else {
                showReport(BaseFunctions.getTestReportDir(), reportParagraphs);
            }
        }
    }

    @Override
    public String toString() {
        return "WebclientSession{" +
                "page=" + page +
                ", playwright=" + playwright +
                ", browserContext=" + browserContext +
                ", dataConfig=" + dataConfig +
                ", reportParagraphs=" + reportParagraphs +
                ", reportPath='" + reportPath + '\'' +
                '}';
    }
}
