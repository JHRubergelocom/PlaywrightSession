package session;

public class ELOCheckValueControl {
    private final String selector;
    private final String value;
    private final ELOControlType type;
    private final ELOCheckValueOperator operator;

    public ELOCheckValueControl(String selector, String value, ELOControlType type, ELOCheckValueOperator operator) {
        this.selector = selector;
        this.value = value;
        this.type = type;
        this.operator = operator;
    }

    public ELOCheckValueControl() {
        this.selector = "";
        this.value = "";
        this.type = ELOControlType.NONE;
        this.operator = ELOCheckValueOperator.NONE;
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

    public ELOCheckValueOperator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "ELOCheckValueControl{" +
                "selector='" + selector + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                ", operator=" + operator +
                '}';
    }
}
