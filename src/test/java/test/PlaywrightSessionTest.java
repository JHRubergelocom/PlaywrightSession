package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
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
    private DataConfig createDataConfig() {
        // ELO Action Def Data
        final String selectorRibbonNew = "xpath=//*[@id=\"button-1218-btnIconEl\"]";
        final String selectorMenuMeeting = "xpath=//*[@id=\"button-1280-btnIconEl\"]";
        final String selectorMenuMeetingPremium = "xpath=//*[@id=\"button-1288-btnIconEl\"]";
        final String selectorButtonCreateMeetingBoard = "xpath=//*[@id=\"ext-comp-1274-textEl\"]";
        final String selectorButtonCreateMeetingBoardPremium = "xpath=//*[@id=\"ext-comp-1281-textEl\"]";
        final String selectorButtonCreateMeeting = "xpath=//*[@id=\"ext-comp-1275-textEl\"]";
        final String selectorButtonCreateMeetingPremium = "xpath=//*[@id=\"ext-comp-1282-textEl\"]";
        final String selectorButtonCreateMeetingItem = "xpath=//*[@id=\"ext-comp-1276-textEl\"]";
        final String selectorButtonCreateMeetingItemPremium = "xpath=//*[@id=\"ext-comp-1284-textEl\"]";
        final String selectorButtonCreateMeetingItemPool = "xpath=//*[@id=\"ext-comp-1277-textEl\"]";
        final String selectorButtonCreateMeetingItemPoolPremium = "xpath=//*[@id=\"ext-comp-1285-textEl\"]";

        // ELO Formula Data
        final String selectorAssignmentMeeting = "xpath=//*[@id=\"part_550_toggle_assignment\"]/tr[4]/td[2]/div/input[2]";
        final String selectorAssignmentPool = "xpath=//*[@id=\"part_550_toggle_assignment\"]/tr[4]/td[2]/div/input[1]";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("xpath=//*[@id=\"field-focustext-1020-inputEl\"]", "Administrator");
        final ELOControl textPassword = new ELOControl("xpath=//*[@id=\"textfield-1021-inputEl\"]", "elo");
        final ELOControl buttonLogin = new ELOControl("xpath=//*[@id=\"button-1023-btnIconEl\"]", "Login");
        final String stack = "ruberg-meeting.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@id=\"tile-1013\"]", "xpath=//*[@id=\"treeview-1061-record-1\"]");

        final Map<String, ELOActionDef> eloActionDefs = new HashMap<>();
        eloActionDefs.put("CreateMeetingBoard", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingBoard));
        eloActionDefs.put("CreateMeetingBoardPremium", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingBoardPremium));
        eloActionDefs.put("CreateMeeting", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeeting));
        eloActionDefs.put("CreateMeetingPremium", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingPremium));
        eloActionDefs.put("CreateMeetingItem", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItem));
        eloActionDefs.put("CreateMeetingItemPremium", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPremium));
        eloActionDefs.put("CreateMeetingItemPool", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItemPool));
        eloActionDefs.put("CreateMeetingItemPoolPremium", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPoolPremium));
        final ELOActionDefData eloActionDefData = new ELOActionDefData(eloActionDefs);

        final ELOActionFormulaData eloActionFormularData = new ELOActionFormulaData(selectorAssignmentMeeting, selectorAssignmentPool);

        final SortedMap<Integer, ELOAction> eloActions = new TreeMap<>();

        Map<String, TabPage> eloTabPages = createMB1();
        ELOAction eloAction = new ELOAction("CreateMeetingBoard", eloTabPages);
        eloActions.put(1, eloAction);

        eloTabPages = createME1();
        eloAction = new ELOAction("CreateMeeting", eloTabPages);
        eloActions.put(2, eloAction);

        eloTabPages = createMI1();
        eloAction = new ELOAction("CreateMeetingItem", eloTabPages);
        eloActions.put(3, eloAction);

        eloTabPages = createMB2Premium();
        eloAction = new ELOAction("CreateMeetingBoardPremium", eloTabPages);
        eloActions.put(4, eloAction);

        eloTabPages = createME1Premium();
        eloAction = new ELOAction("CreateMeetingPremium", eloTabPages);
        eloActions.put(5, eloAction);

        eloTabPages = createMI1Premium();
        eloAction = new ELOAction("CreateMeetingItemPremium", eloTabPages);
        eloActions.put(6, eloAction);

        eloTabPages = createMIP1();
        eloAction = new ELOAction("CreateMeetingItemPool", eloTabPages);
        eloActions.put(7, eloAction);

        eloTabPages = createMIPMI1();
        eloAction = new ELOAction("CreateMeetingItem", eloTabPages);
        eloActions.put(8, eloAction);

        eloTabPages = createMIP2Premium();
        eloAction = new ELOAction("CreateMeetingItemPoolPremium", eloTabPages);
        eloActions.put(9, eloAction);

        eloTabPages = createMIPMI1Premium();
        eloAction = new ELOAction("CreateMeetingItemPremium", eloTabPages);
        eloActions.put(10, eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionDefData,
                eloActionFormularData,
                eloActionData);
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
        // Create DataConfig
        // final DataConfig dataConfig = createDataConfig();
        final DataConfig dataConfig = BaseFunctions.readDataConfig("DataConfigTest.json");

        // Execute DataConfig
        WebclientSession ws = new WebclientSession(dataConfig.getEloSolutionArchiveData());
        ws.login(dataConfig.getLoginData());

        for (SortedMap.Entry<Integer, ELOAction> entryEloAction: dataConfig.getEloActionData().getEloActions().entrySet()) {
            ELOAction eloAction = entryEloAction.getValue();
            String actionName = eloAction.getActionName();
            Map<String, TabPage> tabPages = eloAction.getTabPages();

            // Get Action Definition
            ELOActionDef eloActionDef = dataConfig.getEloActionDefData().getEloActionDefs().get(actionName);

            // Execute Action
            ws.selectSolutionsFolder();
            ws.executeAction(actionName, tabPages, dataConfig.getEloActionFormularData(), eloActionDef);
        }

        ws.getPage().pause();
        ws.close();
    }
    @Test
    public void TestJson() {
        // Create DataConfig
        final DataConfig dataConfig = createDataConfig();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(dataConfig);
        System.out.println(json);

        // Save DataConfig
        try (FileWriter writer = new FileWriter("DataConfig.json")) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read DataConfig
        gson = new Gson();

        System.out.println("Reading DataConfig.json");
        System.out.println("-".repeat(100));

        try(BufferedReader br = new BufferedReader(new FileReader("DataConfig.json"))) {
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
    public void secondScript() {
        page.navigate("http://" + "ruberg-meeting.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        page.pause(); // Start Codegen
    }
}
