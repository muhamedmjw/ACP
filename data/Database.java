package data;
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

    public String loadUser(String username, String password) {
      String role = null;
      String query = "SELECT roleId FROM user WHERE username = ? AND password = ?";
      try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
           PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
          int roleId = rs.getInt("roleId");
          role = (roleId == 1) ? "CLIENT" : "ADMIN"; // Map roleId to role
        }
      } catch (SQLException e) {
        System.err.println("Error during login: " + e.getMessage());
      }
      return role; // Returns the role or null if not found
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
                        rs.getString("Country") + " | " +
                        rs.getDouble("Price") + " | " +
                        rs.getString("Status");
                posts.add(postInfo); // Add to the list
            }
        } catch (SQLException e) {
            System.err.println("Error loading posts: " + e.getMessage());
        }
        return posts; // Return the list of posts
    }


}
