package session;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;

public class Action {
    private final WebclientSession ws;
    private final Locator ribbonLocator;
    private final Locator menuLocator;
    private final Locator buttonLocator;

    public Action(WebclientSession ws, String action) {
        this.ws = ws;
        switch (action) {
            case "CreateMeetingBoard" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1280-btnIconEl\"]");   // Meeting
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1274-textEl\"]");  // Neues Meetingboard
            }
            case "CreateMeetingBoardPremium" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1288-btnIconEl\"]");   // Sitzung
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1281-textEl\"]");  // Neues Meetingboard
            }
            case "CreateMeeting" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1280-btnIconEl\"]");   // Meeting
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1275-textEl\"]");  // Neues Meeting
            }
            case "CreateMeetingPremium" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1288-btnIconEl\"]");   // Sitzung
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1282-textEl\"]");  // Neue Sitzung
            }
            case "CreateMeetingItem" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1280-btnIconEl\"]");   // Meeting
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1276-textEl\"]");  // Neues Thema
            }
            case "CreateMeetingItemPremium" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1288-btnIconEl\"]");   // Sitzung
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1284-textEl\"]");  // Neuer TOP
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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ws.click(buttonLocator);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
