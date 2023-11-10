package Database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Printer {


    private String PrinterName;
    private String status;
    Queue<Job> q = new LinkedList<>();

    public Printer (String printername) {
        Queue<Job> q = new LinkedList<>();
        this.PrinterName = printername;
    }
    public String getPrinterName()
    {
        return this.PrinterName;
    }

    public List<Job> getJobsInQueue()
    {
        printJob();
        List<Job> list = new ArrayList<>(q);
        return list;
    }


    public void PutJobInPrinter(Job job)
    {
        printJob();
        q.add(job);
    }

    public void removeJobInPrinter(Job job)
    {
        printJob();
        q.remove(job);
    }

    public void moveToTop(int job)
    {
        List<Job> jobs = getJobsInQueue();
        for (Job j : jobs)
        {
            if (j.getID() == job)
            {
                removeJobInPrinter(j);
                PutJobInPrinter(j);
            }
        }
    }

    public void clearQueue()
    {
        q.clear();
    }

    private void printJob()
    {
        for (Job j : q)
        {
            if (j.getTimeToLive() < System.currentTimeMillis() )
            {
                System.out.println("File: " + j.getFileName() + "with id: "  + j.getID() + " is printed");
                q.remove(j);
            }
        }
    }
}
