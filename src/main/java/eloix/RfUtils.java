package eloix;

import byps.RemoteException;
import de.elo.ix.client.Any;
import de.elo.ix.client.AnyC;
import de.elo.ix.client.IXConnection;

public class RfUtils {
    public static String executeRF(IXConnection ixConn, String funcName, String jsonParam) throws Exception {
        Any anyParam = new Any();
        anyParam.setType(AnyC.TYPE_STRING);
        anyParam.setStringValue(jsonParam);

        try {
            Any anyResult = ixConn.ix().executeRegisteredFunction(funcName, anyParam);
            return anyResult.getStringValue();
        } catch (RemoteException ex) {
            // Object not found
            System.err.println("executeRF RemoteException message: " + ex.getMessage());
            throw new Exception("RF-Funktion " + funcName + " mit jsonParam " + jsonParam + " konnte nicht ausgeführt werden " + ex.getMessage());
        }
    }
}
