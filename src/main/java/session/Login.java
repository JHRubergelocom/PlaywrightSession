package session;


import com.microsoft.playwright.Locator;

public class Login {
    private final WebclientSession ws;
    private final Locator usernameLocator;
    private final Locator passwordLocator;
    private final Locator loginButtonLocator;
    public Login(WebclientSession ws, String stack, String selectorUsername, String selectorPassword, String selectorLoginButton) {
        this.ws = ws;
        usernameLocator = ws.getPage().getByPlaceholder(selectorUsername);
        passwordLocator = ws.getPage().getByPlaceholder(selectorPassword);
        loginButtonLocator = ws.getPage().getByText(selectorLoginButton);
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

    @Override
    public String toString() {
        return "Login{" +
                "ws=" + ws +
                ", usernameLocator=" + usernameLocator +
                ", passwordLocator=" + passwordLocator +
                ", loginButtonLocator=" + loginButtonLocator +
                '}';
    }
}