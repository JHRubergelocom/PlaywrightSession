package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import de.elo.ix.client.IXConnection;
import eloix.ELOIxConnection;
import eloix.RepoUtils;
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

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> editEmployee1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TITLE", "Doktor H", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_GENDER", "Männlich", ELOControlType.KWL));

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();

        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_FAMILYMEMBER_FIRSTNAME", "Hugo", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_FAMILYMEMBER_LASTNAME", "Egon", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_FAMILYMEMBER_FIRSTNAME", "Sandra", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_FAMILYMEMBER_LASTNAME", "Renz", ELOControlType.TEXT));
        table.add(tableLine);

        tables.add(new ELOTable("part_240_family_members", "Eintrag hinzufügen", table));

        table = new ArrayList<>();

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_FIRSTNAME", "Heinrich", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_LASTNAME", "Müller", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_FIRSTNAME", "Sebastian", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_LASTNAME", "Schulz", ELOControlType.TEXT));
        table.add(tableLine);

        tables.add(new ELOTable("part_250_emergency_contacts", "Eintrag hinzufügen", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Persönlich", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createCompany1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_COMPANY", "Firma Hansens", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createChartElementDivision1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DIVISION", "Orgastruktur1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_CHARTELEMENT_DESC", "Beschreibung Orgastruktur1", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createChartElementDepartement1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DEPARTMENT", "Abteilung1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_CHARTELEMENT_DESC", "Beschreibung Beschreibung Abteilung1", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("", tabPage);

        return tabPages;

    }
    private Map<String, TabPage> createChartElementTeam1() {

        Map<String, TabPage> tabPages = new TreeMap<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_TEAM", "Team1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_CHARTELEMENT_DESC", "Beschreibung Team1", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();

        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Jan Eichner", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Sandra Renz", ELOControlType.DYNKWL));

        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Person", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        tables = new ArrayList<>();
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

        tables.add(new ELOTable("", "Weiteres Thema", table));

        tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Themen", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        tables = new ArrayList<>();
        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Benachrichtigung", table));

        tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_SETTING_ITEMTOAGENDA", "true", ELOControlType.CHECKBOX));


        tables = new ArrayList<>();

        tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "09:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "17:00", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "09:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "17:00", ELOControlType.TEXT));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weiterer Tag", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Allgemein", tabPage);

        // Teilnehmende
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        tables = new ArrayList<>();
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

        tables.add(new ELOTable("", "Weiterer Gast", table));

        tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Teilnehmende", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        tables = new ArrayList<>();
        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Meeting-Einladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Benachrichtigung", table));

        tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();

        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Kraft", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Bodo", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Person", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Kraft", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Bodo", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Person", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Jan Eichner", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_BOARD_ORGANIZER", "Sandra Renz", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Person", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        tables = new ArrayList<>();
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

        tables.add(new ELOTable("", "Weiterer TOP", table));

        tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Tagesordnungspunkte", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        tables = new ArrayList<>();
        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Benachrichtigung", table));

        tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Benachrichtigungen", tabPage);

        // Einstellungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_MEETING_DEFAULT_NAME", "Meeting2", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_MEETING_BOARD_SETTING_APPROVAL_REQUIRED", "false", ELOControlType.CHECKBOX));

        tables = new ArrayList<>();

        tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "11:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "16:00", ELOControlType.TEXT));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_START", "09:00", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_TIMESLOT_END", "17:00", ELOControlType.TEXT));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weiterer Tag", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Allgemein", tabPage);

        // Teilnehmende
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        tables = new ArrayList<>();
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

        tables.add(new ELOTable("", "Weiterer Gast", table));

        tabPage = new TabPage(initTabPage, controls, tables);
        tabPages.put("Teilnehmende", tabPage);

        // Benachrichtigungen
        initTabPage = new ArrayList<>();
        controls = new ArrayList<>();

        tables = new ArrayList<>();
        table = new ArrayList<>();
        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "O", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "y", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "-", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_STARTDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_TEMPLATE_", "Sitzungseinladung", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_RECIPIENTS_", "A", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_VALUE_", "1", ELOControlType.TEXT));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SHIFT_UNIT_", "Q", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_SIGN_", "+", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_NOTIFICATION_REFERENCE_DATE_", "MEETING_ENDDATE", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Benachrichtigung", table));

        tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Bennett", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Charlotte", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Person", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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

        List<ELOTable> tables = new ArrayList<>();
        List<List<ELOControl>> table = new ArrayList<>();
        List<ELOControl> tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Bennett", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Charlotte", ELOControlType.DYNKWL));
        table.add(tableLine);

        tableLine = new ArrayList<>();
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_LASTNAME", "Davis", ELOControlType.DYNKWL));
        tableLine.add(new ELOControl("IX_MAP_MEETING_ITEM_SPEAKER_FIRSTNAME", "Jessica", ELOControlType.DYNKWL));
        table.add(tableLine);

        tables.add(new ELOTable("", "Weitere Person", table));

        TabPage tabPage = new TabPage(initTabPage, controls, tables);
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
        final String selectorButtonNewChartElement = "Organisation erweitern";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Administrator", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        Map<String, TabPage> eloTabPages = createEmployee1();
        ELOAction eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createCompany1();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewCompany), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployee1();
        eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.VIEWER,"Speichern", "", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createChartElementDivision1();
        eloAction = new ELOAction("Solutions/Firma Hansens", FormulaType.EXTERNAL,"OK", "Neuer Organisationsbereich", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewChartElement), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createChartElementDepartement1();
        eloAction = new ELOAction("Solutions/Firma Hansens", FormulaType.EXTERNAL,"OK", "Neue Abteilung", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewChartElement), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createChartElementTeam1();
        eloAction = new ELOAction("Solutions/Firma Hansens", FormulaType.EXTERNAL,"OK", "Neues Team", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewChartElement), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        // Delete Data
        final List<String> arcPaths = new ArrayList<>();
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/H/Hansen, Hans");
        arcPaths.add("ARCPATH:/Firma Hansens");

        final ELODeleteData eloDeleteData = new ELODeleteData(arcPaths);

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData);
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
        ELOAction eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingBoard), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createME1();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeeting), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMI1();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItem), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMB2Premium();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingBoardPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createME1Premium();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMI1Premium();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIP1();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItemPool), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIPMI1();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeeting, selectorButtonCreateMeetingItem), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIP2Premium();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPoolPremium), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = createMIPMI1Premium();
        eloAction = new ELOAction("", FormulaType.EXTERNAL,"OK", "", new ELOActionDef(selectorRibbonNew, selectorMenuMeetingPremium, selectorButtonCreateMeetingItemPremium), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        // Delete Data
        final List<String> arcPaths = new ArrayList<>();
        arcPaths.add("ARCPATH:/Meetingboard1");
        arcPaths.add("ARCPATH:/Meetingboard2");
        arcPaths.add("ARCPATH:/Meetingitempool1");
        arcPaths.add("ARCPATH:/Meetingitempool2");

        final ELODeleteData eloDeleteData = new ELODeleteData(arcPaths);

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData);
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
    private void selectTab(FrameLocator frameLocator, String tabName) {
        if (!tabName.equals("")) {
            System.out.println("tabname="+ tabName);
            frameLocator.getByRole(AriaRole.LINK, new FrameLocator.GetByRoleOptions().setName(tabName)).click();
        }
    }
    public void clickButton(FrameLocator frameLocator, String name) {
        BaseFunctions.sleep();
        BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(name)));
    }
    private Locator selectFolder(String folder) {
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, folder, "class", "color");
        return optionalLocator.orElseGet(() -> page.locator(""));
    }
    private void selectEntryByPath(String path) {
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
    private void clickAddLineButton(FrameLocator frameLocator, String selectorTable, String addLineButtonName) {
        if (!selectorTable.equals("")) {
            BaseFunctions.click(frameLocator.locator("#"+ selectorTable).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(addLineButtonName)));
        } else {
            BaseFunctions.click(frameLocator.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName(addLineButtonName)));
        }
        BaseFunctions.sleep();
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
        context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("")));

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
    @ValueSource(strings = {"DataConfigTest.json"})
    public void TestSession(String jsonFile) {
        WebclientSession.execute(jsonFile, "PlaywrightConfig.json");
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
    public void testLocatorCompany() {
        page.navigate("http://" + "ruberg-hr.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        page.getByPlaceholder("Name").fill("0");
        page.getByPlaceholder("Passwort").fill("elo");
        page.getByText("Anmelden").click();

        // Kachel "Solutions" klicken
        page.locator("xpath=//*[@title=\"Solutions\"]").click();

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Button "Neue Organisation" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neue Organisation", "id", "comp");
        optionalLocator.ifPresent(Locator::click);

        // Get Frame External Formula
        BaseFunctions.sleep();
        FrameLocator frameLocator = getFrameLocator("iframe");
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_COMPANY" + "\"]"), "Firma Hansens", false);

        // Formular speichern
        clickButton(frameLocator, "OK");


        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ordner "Firma Hansens" auswählen
        selectEntryByPath("Solutions/Firma Hansens");

        // Viewer Formular auswählen
        System.out.println("*".repeat(10) + " Viewer Formular auswählen " + "*".repeat(10));

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
        System.out.println("*".repeat(10) + " Viewer Formular auswählen " + "*".repeat(10));

        // FrameLocator Viewer Formular
        frameLocator = getFrameLocator("FormularViewer");

        // Tabpage auswählen
        selectTab(frameLocator, "");

        // Beschreibung eintragen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_CHARTELEMENT_DESC" + "\"]"), "Beschreibung Firma Hansens", false);

        // Speichern
        clickButton(frameLocator, "Speichern");


        // Organisation erweitern mit Vorauswahldialog



        // "Neuer Organisationsbereich"

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ordner "Firma Hansens" auswählen
        selectEntryByPath("Solutions/Firma Hansens");

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Button "Organisation erweitern" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Organisation erweitern", "id", "comp");
        optionalLocator.ifPresent(Locator::click);

        // Vorauswahldialog "Neuer Organisationsbereich" klicken
        BaseFunctions.sleep();
        page.getByText("Neuer Organisationsbereich").click();

        // Get Frame External Formula
        BaseFunctions.sleep();
        frameLocator = getFrameLocator("iframe");
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_DIVISION" + "\"]"), "Orgastruktur1", false);

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_CHARTELEMENT_DESC" + "\"]"), "Beschreibung Orgastruktur1", false);

        // Formular speichern
        clickButton(frameLocator, "OK");


        // "Neue Abteilung"

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ordner "Firma Hansens" auswählen
        selectEntryByPath("Solutions/Firma Hansens");

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button");
        optionalLocator.ifPresent(Locator::click);


        // Button "Organisation erweitern" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Organisation erweitern", "id", "comp");
        optionalLocator.ifPresent(Locator::click);

        // Vorauswahldialog "Neue Abteilung" klicken
        BaseFunctions.sleep();
        page.getByText("Neue Abteilung").click();

        // Get Frame External Formula
        BaseFunctions.sleep();
        frameLocator = getFrameLocator("iframe");
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_DEPARTMENT" + "\"]"), "Abteilung1", false);

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_CHARTELEMENT_DESC" + "\"]"), "Beschreibung Abteilung1", false);

        // Formular speichern
        clickButton(frameLocator, "OK");


        // "Neues Team"

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ordner "Firma Hansens" auswählen
        selectEntryByPath("Solutions/Firma Hansens");

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Button "Organisation erweitern" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Organisation erweitern", "id", "comp");
        optionalLocator.ifPresent(Locator::click);

        // Vorauswahldialog "Neues Team" klicken
        BaseFunctions.sleep();
        page.getByText("Neues Team").click();

        // Get Frame External Formula
        BaseFunctions.sleep();
        frameLocator = getFrameLocator("iframe");
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_TEAM" + "\"]"), "Team1", false);

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_CHARTELEMENT_DESC" + "\"]"), "Beschreibung Team1", false);

        // Formular speichern
        clickButton(frameLocator, "OK");

        page.pause(); // Start Codegen
    }
    @Test
    public void testLocatorEmployee() {
        page.navigate("http://" + "ruberg-hr.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        page.getByPlaceholder("Name").fill("0");
        page.getByPlaceholder("Passwort").fill("elo");
        page.getByText("Anmelden").click();

        // Kachel "Solutions" klicken
        page.locator("xpath=//*[@title=\"Solutions\"]").click();

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ribbon "Neu" auswählen
        BaseFunctions.sleep();
        Optional<Locator> optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neu", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Menü "Personal" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Personal", "id", "button");
        optionalLocator.ifPresent(Locator::click);

        // Button "Neuer Mitarbeiter" auswählen
        BaseFunctions.sleep();
        optionalLocator = BaseFunctions.selectByTextAttribute(page, "Neuer Mitarbeiter", "id", "comp");
        optionalLocator.ifPresent(Locator::click);

        // Get Frame External Formula
        BaseFunctions.sleep();
        FrameLocator frameLocator = getFrameLocator("iframe");
        System.out.println("framelocator = " + frameLocator);

        // Felder füllen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_FIRSTNAME" + "\"]"), "Hans", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_LASTNAME" + "\"]"), "Hansen", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_PERSONNELNO" + "\"]"), "12345", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_ELOUSERID" + "\"]"), "Jan Eichner", true);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_RESPONSIBLE" + "\"]"), "Bodo Kraft", true);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_SUPERIOR" + "\"]"), "Gerd Baum", true);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_GRP_HR_PERSONNEL_DATEOFJOINING" + "\"]"), "20220101", false);

        // Formular speichern
        clickButton(frameLocator, "OK");

        // Ordner "Solutions" auswählen
        selectEntryByPath("Solutions");

        // Ordner "Hans Hansens" auswählen
        selectEntryByPath("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans");

        // Viewer Formular auswählen
        System.out.println("*".repeat(10) + " Viewer Formular auswählen " + "*".repeat(10));

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
        System.out.println("*".repeat(10) + " Viewer Formular auswählen " + "*".repeat(10));

        // FrameLocator Viewer Formular
        frameLocator = getFrameLocator("FormularViewer");

        // Tabpage auswählen
        selectTab(frameLocator, "Persönlich");

        // Beschreibung eintragen
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_TITLE" + "\"]"), "Doktor H", false);

        // Tabellenfelder füllen
        String addLineButtonName = "Eintrag hinzufügen";
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_FIRSTNAME1" + "\"]"), "Hugo", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_LASTNAME1" + "\"]"), "Egon", false);

        clickAddLineButton(frameLocator, "part_240_family_members", addLineButtonName);

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_FIRSTNAME2" + "\"]"), "Sandra", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_FAMILYMEMBER_LASTNAME2" + "\"]"), "Renz", false);


        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_FIRSTNAME1" + "\"]"), "Heinrich", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_LASTNAME1" + "\"]"), "Müller", false);

        clickAddLineButton(frameLocator, "part_250_emergency_contacts", "Eintrag hinzufügen");

        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_FIRSTNAME2" + "\"]"), "Sebastian", false);
        BaseFunctions.type(frameLocator.locator("[name=\"" + "IX_MAP_HR_PERSONNEL_EMERGENCYCONTACT_LASTNAME2" + "\"]"), "Schulz", false);

        // Stichwortlistenfeld Geschlecht füllen
        System.out.println("*".repeat(10) + " Stichwortlistenfeld Geschlecht füllen " + "*".repeat(10));

        Locator locator = frameLocator.locator("[inpname=\"" + "IX_GRP_HR_PERSONNEL_GENDER" + "\"]");
        locator.click();
        frameLocator.getByRole(AriaRole.CELL, new FrameLocator.GetByRoleOptions().setName("Männlich")).click();

        System.out.println("*".repeat(10) + " Stichwortlistenfeld Geschlecht füllen " + "*".repeat(10));

        // Speichern
        clickButton(frameLocator, "Speichern");

        page.pause(); // Start Codegen

    }
    @Test
    public void testLoginLanguage() {
        page.navigate("http://" + "ruberg-hr.dev.elo" + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
        Locator locator = page.locator("[name=\"" + "locale" + "\"]");
        locator.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Deutsch")).click();

        page.getByPlaceholder("Name").fill("0");
        page.getByPlaceholder("Passwort").fill("elo");
        page.getByText("Anmelden").click();

        page.pause(); // Start Codegen

    }
    @Test
    public void testEloIxConnectAndDeleteData() {

        // Fill LoginData
        final ELOControl textUserName = new ELOControl("Name", "Administrator", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";
        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);
        final String arcPath = "ARCPATH:/Personalmanagement/Personalakten/H/Hansen, Hans";

        // Ix Connect
        IXConnection ixConn;
        try {
            ixConn = ELOIxConnection.getIxConnection(loginData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("IxConn: " + ixConn);
        System.out.println( "ixConn.getLoginResult().getUser(): " + ixConn.getLoginResult().getUser());

        // Delete arcpath
        RepoUtils.DeleteSord(ixConn, arcPath);
        ixConn.close();
    }
}
