package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import model.*;
import data.Database;

public class UserController {
    private HashMap<Integer, User> users;
    private final String userDataFile = "data\\UserData.txt";
    private Database database = new Database();


    public UserController() {
        users = new HashMap<>();
        loadUsers();
    }


    public void registerUser(String name, String username, String email, String password, String phoneNo, String address, PrintWriter writer) {
        int userId = users.size() + 1;
        database.registerUser(name, username, email, password, phoneNo, address);
        User newUser = new User(userId, 1, name, address, phoneNo, username, email, password);
        users.put(userId, newUser);
        saveUsers();

        writer.println("Registered '" + newUser.getName() + "' successfully. Please login.");
    }


    public String handleLogin(String username, String password, PrintWriter writer) {
        String role = database.getUserByUsernameAndPassword(username, password);
        return role;
    }


    private void loadUsers(){
        File file = new File(userDataFile);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(userDataFile))){
            String line;
            while ((line=reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {

                    int userId = Integer.parseInt(data[0]);
                    int roleId = Integer.parseInt(data[1]);
                    String name = data[2];
                    String address = data[3];
                    String phoneNumber = data[4];
                    String username = data[5];
                    String email = data[6];
                    String password = data[7];
                    
                    User user = new User(userId, roleId, name, address, phoneNumber, username, email, password);
                    users.put(userId, user); // Add the user to the list
                }
            }

        } catch (IOException e) {
            System.err.println("Error loading user data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing user data: " + e.getMessage());
        }
    }


    private void saveUsers(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDataFile))){
            for(User user : users.values()){
                writer.write(user.getUserId() + "," + user.getRoleId() + "," + user.getName() + ","+ user.getAddress() + "," + user.getPhoneNumber() + ","+ user.getUsername() + "," + user.getEmail() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }
}