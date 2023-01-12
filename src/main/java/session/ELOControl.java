package session;

public class ELOControl {
    private final String selector;
    private final String value;
    public ELOControl(String selector, String value) {
        this.selector = selector;
        this.value = value;
    }
    public String getSelector() {
        return selector;
    }
    public String getValue() {
        return value;
    }
}