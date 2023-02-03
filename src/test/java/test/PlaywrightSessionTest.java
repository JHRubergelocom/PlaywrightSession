package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import session.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class PlaywrightSessionTest {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;
    // New instance for each test method.
    BrowserContext context;
    Page page;

    private Map<String, TabPage> createEmployee1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_FIRSTNAME", "Hans", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_LASTNAME", "Hansen", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_PERSONNELNO", "12345", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_ELOUSERID", "Jan Eichner", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_RESPONSIBLE", "Bodo Kraft", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_SUPERIOR", "Gerd Baum", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DATEOFJOINING", "20220101", ELOControlType.TEXT));

        List<List<ELOControl>> table = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, table, "");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createCompany1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_COMPANY", "Firma Hansens", ELOControlType.TEXT));

        List<List<ELOControl>> table = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, table, "");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createMB1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_MINUTE_TAKER", "Bodo Kraft", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_DESC", "Meetingboard1", ELOControlType.TEXT));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Jan Eichner", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Sandra Renz", ELOControlType.DYNKWL));

        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weitere Person");
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
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_TITLE", "Rückblende", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DURATION", "30", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DESC", "Rückblick vergangene Projekte", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_TITLE", "Planung", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DURATION", "40", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DESC", "Zukünftige Projekte", ELOControlType.TEXT));
        table.add(tableLine);

        tabPage = new TabPage(initTabPage, controls, table, "Weiteres Thema");
        tabPages.put("Themen", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tabPage = new TabPage(initTabPage, controls, table, "Weitere Benachrichtigung");
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_SETTING_ITEMTOAGENDA", "true", ELOControlType.CHECKBOX));


        table = new ArrayList<>();

        tabPage = new TabPage(initTabPage, controls, table, "");
        tabPages.put("Einstellungen", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createME1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_NAME", "Meeting1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_LOCATION", "Musterstadt", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_MINUTE_TAKER", "Charlotte Bennett", ELOControlType.DYNKWL));
        controls.add(new ELOControl("Hinterlegen Sie eine Beschreibung des Meetings.", "Beschreibung Meeting1", ELOControlType.REDACTOR));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "09:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "17:00", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "09:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "17:00", ELOControlType.TEXT));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weiterer Tag");
        tabPages.put("Allgemein", tabPage);

        // Teilnehmende
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_LASTNAME", "Baum", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_FIRSTNAME", "Gerd", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_EMAIL", "g.baum@contelo.de", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG", ELOControlType.DYNKWL));
        table.add(tableLine);

/*
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Renz");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Sandra");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "s.renz@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);
*/

        tabPage = new TabPage(initTabPage, controls, table, "Weiterer Gast");
        tabPages.put("Teilnehmende", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tabPage = new TabPage(initTabPage, controls, table, "Weitere Benachrichtigung");
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

        List<ELOControl> initTabPage = new ArrayList<>();
        initTabPage.add(new ELOControl("Meeting", "true", ELOControlType.RADIO));

        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_NAME", "Meeting1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_TITLE", "Thema1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_DURATION", "40", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "Adrian Smith", ELOControlType.DYNKWL));
        controls.add(new ELOControl("Hinterlegen Sie eine Beschreibung des Themas.", "Beschreibung Thema1", ELOControlType.REDACTOR));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();

        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Kraft", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Bodo", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weitere Person");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createMIP1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)
        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_POOL_NAME", "Meetingitempool1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_DESC", "Beschreibung Meetingitempool1", ELOControlType.TEXT));

        List<List<ELOControl>> table = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, table, "");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createMIPMI1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        initTabPage.add(new ELOControl("Themensammlung", "true", ELOControlType.RADIO));

        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_MAP_MEETING_ITEM_POOL_NAME", "Meetingitempool1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_TITLE", "Thema1 Ideenpool1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_DURATION", "40", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "Adrian Smith", ELOControlType.DYNKWL));
        controls.add(new ELOControl("Hinterlegen Sie eine Beschreibung des Themas.", "Beschreibung Thema1", ELOControlType.REDACTOR));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Kraft", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Bodo", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weitere Person");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createMB2Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB2", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_MINUTE_TAKER", "David Lee", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_DESC", "Beschreibung Meetingboard2", ELOControlType.TEXT));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Jan Eichner", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Sandra Renz", ELOControlType.DYNKWL));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weitere Person");
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
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_TITLE", "Rückblende", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DURATION", "30", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DESC", "Rückblick vergangene Projekte", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_TITLE", "Planung", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DURATION", "40", ELOControlType.TEXT));
        tableLine.add(new ELOControl("WF_MAP_MEETING_ITEM_DESC", "Zukünftige Projekte", ELOControlType.TEXT));
        table.add(tableLine);

        tabPage = new TabPage(initTabPage, controls, table, "Weiterer TOP");
        tabPages.put("Tagesordnungspunkte", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tabPage = new TabPage(initTabPage, controls, table, "Weitere Benachrichtigung");
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting2", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_SETTING_APPROVAL_REQUIRED", "false", ELOControlType.CHECKBOX));

        table = new ArrayList<>();

        tabPage = new TabPage(initTabPage, controls, table, "Weitere Benachrichtigung");
        tabPages.put("Einstellungen", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createME1Premium() {
        Map<String, TabPage> tabPages = new TreeMap<>();

        // Allgemein
        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_NAME", "Sitzung1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_LOCATION", "Musterdorf", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_MINUTE_TAKER", "Bodo Kraft", ELOControlType.DYNKWL));
        controls.add(new ELOControl("Hinterlegen Sie eine Beschreibung der Sitzung.", "Beschreibung Sitzung1", ELOControlType.REDACTOR));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "11:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "16:00", ELOControlType.TEXT));
        table.add(tableLine);


        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "09:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "17:00", ELOControlType.TEXT));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weiterer Tag");
        tabPages.put("Allgemein", tabPage);

        // Teilnehmende
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_LASTNAME", "Baum", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_FIRSTNAME", "Gerd", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_EMAIL", "g.baum@contelo.de", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG", ELOControlType.DYNKWL));
        table.add(tableLine);

/*
        tableLine = new TreeMap<>();
        tableLine.put("WF_MAP_MEETING_PERSON_LASTNAME", "Renz");
        tableLine.put("WF_MAP_MEETING_PERSON_FIRSTNAME", "Sandra");
        tableLine.put("WF_MAP_MEETING_PERSON_EMAIL", "s.renz@contelo.de");
        tableLine.put("WF_MAP_MEETING_PERSON_COMPANYNAME", "Contelo AG");
        table.add(tableLine);
*/
        tabPage = new TabPage(initTabPage, controls, table, "Weiterer Gast");
        tabPages.put("Teilnehmende", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tabPage = new TabPage(initTabPage, controls, table, "Weitere Benachrichtigung");
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

        List<ELOControl> initTabPage = new ArrayList<>();
        initTabPage.add(new ELOControl("Sitzung", "true", ELOControlType.RADIO));

        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_NAME", "Sitzung1", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_TITLE", "TOP1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_DURATION", "30", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "David Lee", ELOControlType.DYNKWL));
        controls.add(new ELOControl("Hinterlegen Sie eine Beschreibung des TOPs.", "Beschreibung TOP1", ELOControlType.REDACTOR));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Bennett", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Charlotte", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weitere Person");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createMIP2Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_POOL_NAME", "Meetingitempool2", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_DESC", "Beschreibung Meetingitempool2", ELOControlType.TEXT));

        List<List<ELOControl>> table = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, table, "");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createMIPMI1Premium() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        initTabPage.add(new ELOControl("Themensammlung", "true", ELOControlType.RADIO));

        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_NAME", "Meetingboard2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_BOARD_CODE", "MB2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_MAP_MEETING_ITEM_POOL_NAME", "Meetingitempool2", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_TITLE", "TOP1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_DURATION", "30", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_MEETING_ITEM_RESPONSIBLE_PERSON", "David Lee", ELOControlType.DYNKWL));
        controls.add(new ELOControl("Hinterlegen Sie eine Beschreibung des TOPs.", "Beschreibung TOP1 Meetingitempool2", ELOControlType.REDACTOR));

        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Bennett", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Charlotte", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        TabPage tabPage = new TabPage(initTabPage, controls, table, "Weitere Person");
        tabPages.put("", tabPage);

        return tabPages;

    }
    private DataConfig createDataConfig(String jsonFile) {
        switch(jsonFile) {
            case "DataConfigMeeting.json" -> {return createDataConfigMeeting();}
            case "DataConfigHr.json" -> {return createDataConfigHr();}
        }
        return new DataConfig();
    }

    private DataConfig createDataConfigHr() {
        // ELO Action Def Data
        final String selectorRibbonNew = "Neu";
        final String selectorMenuPersonnel = "Personal";
        final String selectorButtonNewEmployee = "Neuer Mitarbeiter";
        final String selectorButtonNewCompany = "Neue Organisation";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Administrator", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        Map<String, TabPage> eloTabPages = createEmployee1();
        ELOAction eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createCompany1();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewCompany), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData);
    }

    private DataConfig createDataConfigMeeting() {
        // ELO Action Def Data
        final String selectorRibbonNew = "Neu";
        final String selectorMenuMeeting = "Meeting";
        final String selectorMenuMeetingPremium = "Sitzung";
        final String selectorButtonCreateMeetingBoard = "Neues Meeting-Board";
        final String selectorButtonCreateMeetingBoardPremium = "Neues Meeting-Board";
        final String selectorButtonCreateMeeting = "Neues Meeting";
        final String selectorButtonCreateMeetingPremium = "Neue Sitzung";
        final String selectorButtonCreateMeetingItem = "Neues Thema";
        final String selectorButtonCreateMeetingItemPremium = "Neuer TOP";
        final String selectorButtonCreateMeetingItemPool = "Neue Themensammlung";
        final String selectorButtonCreateMeetingItemPoolPremium = "Neue Themensammlung";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Administrator", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-meeting.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        Map<String, TabPage> eloTabPages = createMB1();
        ELOAction eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingBoard), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createME1();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeeting), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMI1();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItem), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMB2Premium();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingBoardPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createME1Premium();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMI1Premium();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIP1();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItemPool), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIPMI1();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItem), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIP2Premium();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPoolPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIPMI1Premium();
        eloAction = new ELOAction(new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPremium), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData);
    }
    private FrameLocator getFrameLocator() {
        String selector = "";
        page.mainFrame().content();
        for (Frame frame: page.frames()) {
            System.out.println("Frame.name " + frame.name());
            if (frame.name().contains("iframe")) {
                selector = "#" + frame.name();
            }
        }
        FrameLocator frameLocator = page.frameLocator(selector);
        System.out.println("selector " + selector);
        System.out.println("frameLocator " + frameLocator);
        return frameLocator;

    }

    private Locator selectFolder(String folder) {
        return BaseFunctions.selectByTextAttribute(page, folder, "class", "color").get();
    }

    private void selectFolderPath(String path) {
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
    @ParameterizedTest
    @ValueSource(strings = {"DataConfigHr.json"})
    public void TestSession(String jsonFile) {
        WebclientSession.execute(jsonFile, true);
    }
    @ParameterizedTest
    @ValueSource(strings = {"DataConfigHr.json", "DataConfigMeeting.json", "Empty.json"})
    public void CreateDataConfigJson(String jsonFile) {
        // Create DataConfig
        final DataConfig dataConfig = createDataConfig(jsonFile);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(dataConfig);
        System.out.println(json);

        // Save DataConfig
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read DataConfig
        gson = new Gson();

        System.out.println("Reading " + jsonFile);
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
            DataConfig dataConfig1 = gson.fromJson(br, DataConfig.class);
            System.out.println(dataConfig1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(100));
    }

    @Test
    public void firstScript() {
        page.navigate("http://playwright.dev");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
        System.out.println(page.title());
    }
    @Test
    public void testLocator() {
        page.navigate("http://" + "ruberg-hr.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        page.getByPlaceholder("Name").fill("0");
        page.getByPlaceholder("Passwort").fill("elo");
        page.getByText("Anmelden").click();

        // Kachel "Solutions" klicken
        page.locator("xpath=//*[@title=\"Solutions\"]").click();

        // Ordner "Solutions" auswählen
        BaseFunctions.selectByTextAttribute(page, "Solutions", "class", "color").get().click();

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button").get().click();

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button").get().click();

        // Button "Neuer Mitarbeiter" auswählen
        BaseFunctions.sleep();
        BaseFunctions.selectByTextAttribute(page, "Neuer Mitarbeiter", "id", "comp").get().click();

        // Get Frame
        BaseFunctions.sleep();
        FrameLocator frameLocator = getFrameLocator();
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_FIRSTNAME" + "\"]"), "Hans", false);

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_LASTNAME" + "\"]"), "Hansen", false);

        // Formular speichern
        BaseFunctions.sleep();
        BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("OK")));

        // Ordner "Keil, Fritz" auswählen
        selectFolderPath("Solutions/Personalmanagement/Personalakten/K/Keil, Fritz");

        page.pause(); // Start Codegen
    }
}
