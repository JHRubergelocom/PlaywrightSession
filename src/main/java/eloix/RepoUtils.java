package eloix;

import byps.RemoteException;
import de.elo.ix.client.*;

public class RepoUtils {
    private static String normalizePath(String repoPath, boolean withPrefix) {
        if (repoPath.indexOf("ARCPATH") == 0) {
            if (withPrefix) {
                return repoPath;
            } else {
                return repoPath.replace("/^ARCPATH[^:]*:/", "");
            }
        } else {
            if (withPrefix) {
                return "ARCPATH:" + repoPath;
            } else {
                return repoPath;
            }
        }
    }
    private static String getObjId(IXConnection ixConn, String arcPath) {
        arcPath = normalizePath(arcPath, true);
        try {
            Sord sord = ixConn.ix().checkoutSord(arcPath + "", SordC.mbOnlyId, LockC.NO);
            String objId = sord.getId() + "";
            System.out.println("getObjId objId = " + objId);
            return objId;
        } catch (RemoteException ex) {
            // Object not found
            System.err.println("RemoteException message: " + ex.getMessage());
        }
        return null;
    }
    public static void DeleteSord (IXConnection ixConn, String arcPath) {
        String id = getObjId(ixConn, arcPath);
        if (id == null) {
            return;
        }
        try {
            ixConn.ix().deleteSord(null, id, LockC.NO, null);
        } catch (RemoteException ex) {
            // Object not found
            System.err.println("RemoteException message: " + ex.getMessage());
        }
    }
}
