package eloix;

import byps.RemoteException;
import de.elo.ix.client.*;

import java.util.ArrayList;
import java.util.List;

public class WfUtils {
    private static List<WFDiagram> findWorkflows(IXConnection ixConn, FindWorkflowInfo findWorkflowInfo) {
        int max = 100;
        int idx = 0;
        List<WFDiagram> workflows = new ArrayList<>();
        FindResult findResult = null;
        try {
            findResult = ixConn.ix().findFirstWorkflows(findWorkflowInfo, max, WFDiagramC.mbAll);
            while (true) {
                for (int i = 0; i < findResult.getWorkflows().length; i++) {
                    WFDiagram wf = findResult.getWorkflows()[i];
                    workflows.add(wf);
                }
                if (!findResult.isMoreResults()) {
                    break;
                }
                idx += findResult.getWorkflows().length;
                findResult = ixConn.ix().findNextWorkflows(findResult.getSearchId(), idx, max);
            }
        } catch (RemoteException ex) {
            System.err.println("error looking up workflows " + ex.getMessage());
        } finally {
            if (findResult != null) {
                try {
                    ixConn.ix().findClose(findResult.getSearchId());
                } catch (RemoteException ex) {
                    System.err.println("error findclose " + ex.getMessage());
                }
            }
        }
        return workflows;
    }
    private static List<WFDiagram> getActiveWorkflows (IXConnection ixConn) {
        FindWorkflowInfo findInfo = new FindWorkflowInfo();
        findInfo.setType(WFTypeC.ACTIVE);
        return findWorkflows(ixConn, findInfo);
    }
    private static WFDiagram getActiveWorkflow(IXConnection ixConn) throws Exception {
        System.out.println("*".repeat(30) + "Active Workflows" + "*".repeat(30));
        List<WFDiagram> activeWorkflows = WfUtils.getActiveWorkflows(ixConn);
        for (WFDiagram wf: activeWorkflows) {
            System.out.println("wf.getId(): " + wf.getId());
            System.out.println("wf.getName(): " + wf.getName());
            System.out.println("wf.getNameTranslationKey(): " + wf.getNameTranslationKey());
            System.out.println("wf.getTemplateName(): " + wf.getTemplateName());
            System.out.println("-".repeat(60));
        }
        System.out.println("*".repeat(30) + "Active Workflows" + "*".repeat(30));
        try {
            return activeWorkflows.get(0);
        } catch (Exception ex) {
            System.err.println("getActiveWorkflow message: " +  ex.getMessage());
            throw new Exception("Kein aktiver Workflow vorhanden");
        }
    }
    public static List<WFDiagram> getFinishedWorkflows (IXConnection ixConn) {
        FindWorkflowInfo findInfo = new FindWorkflowInfo();
        findInfo.setType(WFTypeC.FINISHED);
        return findWorkflows(ixConn, findInfo);
    }
    public static void removeFinishedWorkflows(IXConnection ixConn, List<WFDiagram> workflows) {
        for (WFDiagram wf: workflows) {
            try {
                ixConn.ix().deleteWorkFlow(wf.getId() + "", WFTypeC.FINISHED, LockC.NO);
            } catch (RemoteException ex) {
                System.err.println("RemoteException message: " + ex.getMessage());
            }
        }

    }
    public static List<WFNode> getActiveUserNodes (WFDiagram workflow) {
        List<WFNode> nodes  = new ArrayList<>();
        WFNode node;
        for (int i = 0; i < workflow.getNodes().length; i++) {
            node = workflow.getNodes()[i];
            if (!node.getEnterDateIso().equals("") && node.getExitDateIso().equals("") && !node.getUserName().equals("")) {
                nodes.add(node);
            }
        }
        return nodes;
    }
    private static WFNode getNode(WFDiagram workflow, int id) {
        for (int i = 0; i < workflow.getNodes().length; i++) {
            WFNode node = workflow.getNodes()[i];
            if ((node.getId() == id) && (node.getType() != WFNodeC.TYPE_NOTHING)) {
                return node;
            }
        }
        return null;
    }
    private static WFNode getActiveUserNode(WFDiagram workflow) throws Exception {
        System.out.println("*".repeat(30) + "Active UserNodes" + "*".repeat(30));
        List<WFNode> activeUserNodes = WfUtils.getActiveUserNodes(workflow);
        for (WFNode userNode: activeUserNodes) {
            System.out.println("userNode.getId(): " + userNode.getId());
            System.out.println("userNode.getName(): " + userNode.getName());
            System.out.println("userNode.getNameTranslationKey(): " + userNode.getNameTranslationKey());
            System.out.println("-".repeat(60));
        }
        System.out.println("*".repeat(30) + "Active UserNodes" + "*".repeat(30));
        try {
            return activeUserNodes.get(0);
        } catch (Exception ex){
            System.err.println("getActiveUserNode message: " +  ex.getMessage());
            throw new Exception("Kein aktiver Benutzerknoten vorhanden");
        }
    }
    private static List<WFNode> getSuccessorNodes (WFDiagram workflow, int nodeFromId, String nodeToName) {
        List<WFNode> succNodes  = new ArrayList<>();
        WFNodeAssoc[] assocs = workflow.getMatrix().getAssocs();
        for (WFNodeAssoc assoc: assocs) {
            if (assoc.getNodeFrom() == nodeFromId) {
                WFNode nodeTo = getNode(workflow, assoc.getNodeTo());
                if (nodeToName.equals(nodeTo.getName()) || nodeToName.equals(nodeTo.getNameTranslationKey())) {
                    succNodes.add(nodeTo);
                }
            }
        }
        return succNodes;
    }
    private static  void forwardWorkflow2 (IXConnection ixConn, int flowId, int currentNodeId, int[] destinationNodeIds) {
        try {
            ixConn.ix().takeWorkFlowNode(flowId, currentNodeId, "", WFTakeNodeC.RESET_IN_USE_DATE, LockC.FORCE);
            ixConn.ix().beginEditWorkFlowNode(flowId, currentNodeId, LockC.YES);
            ixConn.ix().endEditWorkFlowNode(flowId, currentNodeId, false, false, null, null, destinationNodeIds);
        } catch (RemoteException ex) {
            System.err.println("RemoteException message: " + ex.getMessage());
        }

        System.err.println("Workflow forwarded (flowId=" + flowId + ", currentNodeId=" + currentNodeId + ", destinationNodeIds=" + destinationNodeIds+ ")");
    }
    private static void forwardWorkflow1 (IXConnection ixConn, WFDiagram workflow, WFNode currentNode, List<WFNode> destinationNodes) {
        List<Integer> ids = new ArrayList<>();
        for (WFNode node: destinationNodes){
            ids.add(node.getId());
        }
        int[] destinationNodeIds = ids.stream().mapToInt(Integer::intValue).toArray();
        forwardWorkflow2(ixConn, workflow.getId(), currentNode.getId(), destinationNodeIds);
    }
    public static void forwardWorkflow(IXConnection ixConn, String toNodeName) throws Exception {
        // Get Active Workflow
        WFDiagram activeWorkflow = getActiveWorkflow(ixConn);

        // Get Active User Node
        WFNode activeUserNode = getActiveUserNode(activeWorkflow);

        // Get Successor Nodes
        List<WFNode> successorNodes = getSuccessorNodes(activeWorkflow, activeUserNode.getId(), toNodeName);

        // Forward Workflow
        forwardWorkflow1(ixConn, activeWorkflow, activeUserNode, successorNodes);
    }
}
