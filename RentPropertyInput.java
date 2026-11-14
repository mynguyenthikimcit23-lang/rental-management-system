import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class RentPropertyInput {
    private Scanner scanner;

    public RentPropertyInput(Scanner scanner) {
        this.scanner = scanner;
    }

    // Read and convert String to LocalDate
    public LocalDate readDate(String message) {
        while (true) {
            System.out.print(message + " (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please try again.");
            }
        }

    }

    // Read and validate rental dates
    public LocalDate[] readRentalDates() {
        LocalDate startDate = readDate("Enter Start Date");
        LocalDate endDate;
        while (true) {
            endDate = readDate("Enter End Date");
            if (endDate.isAfter(startDate)) {
                break;
            }
            System.out.println("End date must be after start date!");
        }
        return new LocalDate[] { startDate, endDate };
    }

    public int readTenantId() {
        while (true) {
            System.out.print("Enter Tenant ID: ");

            String input = scanner.nextLine().trim();

            try {
                int tenantId = Integer.parseInt(input);

                if (tenantId > 0) {
                    return tenantId;
                }

                System.out.println("Tenant ID must be positive!");

            } catch (NumberFormatException e) {
                System.out.println("Invalid Tenant ID! Please enter an integer.");
            }
        }
    }
}
