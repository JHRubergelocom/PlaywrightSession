package session;

import java.util.ArrayList;

public class DataConfig {
    private final LoginData loginData;
    private final ELOSolutionArchiveData eloSolutionArchiveData;
    private final ELOActionData eloActionData;
    private final ELODeleteData eloDeleteData;
    public DataConfig() {
        this.loginData = new LoginData();
        this.eloSolutionArchiveData = new ELOSolutionArchiveData();
        this.eloActionData = new ELOActionData();
        this.eloDeleteData = new ELODeleteData(new ArrayList<>());
    }
    public DataConfig(LoginData loginData, ELOSolutionArchiveData eloSolutionArchiveData, ELOActionData eloActionData, ELODeleteData eloDeleteData) {
        this.loginData = loginData;
        this.eloSolutionArchiveData = eloSolutionArchiveData;
        this.eloActionData = eloActionData;
        this.eloDeleteData = eloDeleteData;
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
    public ELODeleteData getEloDeleteData() {
        return eloDeleteData;
    }
    @Override
    public String toString() {
        return "DataConfig{" +
                "loginData=" + loginData +
                ", eloSolutionArchiveData=" + eloSolutionArchiveData +
                ", eloActionData=" + eloActionData +
                ", eloDeleteData=" + eloDeleteData +
                '}';
    }
}

