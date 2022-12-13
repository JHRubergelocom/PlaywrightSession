package test;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import session.AssignmentStatus;
import session.TabPage;
import session.WebclientSession;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlaywrightSessionTest {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    private Map<String, TabPage> createMB1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB1");
        fields.put("IX_GRP_MEETING_BOARD_MINUTE_TAKER", "Bodo Kraft");
        fields.put("IX_DESC", "Beschreibung Meetingboard1");

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_BOARD_ORGANIZER", "Jan Eichner");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_BOARD_ORGANIZER", "Sandra Renz");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "//*[@id='part_220_meeting_roles']/tr[11]/td[3]/div/input", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Allgemein", tabPage);

        // Mitglieder
        /*
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Baum");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Gerd");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "g.baum@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Renz");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Sandra");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "s.renz@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "//*[@id='part_320_members']/tr[8]/td[2]/div/input", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Mitglieder", tabPage);
        */
        // Themen
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_ITEM_TITLE", "Rückblende");
        tableLine.put("WF_MAP_MEETING_ITEM_DURATION", "30");
        tableLine.put("WF_MAP_MEETING_ITEM_DESC", "Rückblick vergangene Projekte");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_ITEM_TITLE", "Planung");
        tableLine.put("WF_MAP_MEETING_ITEM_DURATION", "40");
        tableLine.put("WF_MAP_MEETING_ITEM_DESC", "Zukünftige Projekte");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "//*[@id='part_230_meetingitem_templ']/tr[10]/td[2]/div/input", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Themen", tabPage);

        // Benachrichtigungen
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "default");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "default");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "//*[@id='part_410_notifications']/tr[7]/td[2]/div/input", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        fields = new TreeMap<>();
        fields.put("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting1");

        table = new ArrayList<>();

        checkboxes = new TreeMap<>();
        checkboxes.put("IX_MAP_MEETING_BOARD_SETTING_ITEMTOAGENDA", true);

        tabPage = new TabPage(fields, table, "//*[@id='part_410_notifications']/tr[7]/td[2]/div/input", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Einstellungen", tabPage);

        return tabPages;

    }



    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();

        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        // Stop tracing and export it into a zip archive.
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));

        context.close();
    }

    @Test
    public void TestSession() {
        Map<String, TabPage> tabPages;

        WebclientSession ws = new WebclientSession();
        ws.login("ruberg-meeting.dev.elo", "Administrator", "elo");


        ws.selectSolutionsFolder();
        tabPages = createMB1();
/*
        Action action = new Action(ws, "CreateMeetingBoard");
        action.startFormula();

        FrameLocator frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-1782");
        System.out.println("frameLocator: " + frameLocator);
        Locator locator = frameLocator.locator("input[name=\"IX_GRP_MEETING_BOARD_NAME\"]");
        System.out.println("locator: " + locator);
        locator.fill("MB1");
        locator.press("Tab");

        Locator benachrichtigungen = frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName("Benachrichtigungen"));
        System.out.println("benachrichtigungen: " + benachrichtigungen);
        benachrichtigungen.click();
*/
        ws.executeAction("CreateMeetingBoard", tabPages);





        ws.getPage().pause();
        ws.close();

    }
    @Test
    public void firstScript() {
        page.navigate("http://playwright.dev");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
        System.out.println(page.title());
    }

    @Test
    public void secondScript() {
        page.navigate("http://" + "ruberg-meeting.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");



        page.pause(); // Start Codegen
    }

}
