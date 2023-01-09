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

        TabPage tabPage = new TabPage(fields, table, "Weitere Person", checkboxes, AssignmentStatus.NOTHING);
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

        tabPage = new TabPage(fields, table, "Weiteres Mitglied", checkboxes, AssignmentStatus.NOTHING);
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

        tabPage = new TabPage(fields, table, "Weiteres Thema", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Themen", tabPage);

        // Benachrichtigungen
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "Weitere Benachrichtigung", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        fields = new TreeMap<>();
        fields.put("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting1");

        table = new ArrayList<>();

        checkboxes = new TreeMap<>();
        checkboxes.put("IX_MAP_MEETING_BOARD_SETTING_ITEMTOAGENDA", true);

        tabPage = new TabPage(fields, table, "", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Einstellungen", tabPage);

        return tabPages;

    }

    private Map<String, TabPage> createME1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_NAME", "Meeting1");
        fields.put("IX_GRP_MEETING_LOCATION", "Musterstadt");
        fields.put("IX_GRP_MEETING_MINUTE_TAKER", "Charlotte Bennett");
        // fields.put("IX_DESC", "Beschreibung Meeting1");

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_TIMESLOT_START", "09:00");
        tableLine.put("IX_MAP_MEETING_TIMESLOT_END", "17:00");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_TIMESLOT_START", "09:00");
        tableLine.put("IX_MAP_MEETING_TIMESLOT_END", "17:00");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weiterer Tag", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Allgemein", tabPage);

        // Teilnehmende
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Baum");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Gerd");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "g.baum@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);

/*
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Renz");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Sandra");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "s.renz@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);
*/
        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "Weiterer Gast", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Teilnehmende", tabPage);

        // Benachrichtigungen
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "Weitere Benachrichtigung", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Benachrichtigungen", tabPage);

        // Wiederholung
        /*
        fields = new TreeMap<>();

        table = new ArrayList<>();

        checkboxes = new TreeMap<>();
        checkboxes.put("WF_MAP_MEETING_REPETITION_CREATE", true);

        tabPage = new TabPage(fields, table, "", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Wiederholung", tabPage);
         */
        return tabPages;

    }

    private Map<String, TabPage> createMI1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB1");
        fields.put("IX_GRP_MEETING_NAME", "Meeting1");
        fields.put("IX_GRP_MEETING_ITEM_TITLE", "Thema1");
        fields.put("IX_GRP_MEETING_ITEM_DURATION", "40");
        fields.put("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "Adrian Smith");

        // fields.put("IX_DESC", "Beschreibung Thema1");

        List<Map<String, String>> table = new ArrayList<>();

        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Kraft");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Bodo");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weitere Person", checkboxes, AssignmentStatus.MEETING);
        tabPages.put("", tabPage);

        return tabPages;

    }

    private Map<String, TabPage> createMIP1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB1");
        fields.put("IX_GRP_MEETING_ITEM_POOL_NAME", "Meetingitempool1");
        fields.put("IX_DESC", "Beschreibung Meetingitempool1");

        List<Map<String, String>> table = new ArrayList<>();

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("", tabPage);

        return tabPages;

    }

    private Map<String, TabPage> createMIPMI1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB1");
        fields.put("IX_MAP_MEETING_ITEM_POOL_NAME", "Meetingitempool1");
        fields.put("IX_GRP_MEETING_ITEM_TITLE", "Thema1 Ideenpool1");
        fields.put("IX_GRP_MEETING_ITEM_DURATION", "40");
        fields.put("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "Adrian Smith");

        // fields.put("IX_DESC", "Beschreibung Thema1");

        List<Map<String, String>> table = new ArrayList<>();

        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Kraft");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Bodo");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weitere Person", checkboxes, AssignmentStatus.POOL);
        tabPages.put("", tabPage);

        return tabPages;

    }


    private Map<String, TabPage> createMB2Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB2");
        fields.put("IX_GRP_MEETING_BOARD_MINUTE_TAKER", "David Lee");
        fields.put("IX_DESC", "Beschreibung Meetingboard2");

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_BOARD_ORGANIZER", "Jan Eichner");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_BOARD_ORGANIZER", "Sandra Renz");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weitere Person", checkboxes, AssignmentStatus.NOTHING);
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

        tabPage = new TabPage(fields, table, "Weiteres Mitglied", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Mitglieder", tabPage);
        */
        // Tagesordnungspunkte
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

        tabPage = new TabPage(fields, table, "Weiterer TOP", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Tagesordnungspunkte", tabPage);

        // Benachrichtigungen
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "Weitere Benachrichtigung", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        fields = new TreeMap<>();
        fields.put("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting2");

        table = new ArrayList<>();

        checkboxes = new TreeMap<>();
        checkboxes.put("IX_MAP_MEETING_BOARD_SETTING_APPROVAL_REQUIRED", false);

        tabPage = new TabPage(fields, table, "Weitere Benachrichtigung", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Einstellungen", tabPage);

        return tabPages;

    }

    private Map<String, TabPage> createME1Premium() {
        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_NAME", "Sitzung1");
        fields.put("IX_GRP_MEETING_LOCATION", "Musterdorf");
        fields.put("IX_GRP_MEETING_MINUTE_TAKER", "Bodo Kraft");
        // fields.put("IX_DESC", "Beschreibung Sitzung1");

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_TIMESLOT_START", "11:00");
        tableLine.put("IX_MAP_MEETING_TIMESLOT_END", "16:00");
        table.add(tableLine);


        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_TIMESLOT_START", "09:00");
        tableLine.put("IX_MAP_MEETING_TIMESLOT_END", "17:00");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weiterer Tag", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Allgemein", tabPage);

        // Teilnehmende
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Baum");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Gerd");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "g.baum@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);

/*
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Renz");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Sandra");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "s.renz@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);
*/
        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "Weiterer Gast", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Teilnehmende", tabPage);

        // Benachrichtigungen
        fields = new TreeMap<>();

        table = new ArrayList<>();
        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+");
        tableLine.put("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE");
        table.add(tableLine);

        checkboxes = new TreeMap<>();

        tabPage = new TabPage(fields, table, "Weitere Benachrichtigung", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Benachrichtigungen", tabPage);

        // Wiederholung
        /*
        fields = new TreeMap<>();

        table = new ArrayList<>();

        checkboxes = new TreeMap<>();
        checkboxes.put("WF_MAP_MEETING_REPETITION_CREATE", true);

        tabPage = new TabPage(fields, table, "", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("Wiederholung", tabPage);
        */
        return tabPages;

    }

    private Map<String, TabPage> createMI1Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB2");
        fields.put("IX_GRP_MEETING_NAME", "Sitzung1");
        fields.put("IX_GRP_MEETING_ITEM_TITLE", "TOP1");
        fields.put("IX_GRP_MEETING_ITEM_DURATION", "30");
        fields.put("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "David Lee");

        // fields.put("IX_DESC", "Beschreibung TOP1");

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Bennett");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Charlotte");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weitere Person", checkboxes, AssignmentStatus.MEETING);
        tabPages.put("", tabPage);

        return tabPages;

    }

    private Map<String, TabPage> createMIP2Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB2");
        fields.put("IX_GRP_MEETING_ITEM_POOL_NAME", "Meetingitempool2");
        fields.put("IX_DESC", "Beschreibung Meetingitempool2");

        List<Map<String, String>> table = new ArrayList<>();

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "", checkboxes, AssignmentStatus.NOTHING);
        tabPages.put("", tabPage);

        return tabPages;

    }

    private Map<String, TabPage> createMIPMI1Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        Map<String,String> fields = new TreeMap<>();

        fields.put("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2");
        fields.put("IX_GRP_MEETING_BOARD_CODE", "MB2");
        fields.put("IX_MAP_MEETING_ITEM_POOL_NAME", "Meetingitempool2");

        fields.put("IX_GRP_MEETING_ITEM_TITLE", "TOP1");
        fields.put("IX_GRP_MEETING_ITEM_DURATION", "30");
        fields.put("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "David Lee");

        // fields.put("IX_DESC", "Beschreibung TOP1 Meetingitempool2");

        List<Map<String, String>> table = new ArrayList<>();
        Map<String, String> tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Bennett");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Charlotte");
        table.add(tableLine);

        tableLine = new TreeMap<>();
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis");
        tableLine.put("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica");
        table.add(tableLine);

        Map<String,Boolean> checkboxes = new TreeMap<>();

        TabPage tabPage = new TabPage(fields, table, "Weitere Person", checkboxes, AssignmentStatus.POOL);
        tabPages.put("", tabPage);

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
        ws.executeAction("CreateMeetingBoard", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createME1();
        ws.executeAction("CreateMeeting", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMI1();
        ws.executeAction("CreateMeetingItem", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMB2Premium();
        ws.executeAction("CreateMeetingBoardPremium", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createME1Premium();
        ws.executeAction("CreateMeetingPremium", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMI1Premium();
        ws.executeAction("CreateMeetingItemPremium", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMIP1();
        ws.executeAction("CreateMeetingItemPool", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMIPMI1();
        ws.executeAction("CreateMeetingItem", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMIP2Premium();
        ws.executeAction("CreateMeetingItemPoolPremium", tabPages);

        ws.selectSolutionsFolder();
        tabPages = createMIPMI1Premium();
        ws.executeAction("CreateMeetingItemPremium", tabPages);

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
