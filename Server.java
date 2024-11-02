import java.net.*;
import java.io.*;
import controller.*;

public class Server {
    private static final int PORT = 1235;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for client...");
        Socket socket = serverSocket.accept();
        System.out.println("LOG: Client connected");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        UserController userController = new UserController();

        boolean continueRunning = true;
        while (continueRunning) {
            try {
                String command = reader.readLine();
                if (command == null) {
                    break;
                }

                switch (command) {
                    case "REGISTER":
                        handleRegister(reader, writer, userController);
                        break;
                    case "LOGIN":
                        handleLogin(reader, writer, userController);
                        break;
                    case "CREATE_POST":
                        handleCreatePost(reader, writer, userController);
                        break;
                    case "VIEW_POSTS":
                        handleViewPosts(reader, writer, userController);
                        break;
                    case "UPDATE_POST":
                        handleUpdatePost(reader, writer, userController);
                        break;
                    case "DELETE_POST":
                        handleDeletePost(reader, writer, userController);
                        break;
                    case "LOGOUT":
                        handleLogout(reader, writer, userController);
                        break;
                    case "EXIT":
                        continueRunning = false;                       
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            } catch (SocketException e) {
                System.out.println("LOG: Client disconnected");
                break;
            }
        }

        reader.close();
        writer.close();
        socket.close();
        serverSocket.close();
    }

    private static void handleRegister(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        String name = reader.readLine();
        String username = reader.readLine();
        String email = reader.readLine();
        String password = reader.readLine();
        String phoneNo = reader.readLine();
        String address = reader.readLine();
        userController.registerUser(name, username, email, password, phoneNo, address, writer);
        System.out.println("LOG: Registered " + username);
    }

    private static void handleLogin(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        String username = reader.readLine();
        String password = reader.readLine();
        String userRole = userController.handleLogin(username, password, writer);
        if (userRole != null) {
            System.out.println("LOG: Logged " + username + " in as " + userRole);
            writer.println(userRole); // send the role back to the client
        } else {
            System.out.println("LOG: User failed to login.");
            writer.println("");
        }
    }

    private static void handleCreatePost(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        // Handle CREATE_POST command
        // Add your logic here
    }

    private static void handleViewPosts(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        // Handle VIEW_POSTS command
        // Add your logic here
    }

    private static void handleUpdatePost(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        // Handle UPDATE_POST command
        // Add your logic here
    }

    private static void handleDeletePost(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        // Handle DELETE_POST command
        // Add your logic here
    }

    private static void handleLogout(BufferedReader reader, PrintWriter writer, UserController userController) throws IOException {
        // Handle LOGOUT command
        // Add your logic here
    }
   
}