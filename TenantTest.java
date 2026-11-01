import java.time.LocalDate;

public class TenantTest {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + name);
            passed++;
        } else {
            System.out.println("FAIL: " + name);
            failed++;
        }
    }

    private static void expectInvalid(String name, Runnable action) {
        try {
            action.run();
            check(name, false);
        } catch (IllegalArgumentException e) {
            check(name, true);
        }
    }

    public static void main(String[] args) {
        Tenant tenant = new Tenant(
                20, "Minh", "Tran", "minh@example.com");

        // Tenant inherits User validation
        expectInvalid("Tenant rejects non-positive user ID",
                () -> new Tenant(
                        0, "Minh", "Tran", "minh@example.com"));

        expectInvalid("Tenant rejects blank name",
                () -> new Tenant(
                        21, "Minh", "   ", "minh@example.com"));

        expectInvalid("Tenant rejects malformed email",
                () -> new Tenant(
                        22, "Minh", "Tran", "minh@@example.com"));

        LocalDate startDate = LocalDate.of(2026, 11, 1);
        LocalDate endDate = LocalDate.of(2027, 10, 31);

        // Successful rental
        Property validProperty = new Property(
                101, "Apartment A101", 5000);

        RentalContract contract = tenant.rentProperty(
                1001, validProperty, startDate, endDate);

        check("Valid rental returns a contract",
                contract != null);
        check("Successful rental adds contract to tenant",
                tenant.getRentalContracts().size() == 1
                        && tenant.getRentalContracts().get(0) == contract);
        check("Successful rental makes property unavailable",
                !validProperty.getAvailable());

        // Invalid contract ID: no contract added, property remains available
        Property propertyForInvalidId = new Property(
                102, "Apartment A102", 4500);
        int oldSize = tenant.getRentalContracts().size();

        RentalContract invalidIdContract = tenant.rentProperty(
                0, propertyForInvalidId, startDate, endDate);

        check("Invalid contract ID returns null",
                invalidIdContract == null);
        check("Invalid contract ID does not add a contract",
                tenant.getRentalContracts().size() == oldSize);
        check("Invalid contract ID leaves property available",
                propertyForInvalidId.getAvailable());

        // Reversed dates: no contract added, property remains available
        Property propertyForReversedDates = new Property(
                103, "Apartment A103", 4200);

        RentalContract reversedDatesContract = tenant.rentProperty(
                1003,
                propertyForReversedDates,
                LocalDate.of(2027, 10, 31),
                LocalDate.of(2026, 11, 1));

        check("Reversed dates return null",
                reversedDatesContract == null);
        check("Reversed dates do not add a contract",
                tenant.getRentalContracts().size() == oldSize);
        check("Reversed dates leave property available",
                propertyForReversedDates.getAvailable());

        // Null dates: no contract added, property remains available
        Property propertyForNullDate = new Property(
                104, "Apartment A104", 4000);

        RentalContract nullDateContract = tenant.rentProperty(
                1004, propertyForNullDate, null, endDate);

        check("Null date returns null",
                nullDateContract == null);
        check("Null date does not add a contract",
                tenant.getRentalContracts().size() == oldSize);
        check("Null date leaves property available",
                propertyForNullDate.getAvailable());

        // Unavailable property: no contract added, remains unavailable
        Property unavailableProperty = new Property(
                105, "Apartment A105", 3900);
        unavailableProperty.setAvailable(false);

        RentalContract unavailableContract = tenant.rentProperty(
                1005, unavailableProperty, startDate, endDate);

        check("Unavailable property returns null",
                unavailableContract == null);
        check("Unavailable property does not add a contract",
                tenant.getRentalContracts().size() == oldSize);
        check("Unavailable property remains unavailable",
                !unavailableProperty.getAvailable());

        System.out.println("\n===== TEST RESULTS =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}