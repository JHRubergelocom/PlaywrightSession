package session;

public class ELOControl {
    private final String selector;
    private final String value;
    private final ELOControlType type;
    public ELOControl(String selector, String value, ELOControlType type) {
        this.selector = selector;
        this.value = value;
        this.type = type;
    }

    public ELOControl() {
        this.selector = "";
        this.value = "";
        this.type = ELOControlType.NONE;
    }

    public String getSelector() {
        return selector;
    }
    public String getValue() {
        return value;
    }

    public ELOControlType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ELOControl{" +
                "selector='" + selector + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}