package PrintingServiceInterface;

import Database.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;



public class PrintingServiceACL extends UnicastRemoteObject implements PrintingInterface {

    private final List<Printer> printers = new ArrayList<>();
    private final Database dbase = new Database();
    int jobCounter = 1;
    boolean isOnline = true;

    public PrintingServiceACL() throws RemoteException {
        super();
        System.out.println("Printing Server started");
        initPrinter();
    }

    @Override
    public String print(String filename, String printer,String username) throws RemoteException {
        if(dbase.checkCommands(username, "print") && isOnline)
        {
            Job job = new Job(filename, username, jobCounter);
            for (Printer p : printers) {
                if (printer.matches(p.getPrinterName())) {
                    p.PutJobInPrinter(job);
                    jobCounter++;
                }
            }
            System.out.println("File: " + filename + " , added for printing at printer " + printer + " by user " + username);
            return "File " + filename + " started printing at printer " + printer;
        }
        else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    @Override
    public String queue(String printer,String username)  throws RemoteException {
        if(dbase.checkCommands(username, "queue") && isOnline) {
            List<Job> jobs = new ArrayList<>();
            for (Printer p : printers) {
                if (printer.matches(p.getPrinterName())) {
                    jobs = p.getJobsInQueue();
                }
            }
            if (jobs.isEmpty())
            {
                return "Queue for printer: " + printer + " is empty!";
            }
            System.out.println("The queue list of " + printer + ": ");
            String queueString = "";
            for (int i = 0; i < jobs.size(); i++) {
                queueString += "\n" + "Job #" + i + " - Filename: " + jobs.get(i).getFileName() + " - ID: " + jobs.get(i).getID();
            };
            System.out.println(queueString);
            return queueString;
        }  else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    @Override
    public String topQueue(String printer, int job,String username)  throws RemoteException {
        if(dbase.checkCommands(username, "topQueue") && isOnline) {
            for (Printer p : printers) {
                if (printer.matches(p.getPrinterName())) {
                    p.moveToTop(job);
                }
            }
            return "Job " + job + "is moved to the top of the list for printer" + printer;
        }  else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }

    }

    @Override
    public String start(String username) throws RemoteException{
        if(!dbase.checkCommands(username, "start") && isOnline) {
            return "Permission Denied";
        }  else if (!isOnline){
            isOnline = true;
            System.err.println("Starting Print Server");
            return "Starting Printing server";
        } else{
            return " Printing server is already Online";
        }
    }

    @Override
    public String stop(String username) throws RemoteException {
        if(dbase.checkCommands(username, "stop") && isOnline) {
            System.err.println("Stopping Print Server");
            isOnline = false;
            return  "Stopping the Print server";
        }  else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    @Override
    public String restart(String username) throws RemoteException, InterruptedException {
        if(dbase.checkCommands(username, "restart") && isOnline) {
            for (Printer p : printers) {
                p.clearQueue();
            }
            System.err.println("Restarting..");
            Thread.sleep(4000);
            System.err.println("Print Service restarted");
            return "Print Service restarted";
        }else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    @Override
    public String status(String printer, String username) throws RemoteException {
        if(dbase.checkCommands(username, "status") && isOnline) {
            return  "Status of printer " + printer;
        }else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    @Override
    public String readConfig(String parameter, String username) throws RemoteException {
        if(dbase.checkCommands(username, "readConfig") && isOnline) {
            return  "Reading config of " +"\0"+ parameter;
        }else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    @Override
    public String setConfig(String parameter, String value, String username)   throws RemoteException{
        if(dbase.checkCommands(username, "setConfig") && isOnline) {
            return  "the following parameter " + parameter +" is set to " + value;
        }else if (!isOnline){
            return " Printing server offline";
        } else{
            return "Permission Denied";
        }
    }

    private void initPrinter() {
        printers.addAll(dbase.getPrinterList());
    }
}
