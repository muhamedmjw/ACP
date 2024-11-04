package controller;
import data.Database;
import java.io.*;
import java.sql.*;
import java.util.*;

import static data.Database.connect;

public class PostController {
    private Database database;

    public PostController() {
        database = new Database();
    }

    public void createPost(BufferedReader reader, PrintWriter writer) throws IOException {
        String title = reader.readLine();
        String listingType = reader.readLine();
        String type = reader.readLine();
        double area = Double.parseDouble(reader.readLine());
        String contactInfo = reader.readLine();
        String country = reader.readLine();
        String city = reader.readLine();
        String address = reader.readLine();
        String description = reader.readLine();
        double price = Double.parseDouble(reader.readLine());
        int bedrooms = Integer.parseInt(reader.readLine());
        int bathrooms = Integer.parseInt(reader.readLine());

        database.savePost(title, listingType, type, area, contactInfo, country, city, address, description, price, bedrooms, bathrooms);

    }

    public void viewPosts(PrintWriter writer) {
        List<String> posts = database.printPosts(); // Get the list of posts
        for (String post : posts) {
            writer.println(post); // Send each post to the client
        }
        writer.println("END_OF_POSTS"); // Indicate the end of the posts
    }

    public void deletePost(BufferedReader reader, PrintWriter writer) {
        try {
            // Read the Post ID from the client
            int postId = Integer.parseInt(reader.readLine());

            // Call the database method to delete the post
            boolean success = database.deletePost(postId);

            // Send a response back to the client
            if (success) {
                writer.println("Post deleted successfully.");
            } else {
                writer.println("Failed to delete post. Please check the Post ID and try again.");
            }
        } catch (IOException | NumberFormatException e) {
            writer.println("Error: " + e.getMessage());
        }
        writer.flush();  // Ensure the response is sent immediately
    }

    public boolean updatePost(String postId, String fieldChoice, String newValue) {
        return database.updatePost(postId, fieldChoice, newValue);
    }

    public boolean postExists(String postId) {
        String query = "SELECT COUNT(*) FROM Post WHERE PostId = ?";
        try (Connection conn = connect();  // Ensure you have a method to establish a database connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(postId));  // Set the Post ID

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Return true if count is greater than 0 (post exists)
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking post existence: " + e.getMessage());
        }
        return false;  // Return false if there was an error or post doesn't exist
    }

}
