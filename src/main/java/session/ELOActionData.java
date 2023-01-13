package session;

import java.util.SortedMap;

public class ELOActionData {
    private final SortedMap<Integer, ELOAction> eloActions;

    public ELOActionData(SortedMap<Integer, ELOAction> eloActions) {
        this.eloActions = eloActions;
    }

    public SortedMap<Integer, ELOAction> getEloActions() {
        return eloActions;
    }
}
