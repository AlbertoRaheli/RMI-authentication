package Database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {

    private final ArrayList<String> userCommnads = new ArrayList<>();
    private String passwordFile = "";
    private String saltFile = "";
    private String RoleFromRBfile = "";

    Printer printer1 = new Printer("p1");
    Printer printer2 = new Printer("p2");
    Printer printer3 = new Printer("p3");

    public ArrayList<Printer> getPrinterList()
    {
        ArrayList<Printer> printers = new ArrayList<>();
        printers.add(printer1);
        printers.add(printer2);
        printers.add(printer3);
        return  printers;
    }

    public String getPassword(String username)
    {
        passwordFile = "";
        return getPasswordFromFile(username);
    }

    public String getSalt(String username)
    {
        saltFile = "";
        return getSaltFromFile(username);
    }

    public boolean checkCommands(String username, String command)
    {
        commandsFromFile(username);
        for (String s : userCommnads) {
            if (s.matches(command)) {
                userCommnads.clear();
                return true;
            }
        }
        userCommnads.clear();
        System.out.println("User: " + username + " tried using unathorised command: " + command);
        return false;
    }

    public boolean checkCommandsRB(String username,String command)
    {
        commandsFromRBFile(roleFromRBFile(username));
        for (String s : userCommnads) {
            if (s.matches(command)) {
                userCommnads.clear();
                return true;
            }
        }
        userCommnads.clear();
        System.out.println("User: " + username + " tried using unathorised command: " + command);
        return false;
    }

//    #################################################################
//                  Get password from Access Control List

    private String getPasswordFromFile(String inputUsername) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/Database/UserList2.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray userList = (JSONArray) obj;

            userList.forEach(
                    usr -> passwordParser((JSONObject) usr, inputUsername));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return passwordFile;
    }

    private void  passwordParser(JSONObject user, String inputUsername) {
        JSONObject userObject = (JSONObject) user.get("user");
        String username = (String) userObject.get("username");
        if (username.equals(inputUsername)) {
            passwordFile = (String) userObject.get("hash");
        }
    }

// #################################################################
//                  Get salt from Access Control List

    private String getSaltFromFile(String inputUsername) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/Database/UserList2.json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray userList = (JSONArray) obj;

            userList.forEach(
                    usr -> saltParser((JSONObject) usr, inputUsername));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return saltFile;
    }

    private void  saltParser(JSONObject user, String inputUsername) {
        JSONObject userObject = (JSONObject) user.get("user");
        String username = (String) userObject.get("username");
        if (username.equals(inputUsername)) {
            saltFile = (String) userObject.get("salt");
        }
    }

//    #################################################################
//              Get Commands from Access Control List

    private void commandsFromFile(String inputUsername) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/Database/UserList2.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray userList = (JSONArray) obj;

            userList.forEach(
                    usr -> commandsParser((JSONObject) usr, inputUsername));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void  commandsParser(JSONObject user, String inputUsername) {
        JSONObject userObject = (JSONObject) user.get("user");
        String username = (String) userObject.get("username");

        if (username.equals(inputUsername)) {
            JSONArray commands = (JSONArray) userObject.get("commands");
            Iterator<String> iterator = commands.iterator();
            while (iterator.hasNext()) {
                userCommnads.add(iterator.next());
            }
        }
    }


//    #################################################################
//              Get Commands from Role Base Access Control files

    private String roleFromRBFile(String inputUsername) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/Database/UserRoles2.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray userList = (JSONArray) obj;
            userList.forEach(
                    usr -> roleParserRB((JSONObject) usr, inputUsername));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return RoleFromRBfile;
    }

    private void  roleParserRB(JSONObject user, String inputUsername) {
        JSONObject userObject = (JSONObject) user.get("user");
        String username = (String) userObject.get("username");
        if (username.equals(inputUsername)) {
            RoleFromRBfile = (String) userObject.get("role");
        }
    }



    private void commandsFromRBFile(String role) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/Database/PermissionRoles.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray roleList = (JSONArray) obj;

            roleList.forEach(
                    rl -> commandsParserRB((JSONObject) rl, role));


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void  commandsParserRB(JSONObject rl, String role) {
        JSONObject roleObject = (JSONObject) rl.get("role");
        String roleName = (String) roleObject.get("roleName");

        if (roleName.equals(role)) {
            JSONArray commands = (JSONArray) roleObject.get("commands");
            for (String command : (Iterable<String>) commands) {
                userCommnads.add(command);
            }
        }
    }



}


