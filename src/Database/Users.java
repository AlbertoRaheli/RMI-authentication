package Database;

import java.util.ArrayList;

public class Users {


    private String Username;
    private String Password;
    private String Role;
    private String[] commands;

    public Users (String username, String password, String role, String[] commands)
    {
        this.Password = password;
        this.Username = username;
        this.Role = role;
        this.commands = commands;

    }

    public String getUsername()
    {
        return this.Username;
    }

    public String getPassword()
    {
        return this.Password;
    }

    public String[] getCommands() { return this.commands; }

    public String getRole() {return this.Role;}


}


