package eloix;

import byps.RemoteException;
import de.elo.ix.client.Any;
import de.elo.ix.client.AnyC;
import de.elo.ix.client.IXConnection;
import session.ELORf;

public class RfUtils {
    public static String executeRF(IXConnection ixConn, ELORf eloRf) throws Exception {
        Any anyParam = new Any();
        anyParam.setType(AnyC.TYPE_STRING);
        anyParam.setStringValue(eloRf.getJsonParam());

        try {
            System.out.println("*".repeat(30) + "ExecuteRF" + "*".repeat(30));
            System.out.println("eloRf.getFuncName(): " + eloRf.getFuncName());
            System.out.println("eloRf.getJsonParam(): " + eloRf.getJsonParam());
            Any anyResult = ixConn.ix().executeRegisteredFunction(eloRf.getFuncName(), anyParam);
            System.out.println("anyResult.getStringValue(): " + anyResult.getStringValue());
            System.out.println("*".repeat(30) + "ExecuteRF" + "*".repeat(30));
            return anyResult.getStringValue();
        } catch (RemoteException ex) {
            // Object not found
            System.err.println("executeRF RemoteException message: " + ex.getMessage());
            throw new Exception("eloRf " + eloRf + " konnte nicht ausgeführt werden " + ex.getMessage());
        }
    }
}
