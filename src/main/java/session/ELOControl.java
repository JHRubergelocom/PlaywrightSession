package session;

public class ELOControl {
    private final String selector;
    private final String value;
    private final ELOControlType type;
    private final boolean checkValue;
    public ELOControl(String selector, String value, ELOControlType type, boolean checkValue) {
        this.selector = selector;
        this.value = value;
        this.type = type;
        this.checkValue = checkValue;
    }
    public ELOControl() {
        this.selector = "";
        this.value = "";
        this.type = ELOControlType.NONE;
        this.checkValue = false;
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
    public boolean isCheckValue() {
        return checkValue;
    }

    @Override
    public String toString() {
        return "ELOControl{" +
                "selector='" + selector + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                ", checkValue=" + checkValue +
                '}';
    }
}