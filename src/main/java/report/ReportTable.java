package report;

import java.util.List;

public class ReportTable {
    private final List<String> tableCols;
    private final List<List<String>> tableCells;

    public ReportTable(List<String> tableCols, List<List<String>> tableCells) {
        this.tableCols = tableCols;
        this.tableCells = tableCells;
    }

    public List<String> getTableCols() {
        return tableCols;
    }

    public List<List<String>> getTableCells() {
        return tableCells;
    }

    @Override
    public String toString() {
        return "ReportTable{" +
                "tableCols=" + tableCols +
                ", tableCells=" + tableCells +
                '}';
    }
}
