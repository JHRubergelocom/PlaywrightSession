package session;

import java.util.Map;

public class ELOInputData {
    private final Map<String, Map<String, TabPage>> eloActions;

    public ELOInputData(Map<String, Map<String, TabPage>> eloActions) {
        this.eloActions = eloActions;
    }
}
