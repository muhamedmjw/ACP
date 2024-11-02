package model;
public class User {
    private int userId;
    private int roleId;
    private String name;
    private String address;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;

    // Constructor
    public User(int userId, int roleId, String name, String address, String phoneNumber, 
                String username, String email, String password) {
        this.userId = userId;
        this.roleId = roleId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "User [UserID=" + userId + ", RoleID=" + roleId + ", Name=" + name 
               + ", Address=" + address + ", Phone=" + phoneNumber 
               + ", Username=" + username + ", Email=" + email + "]";
    }
}

