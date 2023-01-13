package session;

import java.util.Map;

public class ELOAction {
    private final String actionName;
    private final Map<String, TabPage> tabPages;

    public ELOAction(String actionName, Map<String, TabPage> tabPages) {
        this.actionName = actionName;
        this.tabPages = tabPages;
    }

    public String getActionName() {
        return actionName;
    }

    public Map<String, TabPage> getTabPages() {
        return tabPages;
    }

    @Override
    public String toString() {
        return "ELOAction{" +
                "actionName='" + actionName + '\'' +
                ", tabPages=" + tabPages +
                '}';
    }
}
