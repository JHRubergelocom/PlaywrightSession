package session;

import java.util.List;

public class ELOTable {
    private final String selectorTable;
    private final String addLineButtonName;
    private final List<List<ELOControl>> table;

    public ELOTable(String selectorTable, String addLineButtonName, List<List<ELOControl>> table) {
        this.selectorTable = selectorTable;
        this.addLineButtonName = addLineButtonName;
        this.table = table;
    }
    public String getSelectorTable() {
        return selectorTable;
    }
    public String getAddLineButtonName() {
        return addLineButtonName;
    }
    public List<List<ELOControl>> getTable() {
        return table;
    }
    @Override
    public String toString() {
        return "ELOTable{" +
                "selectorTable='" + selectorTable + '\'' +
                ", addLineButtonName='" + addLineButtonName + '\'' +
                ", table=" + table +
                '}';
    }
}
