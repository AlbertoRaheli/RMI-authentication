package Server;

import PrintingServiceInterface.LogIn;
import PrintingServiceInterface.PrintingServiceACL;
import PrintingServiceInterface.PrintingServiceRBAC;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SSLServer extends PrintingServiceACL {

    private static int Port = 5099;
    private static int SSlSocketPort = 5051;

    public SSLServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {
        System.out.println("Server Starting... beep boop");
        Registry registry = LocateRegistry.createRegistry(Port);
        try {
            LogIn login = new LogIn();
            registry.rebind("logIn",login);
            System.err.println("Server ready");
            PrintingServiceRBAC printServiceRBAC = new PrintingServiceRBAC();
            registry.rebind("RBAC",printServiceRBAC);
            PrintingServiceACL printServiceACL = new PrintingServiceACL();
            registry.rebind("ACL",printServiceACL);
        } catch (Exception e){
            System.out.println("Server e" + e.toString());
            e.printStackTrace();
        }
    }
}

