package session;

public class LoginData {
    private final ELOControl textUserName;
    private final ELOControl textPassword;
    private  final ELOControl buttonLogin;
    private  final String stack;

    public LoginData(ELOControl textUserName, ELOControl textPassword, ELOControl buttonLogin, String stack) {
        this.textUserName = textUserName;
        this.textPassword = textPassword;
        this.buttonLogin = buttonLogin;
        this.stack = stack;
    }

    public ELOControl getTextUserName() {
        return textUserName;
    }

    public ELOControl getTextPassword() {
        return textPassword;
    }

    public ELOControl getButtonLogin() {
        return buttonLogin;
    }

    public String getStack() {
        return stack;
    }
}
