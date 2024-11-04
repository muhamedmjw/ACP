package data;
import controller.PostController;
import controller.UserSession;
import model.Post;
import model.User;

import java.sql.*;
import java.util.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/realestatedb";
    private static final String USER = "root";
    private static final String PASSWORD = "mmmvvv111";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveUser(String name, String username, String email, String password, String phoneNo, String address) {
        String query = "INSERT INTO user (roleid, name, address, phoneNumber, username, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

              pstmt.setInt(1, 1);
              pstmt.setString(2, name);
              pstmt.setString(3, address);
              pstmt.setString(4, phoneNo);
              pstmt.setString(5, username);
              pstmt.setString(6, email);
              pstmt.setString(7, password);
              pstmt.executeUpdate();

        }catch (SQLException e) {
          System.err.println("Registration failed: " + e.getMessage());
        }
    }

    public String[] loadUser(String username, String password) {
        String[] result = new String[2]; // Index 0 for role, 1 for userId
        String query = "SELECT roleId, UserId FROM user WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result[0] = (rs.getInt("roleId") == 1) ? "CLIENT" : "ADMIN"; // Role
                result[1] = rs.getString("UserId"); // UserId
                UserSession.setUserId(result[1]);
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return result; // Returns the role and userId
    }

    public List<String> printPosts() {
        List<String> posts = new ArrayList<>();
        String query = "SELECT * FROM Post"; // Adjust the fields as necessary

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Collect the necessary details
                String postInfo = rs.getInt("PostId") + ". " +
                        rs.getString("Title") + " | " +
                        rs.getString("Type") + " | " +
                        rs.getString("ListingType") + " | " +
                        rs.getString("Address") + " | " +
                        rs.getString("City") + " | " +
                        rs.getString("Country") + " | $" +
                        rs.getDouble("Price") + " | " +
                        rs.getString("Status");
                posts.add(postInfo); // Add to the list
            }
        } catch (SQLException e) {
            System.err.println("Error loading posts: " + e.getMessage());
        }
        return posts; // Return the list of posts
    }

    public boolean deletePost(int postId) {
        String query = "DELETE FROM Post WHERE PostId = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, postId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if a row was successfully deleted
        } catch (SQLException e) {
            System.err.println("Error deleting post: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePost(String postId, String fieldChoice, String newValue) {
        if (fieldChoice.equals("0")) {
            System.out.println("Update process cancelled.");
            return false; // Indicate that no update occurred
        }

        String query = "";

        switch (fieldChoice) {
            case "1": // Title
                query = "UPDATE Post SET Title = ? WHERE PostId = ?";
                break;
            case "2": // Type
                query = "UPDATE Post SET Type = ? WHERE PostId = ?";
                break;
            case "3": // Listing Type
                query = "UPDATE Post SET ListingType = ? WHERE PostId = ?";
                break;
            case "4": // Description
                query = "UPDATE Post SET Description = ? WHERE PostId = ?";
                break;
            case "5": // Country
                query = "UPDATE Post SET Country = ? WHERE PostId = ?";
                break;
            case "6": // City
                query = "UPDATE Post SET City = ? WHERE PostId = ?";
                break;
            case "7": // Address
                query = "UPDATE Post SET Address = ? WHERE PostId = ?";
                break;
            case "8": // Price
                query = "UPDATE Post SET Price = ? WHERE PostId = ?";
                break;
            case "9": // Bedrooms
                query = "UPDATE Post SET Bedrooms = ? WHERE PostId = ?";
                break;
            case "10": // Bathrooms
                query = "UPDATE Post SET Bathrooms = ? WHERE PostId = ?";
                break;
            case "11": // Area
                query = "UPDATE Post SET Area = ? WHERE PostId = ?";
                break;
            case "12": // Status
                query = "UPDATE Post SET Status = ? WHERE PostId = ?";
                break;
            case "13": // Owner Contact Info
                query = "UPDATE Post SET OwnerContactInfo = ? WHERE PostId = ?";
                break;
            default:
                return false; // Invalid field choice
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newValue);  // Set the new value
            pstmt.setInt(2, Integer.parseInt(postId));  // Set the Post ID

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if a row was successfully updated
        } catch (SQLException e) {
            System.err.println("Error updating post: " + e.getMessage());
            return false;
        }
    }

    public  boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM User WHERE Username = ?"; // Adjust table name and column name as necessary
        try (Connection conn = Database.connect(); // Assuming you have a Database class for connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if username exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking username in database: " + e.getMessage());
        }
        return false; // Username does not exist
    }

    public void savePost(String title, String listingType, String type, double area, String contactInfo,String country, String city, String address, String description, double price, int bedrooms, int bathrooms) {
        String sql = "INSERT INTO post(userId, ListingType, Type, Title, Description, country, city, address, price, bedrooms, bathrooms, Area, Status, OwnerContactInfo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Database connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int userId = Integer.parseInt(UserSession.getUserId());
            // Set the parameters
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, listingType);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, title);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, country);
            preparedStatement.setString(7, city);
            preparedStatement.setString(8, address);
            preparedStatement.setDouble(9, price);
            preparedStatement.setInt(10, bedrooms);
            preparedStatement.setInt(11, bathrooms);
            preparedStatement.setDouble(12, area);
            preparedStatement.setString(13, "Available");
            preparedStatement.setString(14, contactInfo);

            // Execute the insert operation
            int rowsAffected = preparedStatement.executeUpdate();

            // Log or handle the success/failure if needed
            if (rowsAffected > 0) {
                System.out.println("Post created successfully!");
            } else {
                System.out.println("Failed to create post.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
