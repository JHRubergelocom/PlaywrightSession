package session;

import java.util.Map;

public class ELOAction {
    private final ELOActionDef eloActionDef;
    private final Map<String, TabPage> tabPages;

    public ELOAction(ELOActionDef eloActionDef, Map<String, TabPage> tabPages) {
        this.eloActionDef = eloActionDef;
        this.tabPages = tabPages;
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
                "eloActionDef=" + eloActionDef +
                ", tabPages=" + tabPages +
                '}';
    }
}
