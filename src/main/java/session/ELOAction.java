package session;

import java.util.Map;

public class ELOAction {
    private final String entryPath;
    private final FormulaType formulaType;
    private final String formulaSaveButton;
    private final ELOActionDef eloActionDef;
    private final Map<String, TabPage> tabPages;
    public ELOAction(String entryPath, FormulaType formulaType, String formulaSaveButton, ELOActionDef eloActionDef, Map<String, TabPage> tabPages) {
        this.entryPath = entryPath;
        this.formulaType = formulaType;
        this.formulaSaveButton = formulaSaveButton;
        this.eloActionDef = eloActionDef;
        this.tabPages = tabPages;
    }

    public String getFormulaSaveButton() {
        return formulaSaveButton;
    }
    public String getEntryPath() {
        return entryPath;
    }
    public FormulaType getFormulaType() {
        return formulaType;
    }

    public ELOActionDef getEloActionDef() {
        return eloActionDef;
    }

    public Map<String, TabPage> getTabPages() {
        return tabPages;
    }

    @Override
    public String toString() {
        return "ELOAction{" +
                "entryPath='" + entryPath + '\'' +
                ", formulaType=" + formulaType +
                ", formulaSaveButton='" + formulaSaveButton + '\'' +
                ", eloActionDef=" + eloActionDef +
                ", tabPages=" + tabPages +
                '}';
    }
}
