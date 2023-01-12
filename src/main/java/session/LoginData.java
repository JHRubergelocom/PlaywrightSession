package session;

public class LoginData {
    private final ELOControl textUserName;
    private final ELOControl textPassword;
    private  final ELOControl buttonLogin;

    public LoginData(ELOControl textUserName, ELOControl textPassword, ELOControl buttonLogin) {
        this.textUserName = textUserName;
        this.textPassword = textPassword;
        this.buttonLogin = buttonLogin;
    }
}
