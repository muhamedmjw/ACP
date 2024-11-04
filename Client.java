import java.net.*;
import java.io.*;
import java.util.*;

import data.Database;
import view.*;
import controller.*;

public class Client {
    static boolean loggedIn = false;
    static boolean exit = false;
    static String userRole = null;

    public static void main(String[] args) {


        try {
            Socket socket = new Socket("localhost", 1235);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            DisplayMenu displayMenu = new DisplayMenu();
            ClientMenu clientMenu = new ClientMenu();
            AdminMenu adminMenu = new AdminMenu();

            System.out.println("--- WELCOME TO THE REAL ESTATE APPLICATION ---");

            while(!exit){

                while (!loggedIn) {
                    displayMenu.displayMenu();
                    int userChoice = getUserChoice(scanner);

                    if (userChoice == 1){
                        processUserChoice(userChoice, writer, scanner, reader);
                    } else if (userChoice == 2) {
                        userRole = processUserChoice(userChoice, writer, scanner, reader);
                        loggedIn = userRole != null;
                    } else {
                        exit = true;
                        break;
                    }
                }

                while (loggedIn) {
                    if (userRole.equals("ADMIN")) {
                        adminMenu.adminMenu();
                    } else if (userRole.equals("CLIENT")) {
                        clientMenu.clientMenu();
                    }

                    int userChoice = getUserChoice(scanner);
                    if (userRole.equals("ADMIN")) {
                        processAdminChoice(userChoice, writer, scanner, reader);
                    } else if (userRole.equals("CLIENT")) {
                        processClientChoice(userChoice, writer, scanner, reader);
                    }
                }

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
            writeRegistrationData(writer, scanner, reader);
            return null;

        } else if (userChoice == 2) {
            return writeLoginData(writer, scanner, reader);

        } else if (userChoice == 3) {
            writer.println("EXIT");
            System.exit(0);
            return null;

        } else {
            System.out.println("Invalid choice");
            return null;
        }
    }

    private static void processClientChoice(int userChoice, PrintWriter writer, Scanner scanner, BufferedReader reader) throws IOException {
        PostController postController = new PostController();
        switch (userChoice) {
            case 1:
                writer.println("CREATE_POST");
                writePostData(writer, reader, scanner);
                break;

            case 2:
                writer.println("VIEW_POSTS");
                try {
                    String post;
                    while (!(post = reader.readLine()).equals("END_OF_POSTS")) {
                        System.out.println(post);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading posts: " + e.getMessage());
                }
                break;

            case 3:
                updatePostData(writer, reader, scanner);
                break;

            case 4:
                writer.println("VIEW_POSTS");
                try {
                    String post;
                    while (!(post = reader.readLine()).equals("END_OF_POSTS")) {
                        System.out.println(post);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading posts: " + e.getMessage());
                }

                writer.println("DELETE_POST");
                System.out.print("Enter the Post ID to delete: ");
                String postId = scanner.nextLine();
                writer.println(postId);

                // Wait for server confirmation
                String response = reader.readLine();  // Read the response from the server
                System.out.println(response);  // Print the response to the console
                break;

            case 5:
                writer.println("LOGOUT");
                System.out.println("You have logged out successfully.");
                userRole = null;
                loggedIn = false;
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void processAdminChoice(int userChoice, PrintWriter writer, Scanner scanner, BufferedReader reader) {

    }

    private static void writeRegistrationData(PrintWriter writer, Scanner scanner, BufferedReader reader) {
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

    private static String writeLoginData(PrintWriter writer, Scanner scanner, BufferedReader reader) {
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

    private static void updatePostData(PrintWriter writer, BufferedReader reader, Scanner scanner) throws IOException {
        writer.println("VIEW_POSTS");
        try {
            String post;
            while (!(post = reader.readLine()).equals("END_OF_POSTS")) {
                System.out.println(post);
            }
        } catch (IOException e) {
            System.out.println("Error reading posts: " + e.getMessage());
        }

        writer.println("UPDATE_POST");

        // Now, prompt the user to enter the Post ID to update
        System.out.print("Enter ID to update: ");
        String postId = scanner.nextLine();
        writer.println(postId);

        // Check for Post ID existence
        String idResponse = reader.readLine();  // Read response from the server about ID existence
        if (idResponse.equals("POST_NOT_FOUND")) {
            System.out.println("Post ID does not exist.");
            return; // Exit the method
        }else{
            System.out.println("Enter a field to update:");
        }

        String fieldChoice;
        do {
            System.out.println("1. Title");
            System.out.println("2. Type");
            System.out.println("3. Listing Type");
            System.out.println("4. Description");
            System.out.println("5. Country");
            System.out.println("6. City");
            System.out.println("7. Address");
            System.out.println("8. Price");
            System.out.println("9. Bedrooms");
            System.out.println("10. Bathrooms");
            System.out.println("11. Area");
            System.out.println("12. Status");
            System.out.println("13. Owner Contact Info");
            System.out.println("0. Exit Updating");
            fieldChoice = scanner.nextLine();
            writer.println(fieldChoice);

            if (fieldChoice.equals("0")) {
                System.out.println("Update process cancelled.");
                return; // Exit the method without proceeding to the new value prompt
            }

            // Check if fieldChoice is valid
            if (Integer.parseInt(fieldChoice) < 1 || Integer.parseInt(fieldChoice) > 13) {
                System.out.println("Invalid field choice. Please enter a valid number.");
            }
        } while (Integer.parseInt(fieldChoice) < 1 || Integer.parseInt(fieldChoice) > 13);

        // Get the new value for the chosen field
        System.out.print("Enter the new value: ");
        String newValue = scanner.nextLine();
        writer.println(newValue);  // Send the new value to the server

        // Wait for server confirmation
        String response = reader.readLine();  // Read the response from the server
        System.out.println(response);  // Print the response to the console
    }

    private static void writePostData(PrintWriter writer, BufferedReader reader, Scanner scanner){
        try {
            System.out.println("\nCreating a new post...");

            // Validate mandatory fields
            String title = validateInput(scanner, "Enter post title:", "^(?!\\s).+$", "Title cannot be empty.");
            String type = validateInput(scanner, "Enter post type (Apartment, House, Warehouse, or Others):", "^(Apartment|House|Warehouse|Others)$", "Invalid type.");
            String listingType = validateInput(scanner, "Enter post listing type (Rent or Buy):", "^(Rent|Buy)$", "Invalid listing type.");
            double area = Double.parseDouble(validateInput(scanner, "Enter property area size (in square meters):", "^[1-9]\\d*(\\.\\d+)?$", "Invalid area size."));
            String contactInfo = validateInput(scanner, "Enter contact information:", "^(?!\\s).+$", "Contact information cannot be empty.");
            String country = validateInput(scanner, "Enter post country:", "^(?!\\s).+$", "Country cannot be empty.");
            String city = validateInput(scanner, "Enter post city:", "^(?!\\s).+$", "City cannot be empty.");
            String address = validateInput(scanner, "Enter post address:", "^(?!\\s).+$", "Address cannot be empty.");
            String description = validateInput(scanner, "Enter post description (optional):", "^(?!\\s).*$", "Description cannot be empty if provided.");
            double price = Double.parseDouble(validateInput(scanner, "Enter post price: ", "^[1-9]\\d*(\\.\\d+)?$", "Invalid price."));
            int bedrooms = Integer.parseInt(validateInput(scanner, "Enter number of bedrooms: ", "^[0-9]+$", "Invalid number of bedrooms."));
            int bathrooms = Integer.parseInt(validateInput(scanner, "Enter number of bathrooms: ", "^[0-9]+$", "Invalid number of bathrooms."));

            // Call method to save post to database
            writer.println(title);
            writer.println(listingType);
            writer.println(type);
            writer.println(area);
            writer.println(contactInfo);
            writer.println(country);
            writer.println(city);
            writer.println(address);
            writer.println(description);
            writer.println(price);
            writer.println(bedrooms);
            writer.println(bathrooms);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data for each field.");
            scanner.nextLine(); // Clear the invalid input
        } catch (Exception e) {
            System.out.println("An error occurred while creating the post: " + e.getMessage());
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
        Database database = new Database();
        if (database.usernameExists(username)) {
            System.out.println("Username already taken. Please choose another one.");
            return uniqueUsernameChecker(scanner);
        }
        return username;
    }

}