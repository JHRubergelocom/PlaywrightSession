package session;

import java.util.ArrayList;

public class DataConfig {
    private final LoginData loginData;
    private final ELOSolutionArchiveData eloSolutionArchiveData;
    private final ELOActionData eloActionData;
    public DataConfig() {
        this.loginData = new LoginData(new ELOControl("", "", ELOControlType.TEXT),
                                       new ELOControl("", "", ELOControlType.TEXT),
                                       new ELOControl("", "", ELOControlType.BUTTON), "");
        this.eloSolutionArchiveData = new ELOSolutionArchiveData("", "");
        this.eloActionData = new ELOActionData(new ArrayList<>());
    }
    public DataConfig(LoginData loginData, ELOSolutionArchiveData eloSolutionArchiveData, ELOActionData eloActionData) {
        this.loginData = loginData;
        this.eloSolutionArchiveData = eloSolutionArchiveData;
        this.eloActionData = eloActionData;
    }
    public LoginData getLoginData() {
        return loginData;
    }
    public ELOSolutionArchiveData getEloSolutionArchiveData() {
        return eloSolutionArchiveData;
    }
    public ELOActionData getEloActionData() {
        return eloActionData;
    }

    @Override
    public String toString() {
        return "DataConfig{" +
                "loginData=" + loginData +
                ", eloSolutionArchiveData=" + eloSolutionArchiveData +
                ", eloActionData=" + eloActionData +
                '}';
    }
}

