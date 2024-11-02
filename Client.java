import java.net.*;
import java.io.*;
import java.util.*;
import view.*;

public class Client {

    public static void main(String[] args) {
        boolean loggedIn = false;
        String userRole = null;

        try {
            Socket socket = new Socket("localhost", 1235);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            DisplayMenu displayMenu = new DisplayMenu();
            ClientMenu clientMenu = new ClientMenu();
            AdminMenu adminMenu = new AdminMenu();
            System.out.println("--- WELCOME TO THE REAL ESTATE APPLICATION ---");


            while (!loggedIn) {
                displayMenu.displayMenu();
                int userChoice = getUserChoice(scanner);
                if (userChoice == 2) {
                    userRole = processUserChoice(userChoice, writer, scanner, reader);
                    loggedIn = userRole != null;
                } else {
                    processUserChoice(userChoice, writer, scanner, reader);
                }
            }


            while (loggedIn) {
                if (userRole.equals("ADMIN")) {
                    adminMenu.adminMenu();
                } else if (userRole.equals("CLIENT")) {
                    clientMenu.clientMenu();
                }
                //int userChoice = getUserChoice(scanner);
                // userRole = processUserChoice(userChoice, writer, scanner, reader);
                // if (userRole == null) {
                //     loggedIn = false;
                // }
            }


        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static int getUserChoice(Scanner scanner) {
        try {
            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over
            return userChoice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.next(); // Clear invalid input
            return getUserChoice(scanner); // Recursively call until valid input
        }
    }


    private static String processUserChoice(int userChoice, PrintWriter writer, Scanner scanner, BufferedReader reader) {
        if (userChoice == 1) {
            registerUser(writer, scanner, reader);
            return null;


        } else if (userChoice == 2) {
            return loginUser(writer, scanner, reader);


        } else if (userChoice == 3) {
            writer.println("EXIT");
            System.exit(0);
            return null;


        } else {
            System.out.println("Invalid choice");
            return null;
        }
    }


    private static void registerUser(PrintWriter writer, Scanner scanner, BufferedReader reader) {
        String name = validateInput(scanner, "Enter your name:", "[a-zA-Z]+([ ][a-zA-Z]+)*", "Invalid name. Please enter a valid name.");
        String username = uniqueUsernameChecker(scanner);
        String email = validateInput(scanner, "Enter your email:", "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", "Invalid email. Please enter a valid email.");
        String password = validateInput(scanner, "Enter your password:", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", "Invalid password. Minimum eight characters, at least one letter and one number");
        String phoneNo = validateInput(scanner, "Enter your phone number (format: 000-000-0000):", "^\\d{3}-?\\d{3}-?\\d{4}$", "Invalid phone number format. Please use the format 000-000-0000.");
        String address = validateInput(scanner, "Enter your address:", "^.*$", "Invalid address. Please enter a valid address.");

        writer.println("REGISTER");
        writer.println(name);
        writer.println(username);
        writer.println(email);
        writer.println(password);
        writer.println(phoneNo);
        writer.println(address);
        try{
            String response = reader.readLine();
            System.out.println(response);
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static String loginUser(PrintWriter writer, Scanner scanner, BufferedReader reader) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
    
        writer.println("LOGIN");
        writer.println(username);
        writer.println(password);
    
        try {
            String response = reader.readLine();
            if (response.equals("CLIENT") || response.equals("ADMIN")) {
                System.out.println("Login successful");
                return response; // Login successful
            } else {
                System.out.println("Invalid credentials. Please try again.");
                return null; // Login failed
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    private static String validateInput(Scanner scanner, String prompt, String regex, String errorMessage) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.matches(regex)) {
                break; // Valid
            } else {
                System.out.println(errorMessage);
            }
        }
        return input;
    }


    private static String uniqueUsernameChecker(Scanner scanner) {
        String username = validateInput(scanner, "Enter your username:", "^[a-zA-Z][a-zA-Z0-9_]{2,14}$", "Invalid username. It should start with a letter, be between 3 and 15 characters long, and contain only letters, digits, or underscores.");
    
        if (usernameExists(username)) {
            System.out.println("Username already taken. Please choose another one.");
            return uniqueUsernameChecker(scanner);
        }
    
        return username;
    }


    private static boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("data\\UserData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[5].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data file: " + e.getMessage());
        }
        return false;
    }


}