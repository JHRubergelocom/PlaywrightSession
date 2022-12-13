package session;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;

public class Action {
    private final WebclientSession ws;
    private final Locator ribbonLocator;
    private final Locator menuLocator;
    private final Locator buttonLocator;
    private final FrameLocator frameLocator;

    public Action(WebclientSession ws, String action) {

        this.ws = ws;
        switch (action) {
            case "CreateMeetingBoard" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1280-btnIconEl\"]");   // Meeting
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1274-textEl\"]");  // Neues Meetingboard
                frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-1782");
            }
            case "CreateMeetingBoardPremium" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1288-btnIconEl\"]");   // Sitzung
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1281-textEl\"]");  // Neues Meetingboard
                frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-1992");
            }
            case "CreateMeeting" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1280-btnIconEl\"]");   // Meeting
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1275-textEl\"]");  // Neues Meeting
                frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-1863");
            }
            case "CreateMeetingPremium" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1288-btnIconEl\"]");   // Sitzung
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1282-textEl\"]");  // Neue Sitzung
                frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-2030");
            }
            case "CreateMeetingItem" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1280-btnIconEl\"]");   // Meeting
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1276-textEl\"]");  // Neues Thema
                frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-1944");
            }
            case "CreateMeetingItemPremium" -> {
                ribbonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1218-btnIconEl\"]"); // Neu
                menuLocator = ws.getPage().locator("xpath=//*[@id=\"button-1288-btnIconEl\"]");   // Sitzung
                buttonLocator = ws.getPage().locator("xpath=//*[@id=\"ext-comp-1284-textEl\"]");  // Neuer TOP
                frameLocator = ws.getPage().frameLocator("#IFramePanelIFrame-panel-iframe-2098");
            }
            default -> {
                ribbonLocator = ws.getPage().locator("");
                menuLocator = ws.getPage().locator("");
                buttonLocator = ws.getPage().locator("");
                frameLocator = ws.getPage().frameLocator("");
            }
        }
    }

    public FrameLocator getFrameLocator() {
        return frameLocator;
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
