package session;

import java.util.List;

public class ELODeleteData {
    private final List<String> arcPaths;

    public ELODeleteData(List<String> arcPaths) {
        this.arcPaths = arcPaths;
    }

    public List<String> getArcPaths() {
        return arcPaths;
    }

    @Override
    public String toString() {
        return "ELODeleteData{" +
                "arcPaths=" + arcPaths +
                '}';
    }
}
