package eloix;

import de.elo.ix.client.IXConnFactory;
import de.elo.ix.client.IXConnection;
import session.LoginData;

import java.rmi.RemoteException;

public class ELOIxConnection {

    private static String getIxUrl(LoginData loginData) {
        return  "http://" + loginData.getStack() + "/ix-Solutions/ix";
    }
    private static String getUser(LoginData loginData) {
        return loginData.getTextUserName().getValue();
    }
    public static IXConnection getIxConnection(LoginData loginData, boolean asAdmin) throws Exception{
        IXConnection ixConn;
        IXConnFactory connFact;
        try {
            connFact = new IXConnFactory(getIxUrl(loginData), "IXConnection", "1.0");
        } catch (Exception ex) {
            System.err.println("ELO ELOIxConnection Falsche Verbindungsdaten zu ELO \n: " + ex.getMessage());
            System.err.println("IllegalStateException message: " +  ex.getMessage());
            throw new Exception("ELOIxConnection");

        }
        try {
            if (asAdmin) {
                ixConn = connFact.create("Administrator", "elo", null, null);
            } else {
                ixConn = connFact.create(loginData.getTextUserName().getValue(), loginData.getTextPassword().getValue(), null, null);
            }
        } catch (RemoteException ex) {
            System.err.println("ELO ELOIxConnection Indexserver-Verbindung ungültig \n User: " + getUser(loginData) + "\n IxUrl: " + getIxUrl(loginData));
            System.err.println("RemoteException message: " + ex.getMessage());
            throw new Exception("ELOIxConnection");
        }
        return ixConn;
    }
}
