package session;

import java.util.List;

public class TabPage {
    private final String tabName;
    private final List<ELOControl> initTabPage;
    private final List<ELOControl> controls;
    private final List<ELOTable> tables;
    private final List<ELOCheckValueControl> checkValueControls;
    public TabPage(String tabName, List<ELOControl> initTabPage, List<ELOControl> controls, List<ELOTable> tables, List<ELOCheckValueControl> checkValueControls) {
        this.tabName = tabName;
        this.initTabPage = initTabPage;
        this.controls = controls;
        this.tables = tables;
        this.checkValueControls = checkValueControls;
    }
    public String getTabName() {
        return tabName;
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
    public List<ELOCheckValueControl> getCheckValueControls() {
        return checkValueControls;
    }

    @Override
    public String toString() {
        return "TabPage{" +
                "tabName='" + tabName + '\'' +
                ", initTabPage=" + initTabPage +
                ", controls=" + controls +
                ", tables=" + tables +
                ", checkValueControls=" + checkValueControls +
                '}';
    }
}
