package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.RentalController;
import model.Property;
import model.PropertyOwner;
import model.RentalContract;
import model.Tenant;

public class ConsoleView {
    private final RentalController controller;
    private final PropertyOwner owner;
    private final Tenant tenant;
    private final Scanner scanner;
    private final Map<RentalContract, Property> rentedPropertyByContract = new HashMap<>();

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
                    rentProperty();
                    break;
                case "3":
                    terminateContract();
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
            System.out.println("Property available: " + property.getAvailable());
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create property: " + e.getMessage());
        }
    }

    private void rentProperty() {
        List<Property> availableProperties = new ArrayList<>();
        for (Property property : owner.getProperties()) {
            if (property.getAvailable()) {
                availableProperties.add(property);
            }
        }

        if (availableProperties.isEmpty()) {
            System.out.println("There are no available properties to rent.");
            return;
        }

        System.out.println("Available properties:");
        for (int i = 0; i < availableProperties.size(); i++) {
            Property property = availableProperties.get(i);
            System.out.printf("%d. %s (ID: %d, monthly rate: %.2f)%n",
                    i + 1, property.getDescription(), property.getPropertyId(), property.getRentalRate());
        }

        int propertyChoice = readSelection("Choose a property: ", availableProperties.size());
        Property property = availableProperties.get(propertyChoice - 1);

        try {
            int contractId = readInt("Contract ID: ");
            LocalDate startDate = readDate("Start date (yyyy-MM-dd): ");
            LocalDate endDate = readDate("End date (yyyy-MM-dd): ");

            RentalContract contract = controller.rentProperty(
                    contractId, tenant, property, startDate, endDate);

            if (contract == null) {
                System.out.println("Rental was rejected. Check the contract ID, date range, and property availability.");
                return;
            }

            rentedPropertyByContract.put(contract, property);
            System.out.println("Property rented successfully by " + tenant.getFullName() + ".");
            System.out.println("Property available: " + property.getAvailable());
        } catch (IllegalArgumentException e) {
            System.out.println("Could not rent property: " + e.getMessage());
        }
    }

    private void terminateContract() {
        List<RentalContract> activeContracts = new ArrayList<>();
        for (RentalContract contract : tenant.getRentalContracts()) {
            if (contract.getActive()) {
                activeContracts.add(contract);
            }
        }

        if (activeContracts.isEmpty()) {
            System.out.println("There are no active contracts to terminate.");
            return;
        }

        System.out.println("Active contracts:");
        for (int i = 0; i < activeContracts.size(); i++) {
            RentalContract contract = activeContracts.get(i);
            Property contractProperty = rentedPropertyByContract.get(contract);
            if (contractProperty == null) {
                System.out.printf("%d. Active contract (monthly rent: %.2f)%n",
                        i + 1, contract.getRentAmount());
            } else {
                System.out.printf("%d. %s (monthly rent: %.2f)%n",
                        i + 1, contractProperty.getDescription(), contract.getRentAmount());
            }
        }

        int contractChoice = readSelection("Choose a contract to terminate: ", activeContracts.size());
        RentalContract contract = activeContracts.get(contractChoice - 1);
        Property property = rentedPropertyByContract.get(contract);

        try {
            controller.terminateContract(contract);
            System.out.println("Contract terminated successfully.");
            System.out.println("Contract active: " + contract.getActive());
            if (property != null) {
                System.out.println("Property available: " + property.getAvailable());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Could not terminate contract: " + e.getMessage());
        }
    }

    private int readSelection(String prompt, int itemCount) {
        while (true) {
            int choice = readInt(prompt);
            if (choice >= 1 && choice <= itemCount) {
                return choice;
            }
            System.out.println("Choose a number from 1 to " + itemCount + ".");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use yyyy-MM-dd, for example 2026-11-14.");
            }
        }
    }
}
