package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.elo.ix.client.IXConnection;
import de.elo.ix.client.WFDiagram;
import eloix.ELOIxConnection;
import eloix.RepoUtils;
import eloix.RfUtils;
import eloix.WfUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import session.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlaywrightHrTest {
    private List<TabPage> createEmployee() {
        List<TabPage> tabPages = new ArrayList<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_FIRSTNAME", "Hans", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_LASTNAME", "Hansen", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> checkStatusO() {

        List<TabPage> tabPages = new ArrayList<>();

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();
        expectedValueControls.add(new ELOControl("IX_GRP_HR_PERSONNEL_PERSONNELSTATUS", "O - In Vorbereitung", ELOControlType.KWL));

        TabPage tabPage = new TabPage("Personal", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> editEmployee() {

        List<TabPage> tabPages = new ArrayList<>();

        // Personal
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_WORKSCHEDULE", "Teilzeit", ELOControlType.KWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_LICENSEPLATE", "S-AA 1234", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Personal", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        // Persönlich
        initTabPage = new ArrayList<>();

        controls = new ArrayList<>();
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_BIRTHDAY", "25.03.1960", ELOControlType.TEXT));

        tables = new ArrayList<>();
        expectedValueControls = new ArrayList<>();

        tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> startOnBoarding() {
        List<TabPage> tabPages = new ArrayList<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_SUPERIOR", "Gerd Baum", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_JOBTITLE", "Consultant", ELOControlType.TEXT));

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_COMPANY", "Contelo AG", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DIVISION", "Projekte & Services", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DEPARTMENT", "Consulting", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_TEAM", "", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DATEOFJOINING", "#nowdate#", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> checkEmployeeOnBoardingStatus() {

        List<TabPage> tabPages = new ArrayList<>();

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();
        expectedValueControls.add(new ELOControl("IX_GRP_HR_PERSONNEL_PERSONNELSTATUS", "E - Angestellt", ELOControlType.KWL));

        TabPage tabPage = new TabPage("Personal", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;

    }
    private List<TabPage> checkStatusC() {

        List<TabPage> tabPages = new ArrayList<>();

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();
        expectedValueControls.add(new ELOControl("IX_GRP_HR_PERSONNEL_PERSONNELSTATUS", "C - Gekündigt", ELOControlType.KWL));

        TabPage tabPage = new TabPage("Personal", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;

    }
    private List<TabPage> startOffBoarding() {
        List<TabPage> tabPages = new ArrayList<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_DATEOFNOTICE", "#nowdate#", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DATEOFLEAVING", "#nowdate#", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_BLOB_HR_PERSONNEL_TERMINATIONCOMMENT", "Mitarbeiterkündifung", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> createEmployeeFL(String firstname, String lastname, String responsible) {
        List<TabPage> tabPages = new ArrayList<>();

        // "" (Nur eine tabPage)

        List<ELOControl> initTabPage = new ArrayList<>();
        List<ELOControl> controls = new ArrayList<>();

        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_FIRSTNAME", firstname, ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_LASTNAME", lastname, ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_RESPONSIBLE", responsible, ELOControlType.DYNKWL));

        List<ELOTable> tables = new ArrayList<>();

        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private String getEntryPath(String firstname, String lastname) {
        return "Solutions/Personalmanagement/Personalakten/" + lastname.charAt(0) + "/" + lastname + ", " + firstname;
    }
    private List<TabPage> editEmployeeLG() {

        List<TabPage> tabPages = new ArrayList<>();

        // Persönlich
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TITLE", "Dipl.-Soz.päd. (DH)", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_STREETADDRESS", "Franz-Joseph-Spiegler-Straße 75", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_POSTALCODE", "97013", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_CITY", "Bad Gottleuba-Berggießhübel", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_STATE", "Baden-Württemberg", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_COUNTRY", "Deutschland", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BUSINESSEMAIL", "a.boehm@contelo.com", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PRIVATEEMAIL", "a_boehm@web.de", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BUSINESSPHONENUMBER", "+49 731-123456", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PRIVATEPHONENUMBER", "07522 987654", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BUSINESSMOBILENUMBER", "(+49)176 12345678", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PRIVATEMOBILENUMBER", "0160-12345698", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_GENDER", "Weiblich", ELOControlType.KWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_FIRSTLANGUAGE", "Deutsch", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_BIRTHDAY", "23.12.1999", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHPLACE", "Krätze", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHNAME", "Schnarrenberger", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_NATIONALITY", "Deutschland", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_RELIGIOUSDENOMINATION", "Katholisch", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_CIVILSTATUS", "Verheiratet", ELOControlType.KWL));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        // Personal
        initTabPage = new ArrayList<>();

        controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PHYSICALFILINGLOCATION", "143581 - 154566", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_LICENSEPLATE", "S-AA 1234", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TIN", "99 999 999 999", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_SOCIALSECURITYNUMBER", "12 123456 W 123", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_HEALTHINSURANCENUMBER", "A123456789", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BANK", "COMMERZBANK AG", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_IBAN", "DE 99 12345678 987654321", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_CURRENCY", "Euro", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BANK_COUNTRY", "Deutschland", ELOControlType.DYNKWL));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_WORKSCHEDULE", "Vollzeit", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_VACATIONQUOTA", "30", ELOControlType.TEXT));

        tables = new ArrayList<>();
        expectedValueControls = new ArrayList<>();

        tabPage = new TabPage("Personal", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        // Ein-/ Austritt
        initTabPage = new ArrayList<>();

        controls = new ArrayList<>();
        controls.add(new ELOControl("unbefristet", "true", ELOControlType.RADIO));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_DATEOFJOINING", "01.04.2023", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PERIODOFNOTICE", "3", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PERIODOFNOTICE_UNIT", "Monate", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PERIODOFNOTICE_TP", "Monatsende", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PROBATIONARYPERIODDURATION", "6", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PROBATIONARYPERIODDURATION_UNIT", "Monate", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PERIODOFNOTICEPROBATIONARY", "1", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_PERIODOFNOTICEPROBATIONARY_UNIT", "Monate", ELOControlType.KWL));

        tables = new ArrayList<>();
        expectedValueControls = new ArrayList<>();

        tabPage = new TabPage("Ein-/ Austritt", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> editEmployeeBK() {

        List<TabPage> tabPages = new ArrayList<>();

        // Persönlich
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TITLE", "Master of Disaster Management and Risk Governance", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_NAMEAFFIX", "Freiherr von und zu Guttenberg", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_CITY", "Buckow (Märkische Schweiz)", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHPLACE", "São Paulo", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> editEmployeeBA() {

        List<TabPage> tabPages = new ArrayList<>();

        // Persönlich
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TITLE", "M. Comp. Sc.", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_CITY", "Bad Wörishofen", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_GENDER", "Weiblich", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHPLACE", "Kyōto", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> editEmployeeMF() {

        List<TabPage> tabPages = new ArrayList<>();

        // Persönlich
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_CITY", "Auerbach in der Oberpfalz", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_GENDER", "Divers", ELOControlType.KWL));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHPLACE", "Ecatepec de Morelos", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_COUNTRY", "Mexiko", ELOControlType.DYNKWL));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> editEmployeeS() {

        List<TabPage> tabPages = new ArrayList<>();

        // Persönlich
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TITLE", "Dr. rer. biol. vet.", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_CITY", "Frankenberg/Sa.", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHPLACE", "Ciudad Juárez", ELOControlType.TEXT));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private List<TabPage> editEmployeeCS() {

        List<TabPage> tabPages = new ArrayList<>();

        // Persönlich
        List<ELOControl> initTabPage = new ArrayList<>();

        List<ELOControl> controls = new ArrayList<>();
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_TITLE", "Dr.", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_CITY", "Xi’an", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_MAP_HR_PERSONNEL_BIRTHPLACE", "Bogotá", ELOControlType.TEXT));
        controls.add(new ELOControl("IX_GRP_HR_PERSONNEL_COUNTRY", "Japan", ELOControlType.DYNKWL));

        List<ELOTable> tables = new ArrayList<>();
        List<ELOControl>  expectedValueControls = new ArrayList<>();

        TabPage tabPage = new TabPage("Persönlich", initTabPage, controls, tables, expectedValueControls);
        tabPages.add(tabPage);

        return tabPages;
    }
    private DataConfig createDataConfigCreateFile() {
        // ELO Action Def Data
        final String selectorRibbonNew = "Neu";
        final String selectorMenuPersonnel = "Personal";
        final String selectorButtonNewEmployee = "Neuer Mitarbeiter";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Bodo Kraft", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        List<TabPage> eloTabPages = createEmployee();
        ELOAction eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = checkStatusO();
        eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployee();
        eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        final ELODeleteData eloDeleteData = new ELODeleteData(new ArrayList<>());

        final ELOForwardWorkflow eloForwardWorkflow = new ELOForwardWorkflow(new ArrayList<>());

        final ELOExecuteRf eloExecuteRf = new ELOExecuteRf(new ArrayList<>());

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData,
                eloForwardWorkflow,
                eloExecuteRf);

    }
    private DataConfig createDataConfigStartOnBoarding() {
        // ELO Action Def Data
        final String selectorRibbonNew = "Personal";
        final String selectorMenuPersonnel = "Personal";
        final String selectorButtonStartOnBoarding = "Eintrittsprozess";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Gerd Baum", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        List<TabPage> eloTabPages = startOnBoarding();
        ELOAction eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonStartOnBoarding), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        final ELODeleteData eloDeleteData = new ELODeleteData(new ArrayList<>());

        // Forward Workflow
        final List<String> toNodesName = new ArrayList<>();
        toNodesName.add("sol.common.wf.node.ok");
        toNodesName.add("sol.common.wf.node.approve");

        final ELOForwardWorkflow eloForwardWorkflow = new ELOForwardWorkflow(toNodesName);

        // Start AS-Direct Rule "sol.hr.PersonnelFileReminder"
        final List<ELORf> eloRfs = new ArrayList<>();
        eloRfs.add(new ELORf("RF_sol_common_service_ExecuteAsAction", "{ \"action\": \"sol.hr.PersonnelFileReminder\", \"config\": {} }"));

        final ELOExecuteRf eloExecuteRf = new ELOExecuteRf(eloRfs);

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData,
                eloForwardWorkflow,
                eloExecuteRf);
    }
    private DataConfig createDataConfigResignation() {
        // TODO createDataConfigResignation
        return new DataConfig();
    }
    private DataConfig createDataConfigDeleteData() {
        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Administrator", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        // Delete Data
        final List<String> arcPaths = new ArrayList<>();
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/H/Hansen, Hans");
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/L/Leutheusser-Schnarrenberger, Geneviève-Gabrielle");
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/B/Buhl, Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph");
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/B/Bärenfänger, Adoración");
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/M/Montaña Lucena, Françoise Andrés Evaristo");
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/S/Stephan, Annaëlle");
        arcPaths.add("ARCPATH:/Personalmanagement/Personalakten/Ç/Çamurcuoğlu, Sançar Ömür");

        final ELODeleteData eloDeleteData = new ELODeleteData(arcPaths);

        final ELOForwardWorkflow eloForwardWorkflow = new ELOForwardWorkflow(new ArrayList<>());

        final ELOExecuteRf eloExecuteRf = new ELOExecuteRf(new ArrayList<>());

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData,
                eloForwardWorkflow,
                eloExecuteRf);
    }
    private DataConfig createDataConfigFirstdayOfWorkReminder() {

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Gerd Baum", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        List<TabPage> eloTabPages = checkEmployeeOnBoardingStatus();
        ELOAction eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        final ELODeleteData eloDeleteData = new ELODeleteData(new ArrayList<>());

        // Forward Workflow
        final List<String> toNodesName = new ArrayList<>();
        toNodesName.add("sol.common.wf.node.ok");

        final ELOForwardWorkflow eloForwardWorkflow = new ELOForwardWorkflow(toNodesName);

        final ELOExecuteRf eloExecuteRf = new ELOExecuteRf(new ArrayList<>());

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData,
                eloForwardWorkflow,
                eloExecuteRf);

    }
    private DataConfig createDataConfigStartOffBoarding() {
        // TODO createDataConfigStartOffBoarding

        // ELO Action Def Data
        final String selectorRibbonNew = "Personal";
        final String selectorMenuPersonnel = "Personal";
        final String selectorButtonStartOnBoarding = "Austrittsprozess";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Gerd Baum", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        List<TabPage> eloTabPages = startOffBoarding();
        ELOAction eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonStartOnBoarding), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = checkStatusC();
        eloAction = new ELOAction("Solutions/Personalmanagement/Personalakten/H/Hansen, Hans", FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        final ELODeleteData eloDeleteData = new ELODeleteData(new ArrayList<>());

        final ELOForwardWorkflow eloForwardWorkflow = new ELOForwardWorkflow(new ArrayList<>());

        final ELOExecuteRf eloExecuteRf = new ELOExecuteRf(new ArrayList<>());

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData,
                eloForwardWorkflow,
                eloExecuteRf);

    }
    private DataConfig createDataConfigFilesAmelie() {
        // ELO Action Def Data
        final String selectorRibbonNew = "Neu";
        final String selectorMenuPersonnel = "Personal";
        final String selectorButtonNewEmployee = "Neuer Mitarbeiter";

        // Fill DataConfig
        final ELOControl textUserName = new ELOControl("Name", "Ute Schenk", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";

        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        final ELOSolutionArchiveData eloSolutionArchiveData = new ELOSolutionArchiveData("xpath=//*[@title=\"Solutions\"]", "Solutions");

        final List<ELOAction> eloActions = new ArrayList<>();

        List<TabPage> eloTabPages = createEmployeeFL("Geneviève-Gabrielle", "Leutheusser-Schnarrenberger", "HR");
        ELOAction eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployeeLG();
        eloAction = new ELOAction(getEntryPath("Geneviève-Gabrielle", "Leutheusser-Schnarrenberger"), FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);


        eloTabPages = createEmployeeFL("Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph", "Buhl", "Personal");
        eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployeeBK();
        eloAction = new ELOAction(getEntryPath("Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph", "Buhl"), FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);


        eloTabPages = createEmployeeFL("Adoración", "Bärenfänger", "sol.hr.roles.Managers");
        eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployeeBA();
        eloAction = new ELOAction(getEntryPath("Adoración", "Bärenfänger"), FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);


        eloTabPages = createEmployeeFL("Françoise Andrés Evaristo", "Montaña Lucena", "HR");
        eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployeeMF();
        eloAction = new ELOAction(getEntryPath("Françoise Andrés Evaristo", "Montaña Lucena"), FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);


        eloTabPages = createEmployeeFL("Annaëlle", "Stephan","HR");
        eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployeeS();
        eloAction = new ELOAction(getEntryPath("Annaëlle", "Stephan"), FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);


        eloTabPages = createEmployeeFL("Sançar Ömür", "Çamurcuoğlu", "Ute Schenk");
        eloAction = new ELOAction("", FormulaType.EXTERNAL, "OK", "Abbrechen","", new ELOActionDef(selectorRibbonNew, selectorMenuPersonnel, selectorButtonNewEmployee), eloTabPages);
        eloActions.add(eloAction);

        eloTabPages = editEmployeeCS();
        eloAction = new ELOAction(getEntryPath("Sançar Ömür", "Çamurcuoğlu"), FormulaType.VIEWER,"Speichern", "","", new ELOActionDef(), eloTabPages);
        eloActions.add(eloAction);

        final ELOActionData eloActionData = new ELOActionData(eloActions);

        final ELODeleteData eloDeleteData = new ELODeleteData(new ArrayList<>());

        final ELOForwardWorkflow eloForwardWorkflow = new ELOForwardWorkflow(new ArrayList<>());

        final ELOExecuteRf eloExecuteRf = new ELOExecuteRf(new ArrayList<>());

        return new DataConfig(loginData,
                eloSolutionArchiveData,
                eloActionData,
                eloDeleteData,
                eloForwardWorkflow,
                eloExecuteRf);


    }
    private DataConfig createDataConfig(String jsonFile) {
        switch(jsonFile) {
            case "DataConfigCreateFile.json" -> {return createDataConfigCreateFile();}
            case "DataConfigStartOnBoarding.json" -> {return createDataConfigStartOnBoarding();}
            case "DataConfigResignation.json" -> {return createDataConfigResignation();}
            case "DataConfigDeleteData.json" -> {return createDataConfigDeleteData();}
            case "DataConfigFirstdayOfWorkReminder.json" -> {return createDataConfigFirstdayOfWorkReminder();}
            case "DataConfigStartOffBoarding.json" -> {return createDataConfigStartOffBoarding();}
            case "DataConfigCreateFilesAmelie.json" -> {return createDataConfigFilesAmelie();}
        }
        return new DataConfig();
    }
    private List<WFDiagram> getFinishedWorkflows(IXConnection ixConn) {
        System.out.println("*".repeat(30) + "Finished Workflows" + "*".repeat(30));
        List<WFDiagram> finishedWorkflows = WfUtils.getFinishedWorkflows(ixConn);
        for (WFDiagram wf: finishedWorkflows) {
            System.out.println("wf.getId(): " + wf.getId());
            System.out.println("wf.getName(): " + wf.getName());
            System.out.println("wf.getNameTranslationKey(): " + wf.getNameTranslationKey());
            System.out.println("wf.getTemplateName(): " + wf.getTemplateName());
            System.out.println("-".repeat(60));
        }
        System.out.println("*".repeat(30) + "Finished Workflows" + "*".repeat(30));
        return finishedWorkflows;
    }
    @ParameterizedTest
    @ValueSource(strings = {"DataConfigCreateFile.json", "DataConfigStartOnBoarding.json", "DataConfigDeleteData.json", "DataConfigFirstdayOfWorkReminder.json", "DataConfigStartOffBoarding.json", "DataConfigCreateFilesAmelie.json"})
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
    @ParameterizedTest
    @ValueSource(strings = {"DataConfigCreateFilesAmelie.json"})
    public void TestSession(String jsonFile) {
        WebclientSession.execute(jsonFile, "PlaywrightConfig.json");
    }
    @Test
    public void testForwardWorkflow() {

        // Fill LoginData
        final ELOControl textUserName = new ELOControl("Name", "Gerd Baum", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";
        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        // Ix Connect
        IXConnection ixConn;
        try {
            ixConn = ELOIxConnection.getIxConnection(loginData, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("IxConn: " + ixConn);
        System.out.println( "ixConn.getLoginResult().getUser(): " + ixConn.getLoginResult().getUser());

        // Forward Workflow
        try {
            WfUtils.forwardWorkflow(ixConn, "sol.common.wf.node.approve");
        } catch (Exception ex) {
            System.err.println("testForwardWorkflow message: " +  ex.getMessage());
        }

        ixConn.close();
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
            ixConn = ELOIxConnection.getIxConnection(loginData, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("IxConn: " + ixConn);
        System.out.println( "ixConn.getLoginResult().getUser(): " + ixConn.getLoginResult().getUser());

        // Delete arcpath
        RepoUtils.DeleteSord(ixConn, arcPath);

        // Remove Workflows
        List<WFDiagram> finishedWorkflows = getFinishedWorkflows(ixConn);
        WfUtils.removeFinishedWorkflows(ixConn, finishedWorkflows);

        ixConn.close();
    }
    @Test
    public void testDateTime() {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS");
        String value = simpleDateFormat.format(nowDate);

        System.out.println("Aktuelles Datum: " + value);
        BaseFunctions.getTimeStamp();

    }
    @Test
    public void testEntryPath() {
        String firstname = "Geneviève-Gabrielle";
        String lastname = "Leutheusser-Schnarrenberger";

        System.out.println("firstname: " + firstname);
        System.out.println("lastname: " + lastname);

        System.out.println("EntryPath: " + getEntryPath(firstname, lastname));

    }
    @Test
    public void testExecuteRF() {

        // Fill LoginData
        final ELOControl textUserName = new ELOControl("Name", "Gerd Baum", ELOControlType.TEXT);
        final ELOControl textPassword = new ELOControl("Passwort", "elo", ELOControlType.TEXT);
        final ELOControl buttonLogin = new ELOControl("Anmelden", "Login", ELOControlType.BUTTON);
        final String stack = "ruberg-hr.dev.elo";
        final LoginData loginData = new LoginData(textUserName, textPassword, buttonLogin,stack);

        // Ix Connect
        IXConnection ixConn;
        try {
            ixConn = ELOIxConnection.getIxConnection(loginData, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("IxConn: " + ixConn);
        System.out.println( "ixConn.getLoginResult().getUser(): " + ixConn.getLoginResult().getUser());

        // Execute RF
        try {
            String funcName = "RF_sol_common_service_ExecuteAsAction";
            String jsonParam = "{ \"action\": \"sol.hr.PersonnelFileReminder\", \"config\": {} }";
            ELORf eloRf = new ELORf(funcName, jsonParam);
            System.out.println("eloRf " + eloRf);
            String rfResult = RfUtils.executeRF(ixConn, eloRf);
            System.out.println("rfResult: " + rfResult);
        } catch (Exception ex) {
            System.err.println("testForwardWorkflow message: " +  ex.getMessage());
        }
        ixConn.close();
    }
}
