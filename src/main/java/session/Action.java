package session;

import com.microsoft.playwright.Locator;

public class Action {
    private final WebclientSession ws;
    private final Locator ribbonLocator;
    private final Locator menuLocator;
    private final Locator buttonLocator;
    public Action(WebclientSession ws,
                  String action,
                  String selectorRibbonNew,
                  String selectorMenuMeeting,
                  String selectorMenuMeetingPremium,
                  String selectorButtonCreateMeetingBoard,
                  String selectorButtonCreateMeetingBoardPremium,
                  String selectorButtonCreateMeeting,
                  String selectorButtonCreateMeetingPremium,
                  String selectorButtonCreateMeetingItem,
                  String selectorButtonCreateMeetingItemPremium,
                  String selectorButtonCreateMeetingItemPool,
                  String selectorButtonCreateMeetingItemPoolPremium) {
        this.ws = ws;
        switch (action) {
            case "CreateMeetingBoard" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeeting);   // Meeting
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingBoard);  // Neues Meetingboard
            }
            case "CreateMeetingBoardPremium" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeetingPremium);   // Sitzung
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingBoardPremium);  // Neues Meetingboard
            }
            case "CreateMeeting" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeeting);   // Meeting
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeeting);  // Neues Meeting
            }
            case "CreateMeetingPremium" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeetingPremium);   // Sitzung
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingPremium);  // Neue Sitzung
            }
            case "CreateMeetingItem" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeeting);   // Meeting
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingItem);  // Neues Thema
            }
            case "CreateMeetingItemPremium" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeetingPremium);   // Sitzung
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingItemPremium);  // Neuer TOP
            }
            case "CreateMeetingItemPool" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeeting);   // Meeting
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingItemPool);  // Neue Themensammlung
            }
            case "CreateMeetingItemPoolPremium" -> {
                ribbonLocator = ws.getPage().locator(selectorRibbonNew); // Neu
                menuLocator = ws.getPage().locator(selectorMenuMeetingPremium);   // Sitzung
                buttonLocator = ws.getPage().locator(selectorButtonCreateMeetingItemPoolPremium);  // Neuer Themensammlung
            }
            default -> {
                ribbonLocator = ws.getPage().locator("");
                menuLocator = ws.getPage().locator("");
                buttonLocator = ws.getPage().locator("");
            }
        }
    }
    public void startFormula() {
        ws.click(ribbonLocator);
        ws.click(menuLocator);
        BaseFunctions.sleep();
        ws.click(buttonLocator);
        BaseFunctions.sleep();
    }
}
