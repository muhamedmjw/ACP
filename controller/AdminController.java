package controller;
import java.io.*;
import java.util.*;
import model.*;
import data.Database;

public class AdminController {
    private Database database = new Database();


    public void viewAllClients(PrintWriter writer) {

            List<String> allClientProfiles = database.printAllClientProfiles();
                for (String info : allClientProfiles) {
                    writer.println(info);
                }
        writer.println("END_OF_PROFILES");
        }

    public void deleteClient(BufferedReader reader, PrintWriter writer){
        try {
            // Read the Post ID from the client
            int userId = Integer.parseInt(reader.readLine());

            // Call the database method to delete the post
            boolean success = database.deleteClient(userId);

            // Send a response back to the client
            if (success) {
                writer.println("Client deleted successfully.");
            } else {
                writer.println("Failed to delete Client. Please check the Client ID and try again.");
            }
        } catch (IOException | NumberFormatException e) {
            writer.println("Error: " + e.getMessage());
        }
        writer.flush();  // Ensure the response is sent immediately
    }

}
