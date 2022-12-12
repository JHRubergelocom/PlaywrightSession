package session;


import com.microsoft.playwright.Locator;

public class Login {
    private WebclientSession ws;
    private Locator usernameLocator;
    private Locator passwordLocator;
    private Locator loginButtonLocator;

    public Login(WebclientSession ws, String stack) {
        this.ws = ws;
        usernameLocator = ws.getPage().locator("xpath=//*[@id=\"field-focustext-1020-inputEl\"]");
        passwordLocator = ws.getPage().locator("xpath=//*[@id=\"textfield-1021-inputEl\"]");
        loginButtonLocator = ws.getPage().locator("xpath=//*[@id=\"button-1023-btnIconEl\"]");
        this.ws.visit("http://" + stack + "/ix-Solutions/plugin/de.elo.ix.plugin.proxy/web/");
    }

    public void typeUsername(String username) {
        ws.type(usernameLocator, username);
    }

    public void typePassword(String password) {
        ws.type(passwordLocator, password);
    }

    public void clickLoginButton() {
        ws.click(loginButtonLocator);
    }
}
