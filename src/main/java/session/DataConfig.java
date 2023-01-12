package session;

public class DataConfig {
    private final LoginData loginData;
    private final ELOSolutionArchiveData eloSolutionArchiveData;
    private final ELOActionDefData eloActionDefData;
    private final ELOActionFormulaData eloActionFormularData;
    private final ELOInputData eloInputData;
    public DataConfig(LoginData loginData, ELOSolutionArchiveData eloSolutionArchiveData, ELOActionDefData eloActionDefData, ELOActionFormulaData eloActionFormularData, ELOInputData eloInputData) {
        this.loginData = loginData;
        this.eloSolutionArchiveData = eloSolutionArchiveData;
        this.eloActionDefData = eloActionDefData;
        this.eloActionFormularData = eloActionFormularData;
        this.eloInputData = eloInputData;
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
    public ELOInputData getEloInputData() {
        return eloInputData;
    }
}

