package session;

import com.microsoft.playwright.Locator;

public class Action {
    private final WebclientSession ws;
    private final Locator ribbonLocator;
    private final Locator menuLocator;
    private final Locator buttonLocator;
    public Action(WebclientSession ws, ELOActionDef eloActionDef) {
        this.ws = ws;
        ribbonLocator = ws.getPage().locator(eloActionDef.getSelectorRibbon());
        menuLocator = ws.getPage().locator(eloActionDef.getSelectorMenu());
        buttonLocator = ws.getPage().locator(eloActionDef.getSelectorButton());
    }
    public void startFormula() {
        ws.click(ribbonLocator);
        ws.click(menuLocator);
        BaseFunctions.sleep();
        ws.click(buttonLocator);
        BaseFunctions.sleep();
    }

    @Override
    public String toString() {
        return "Action{" +
                "ws=" + ws +
                ", ribbonLocator=" + ribbonLocator +
                ", menuLocator=" + menuLocator +
                ", buttonLocator=" + buttonLocator +
                '}';
    }
}
