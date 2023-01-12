package session;


import com.microsoft.playwright.Locator;

public class Login {
    private final WebclientSession ws;
    private final Locator usernameLocator;
    private final Locator passwordLocator;
    private final Locator loginButtonLocator;
    public Login(WebclientSession ws, String stack, String selectorUsername, String selectorPassword, String selectorLoginButton) {
        this.ws = ws;
        usernameLocator = ws.getPage().locator(selectorUsername);
        passwordLocator = ws.getPage().locator(selectorPassword);
        loginButtonLocator = ws.getPage().locator(selectorLoginButton);
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