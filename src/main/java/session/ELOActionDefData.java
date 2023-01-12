package session;

import java.util.Map;

public class ELOActionDefData {
    private final Map<String, ELOActionDef> eloActionDefs;
    public ELOActionDefData(Map<String, ELOActionDef> eloActionDefs) {
        this.eloActionDefs = eloActionDefs;
    }
    public Map<String, ELOActionDef> getEloActionDefs() {
        return eloActionDefs;
    }
}
