package controller;

public class UserSession {
    private static String userId; // Add this field

    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }
}
