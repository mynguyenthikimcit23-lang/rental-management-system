import java.util.Scanner;
import java.time.LocalDate;

public class TestRentPropertyInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RentPropertyInput input = new RentPropertyInput(scanner);

        int tenantId = input.readTenantId();

        LocalDate[] dates = input.readRentalDates();

        System.out.println("\n===== RENT INFORMATION =====");
        System.out.println("Tenant ID: " + tenantId);
        System.out.println("Start Date: " + dates[0]);
        System.out.println("End Date: " + dates[1]);
    }
}
