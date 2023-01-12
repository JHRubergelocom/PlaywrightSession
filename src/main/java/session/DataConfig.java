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
}

