package session;

import java.util.List;

public class ELOExecuteRf {
    private final List<ELORf> eloRfs;

    public ELOExecuteRf(List<ELORf> eloRfs) {
        this.eloRfs = eloRfs;
    }

    public List<ELORf> getEloRfs() {
        return eloRfs;
    }

    @Override
    public String toString() {
        return "ELOExecuteRf{" +
                "eloRfs=" + eloRfs +
                '}';
    }
}
