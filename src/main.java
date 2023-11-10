import Client.SSLClient;
import Server.SSLServer;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class main
{
    public static void main(String[] args) throws InterruptedException, RemoteException, MalformedURLException, UnknownHostException, NotBoundException {
        SSLServer.main(args);
        Thread.sleep(2000);
        SSLClient.main(args);

//
//        RunProcess.exec("java Server.SSLServer");
//        Thread.Sleep(2000);
//        TimeUnit.SECONDS.sleep(4);
//        runProcess.exec("java Client.SSLClient");
   }
}

