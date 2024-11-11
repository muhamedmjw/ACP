package controller;
import java.io.*;
import java.util.*;
import model.*;
import data.Database;

public class UserController {
    private Database database = new Database();

    public void registerUser(String name, String username, String email, String password, String phoneNo, String address, PrintWriter writer) {
        database.saveUser(name, username, email, password, phoneNo, address);
    }

    public String loginUser(String username, String password, PrintWriter writer) {
        String[] role = database.loadUser(username, password);
        return role[0];
    }

    public void viewProfile(PrintWriter writer) {
        String userId = UserSession.getUserId();
        List<String> profileInfo = database.printProfileInformation(userId); // Retrieve profile information

        for (String info : profileInfo) {
            writer.println(info); // Send each piece of profile information to the client
        }
        writer.println("END_OF_PROFILE"); // Indicate the end of profile information
    }

    public void upgradeRole(PrintWriter writer){
        database.changeClientRole(UserSession.getUserId());
    }
}