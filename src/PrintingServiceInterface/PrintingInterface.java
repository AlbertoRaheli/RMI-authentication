package PrintingServiceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintingInterface extends Remote {
    String print(String filename, String printer, String username) throws RemoteException;   // prints file filename on the specified printer
    String queue(String printer, String username) throws RemoteException;   // lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
    String topQueue(String printer, int job, String username) throws RemoteException;   // moves job to the top of the queue
    String start(String username) throws RemoteException;   // starts the print server
    String stop(String username) throws RemoteException;   // stops the print server
    String restart(String username) throws RemoteException, InterruptedException;   // stops the print server, clears the print queue and starts the print server again
    String status(String printer,String username) throws RemoteException;  // prints status of printer on the user's display
    String readConfig(String parameter,String username) throws RemoteException;   // prints the value of the parameter on the user's display
    String setConfig(String parameter, String value,String username) throws RemoteException;   // sets the parameter to value
}
