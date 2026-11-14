package view;

import java.util.Scanner;

import controller.RentalController;
import model.Property;
import model.PropertyOwner;
import model.Tenant;

public class ConsoleView {
    private final RentalController controller;
    private final PropertyOwner owner;
    private final Tenant tenant;
    private final Scanner scanner;

    public ConsoleView(RentalController controller, PropertyOwner owner, Tenant tenant) {
        this(controller, owner, tenant, new Scanner(System.in));
    }

    public ConsoleView(RentalController controller, PropertyOwner owner, Tenant tenant, Scanner scanner) {
        if (controller == null || owner == null || tenant == null || scanner == null) {
            throw new IllegalArgumentException("Controller, owner, tenant, and scanner are required.");
        }
        this.controller = controller;
        this.owner = owner;
        this.tenant = tenant;
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createProperty();
                    break;
                case "2":
                    System.out.println("Rent property is not connected to RentalController yet.");
                    break;
                case "3":
                    System.out.println("Terminate contract is not connected to RentalController yet.");
                    break;
                case "4":
                    running = false;
                    System.out.println("Exiting Rental Management System.");
                    break;
                default:
                    System.out.println("Invalid menu option. Please choose 1, 2, 3, or 4.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Rental Management System ===");
        System.out.println("1. Create property");
        System.out.println("2. Rent property");
        System.out.println("3. Terminate contract");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private void createProperty() {
        try {
            int propertyId = readInt("Property ID: ");
            System.out.print("Description: ");
            String description = scanner.nextLine();
            double rentalRate = readDouble("Monthly rental rate: ");

            Property property = controller.createProperty(owner, propertyId, description, rentalRate);
            if (property == null) {
                System.out.println("Property was not created. Check the entered values.");
                return;
            }

            System.out.println("Property created successfully: " + property.getDescription());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid ID and rental rate.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create property: " + e.getMessage());
        }
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private double readDouble(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }
}
