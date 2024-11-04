package controller;

import data.Database;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class PostController {
    private Database database;

    public PostController() {
        database = new Database(); // Initialize the Database instance
    }

    public void createPost(PrintWriter writer, Scanner scanner) {
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
            writer.println("CREATE_POST");
            //writer.println(userId);  // TODO: FIND A WAY
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

    public void viewPosts(PrintWriter writer) {
        List<String> posts = database.printPosts(); // Get the list of posts
        for (String post : posts) {
            writer.println(post); // Send each post to the client
        }
        writer.println("END_OF_POSTS"); // Indicate the end of the posts
    }

    public String validateInput(Scanner scanner, String prompt, String regex, String errorMessage) {
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
}
