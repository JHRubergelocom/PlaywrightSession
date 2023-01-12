package session;

public class ELOActionDef {
    private final String selectorRibbon;
    private final String selectorMenu;
    private final String selectorButton;
    public ELOActionDef(String selectorRibbon, String selectorMenu, String selectorButton) {
        this.selectorRibbon = selectorRibbon;
        this.selectorMenu = selectorMenu;
        this.selectorButton = selectorButton;
    }
    public String getSelectorRibbon() {
        return selectorRibbon;
    }
    public String getSelectorMenu() {
        return selectorMenu;
    }
    public String getSelectorButton() {
        return selectorButton;
    }
}
