package session;

import java.util.List;

public class TabPage {
    private final List<ELOControl> initTabPage;
    private final List<ELOControl> controls;
    private final List<List<ELOControl>> table;
    private final String addLineButtonName;
    public TabPage(List<ELOControl> initTabPage, List<ELOControl> controls, List<List<ELOControl>> table, String addLineButtonName) {
        this.initTabPage = initTabPage;
        this.controls = controls;
        this.table = table;
        this.addLineButtonName = addLineButtonName;
    }
    public List<List<ELOControl>> getTable() {
        return table;
    }
    public String getAddLineButtonName() {
        return addLineButtonName;
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
                ", table=" + table +
                ", addLineButtonName='" + addLineButtonName + '\'' +
                '}';
    }
}
