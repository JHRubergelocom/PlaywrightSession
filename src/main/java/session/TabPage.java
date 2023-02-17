package session;

import java.util.List;

public class TabPage {
    private final List<ELOControl> initTabPage;
    private final List<ELOControl> controls;
    private final List<ELOTable> tables;
    public TabPage(List<ELOControl> initTabPage, List<ELOControl> controls, List<ELOTable> tables) {
        this.initTabPage = initTabPage;
        this.controls = controls;
        this.tables = tables;
    }
    public List<ELOTable> getTables() {
        return tables;
    }
    public List<ELOControl> getControls() {
        return controls;
    }
    public List<ELOControl> getInitTabPage() {
        return initTabPage;
    }

    @Override
    public String toString() {
        return "TabPage{" +
                "initTabPage=" + initTabPage +
                ", controls=" + controls +
                ", tables=" + tables +
                '}';
    }
}
