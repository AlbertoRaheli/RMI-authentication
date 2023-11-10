package PrintingServiceInterface;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface LogInInterface extends Remote {
    boolean LogIn(String name, String password) throws IOException, NoSuchAlgorithmException;
}
