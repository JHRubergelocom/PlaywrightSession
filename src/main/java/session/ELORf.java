package session;

public class ELORf {
    private final String funcName;
    private final String jsonParam;

    public ELORf(String funcName, String jsonParam) {
        this.funcName = funcName;
        this.jsonParam = jsonParam;
    }

    public String getFuncName() {
        return funcName;
    }

    public String getJsonParam() {
        return jsonParam;
    }

    @Override
    public String toString() {
        return "ELORf{" +
                "funcName='" + funcName + '\'' +
                ", jsonParam='" + jsonParam + '\'' +
                '}';
    }
}
