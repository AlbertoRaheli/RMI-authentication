package Database;

import java.util.Random;

public class Job {


        private String Username;
        private String FileName;
        private int ID;
        private long timeToLive;

    public Job (String filename, String username,int ID)
        {
            this.FileName = filename;
            this.Username = username;
            this.ID = ID;
            this.timeToLive = System.currentTimeMillis() + (long)(Math.random()*(30000-5000+1)+5000);
        }

        public String getUsername()
        {
            return this.Username;
        }

        public String getFileName()
        {
            return this.FileName;
        }

        public int getID()
        {
            return this.ID;
        }
        public long getTimeToLive()
        {
            return this.timeToLive;
        }


}
