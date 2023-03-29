package session;

import java.util.List;

public class TabPage {
    private final List<ELOControl> initTabPage;
    private final List<ELOControl> controls;
    private final List<ELOTable> tables;
    private final List<ELOControl> expectedValueControls;
    public TabPage(List<ELOControl> initTabPage, List<ELOControl> controls, List<ELOTable> tables, List<ELOControl> expectedValueControls) {
        this.initTabPage = initTabPage;
        this.controls = controls;
        this.tables = tables;
        this.expectedValueControls = expectedValueControls;
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
    public List<ELOControl> getExpectedValueControls() {
        return expectedValueControls;
    }
    @Override
    public String toString() {
        return "TabPage{" +
                "initTabPage=" + initTabPage +
                ", controls=" + controls +
                ", tables=" + tables +
                ", expectedValueControls=" + expectedValueControls +
                '}';
    }
}
