package session;

import java.util.List;

public class ELOActionData {
    private final List<ELOAction> eloActions;

    public ELOActionData(List<ELOAction> eloActions) {
        this.eloActions = eloActions;
    }

    public List<ELOAction> getEloActions() {
        return eloActions;
    }

    @Override
    public String toString() {
        return "ELOActionData{" +
                "eloActions=" + eloActions +
                '}';
    }
}
