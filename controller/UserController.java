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
        String role = database.loadUser(username, password);
        return role;
    }

}