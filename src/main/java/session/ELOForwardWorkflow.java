package session;

import java.util.List;

public class ELOForwardWorkflow {
    private final List<String> toNodesName;

    public ELOForwardWorkflow(List<String> toNodesName) {
        this.toNodesName = toNodesName;
    }

    public List<String> getToNodesName() {
        return toNodesName;
    }

    @Override
    public String toString() {
        return "ELOForwardWorkflow{" +
                "toNodesName=" + toNodesName +
                '}';
    }
}
