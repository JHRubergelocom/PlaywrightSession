package session;

public class DataConfig {
    private final LoginData loginData;
    private final ELOSolutionArchiveData eloSolutionArchiveData;
    private final ELOActionDefData eloActionDefData;
    private final ELOActionFormulaData eloActionFormularData;
    private final ELOActionData eloActionData;
    public DataConfig(LoginData loginData, ELOSolutionArchiveData eloSolutionArchiveData, ELOActionDefData eloActionDefData, ELOActionFormulaData eloActionFormularData, ELOActionData eloActionData) {
        this.loginData = loginData;
        this.eloSolutionArchiveData = eloSolutionArchiveData;
        this.eloActionDefData = eloActionDefData;
        this.eloActionFormularData = eloActionFormularData;
        this.eloActionData = eloActionData;
    }
    public LoginData getLoginData() {
        return loginData;
    }
    public ELOSolutionArchiveData getEloSolutionArchiveData() {
        return eloSolutionArchiveData;
    }
    public ELOActionDefData getEloActionDefData() {
        return eloActionDefData;
    }
    public ELOActionFormulaData getEloActionFormularData() {
        return eloActionFormularData;
    }
    public ELOActionData getEloActionData() {
        return eloActionData;
    }
}

