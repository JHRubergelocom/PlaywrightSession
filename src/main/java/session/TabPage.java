package session;

import java.util.List;
import java.util.Map;

public class TabPage {
    private final Map<String,String> fields;
    private final List<Map<String, String>> table;
    private final String addLineButtonName;
    private final Map<String, Boolean> checkboxes;
    private final AssignmentStatus assignment;
    public AssignmentStatus getAssignment() {
        return assignment;
    }
    public TabPage(Map<String, String> fields, List<Map<String, String>> table, String addLineButtonName, Map<String, Boolean> checkboxes, AssignmentStatus assignment) {
        this.fields = fields;
        this.table = table;
        this.addLineButtonName = addLineButtonName;
        this.checkboxes = checkboxes;
        this.assignment = assignment;
    }
    public Map<String, String> getFields() {
        return fields;
    }
    public List<Map<String, String>> getTable() {
        return table;
    }
    public Map<String, Boolean> getCheckboxes() {
        return checkboxes;
    }
    public String getAddLineButtonName() {
        return addLineButtonName;
    }

    @Override
    public String toString() {
        return "TabPage{" +
                "fields=" + fields +
                ", table=" + table +
                ", addLineButtonName='" + addLineButtonName + '\'' +
                ", checkboxes=" + checkboxes +
                ", assignment=" + assignment +
                '}';
    }
}
