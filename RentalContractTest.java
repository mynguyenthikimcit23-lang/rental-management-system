import java.time.LocalDate;

public class RentalContractTest {
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
                30, "Linh", "Le", "linh@example.com");

        LocalDate startDate = LocalDate.of(2026, 11, 1);
        LocalDate endDate = LocalDate.of(2027, 10, 31);

        // Valid contract
        Property validProperty = new Property(
                201, "House", 5000);

        RentalContract contract = new RentalContract(
                2001, validProperty, tenant, startDate, endDate);

        check("Valid contract is active",
                contract.getActive());
        check("Valid contract copies the property rent",
                contract.getRentAmount() == 5000);
        check("Valid contract makes property unavailable",
                !validProperty.getAvailable());

        // Contract ID validation
        Property propertyForZeroId = new Property(
                202, "Apartment", 4000);

        expectInvalid("Contract ID cannot be zero",
                () -> new RentalContract(
                        0, propertyForZeroId, tenant, startDate, endDate));
        check("Zero contract ID leaves property available",
                propertyForZeroId.getAvailable());

        Property propertyForNegativeId = new Property(
                203, "Apartment", 4000);

        expectInvalid("Contract ID cannot be negative",
                () -> new RentalContract(
                        -4, propertyForNegativeId, tenant, startDate, endDate));
        check("Negative contract ID leaves property available",
                propertyForNegativeId.getAvailable());

        // Required objects
        Property propertyForNullTenant = new Property(
                204, "Apartment", 4000);

        expectInvalid("Tenant cannot be null",
                () -> new RentalContract(
                        2004, propertyForNullTenant, null, startDate, endDate));
        check("Null tenant leaves property available",
                propertyForNullTenant.getAvailable());

        expectInvalid("Property cannot be null",
                () -> new RentalContract(
                        2005, null, tenant, startDate, endDate));

        // Date validation
        Property propertyForNullStart = new Property(
                206, "Apartment", 4000);

        expectInvalid("Start date cannot be null",
                () -> new RentalContract(
                        2006, propertyForNullStart, tenant, null, endDate));
        check("Null start date leaves property available",
                propertyForNullStart.getAvailable());

        Property propertyForNullEnd = new Property(
                207, "Apartment", 4000);

        expectInvalid("End date cannot be null",
                () -> new RentalContract(
                        2007, propertyForNullEnd, tenant, startDate, null));
        check("Null end date leaves property available",
                propertyForNullEnd.getAvailable());

        Property propertyForEqualDates = new Property(
                208, "Apartment", 4000);

        expectInvalid("End date must be after start date",
                () -> new RentalContract(
                        2008, propertyForEqualDates, tenant, startDate, startDate));
        check("Equal dates leave property available",
                propertyForEqualDates.getAvailable());

        Property propertyForReversedDates = new Property(
                209, "Apartment", 4000);

        expectInvalid("End date cannot be before start date",
                () -> new RentalContract(
                        2009,
                        propertyForReversedDates,
                        tenant,
                        endDate,
                        startDate));
        check("Reversed dates leave property available",
                propertyForReversedDates.getAvailable());

        // Property must be available
        Property unavailableProperty = new Property(
                210, "Apartment", 4000);
        unavailableProperty.setAvailable(false);

        expectInvalid("Unavailable property cannot be rented",
                () -> new RentalContract(
                        2010, unavailableProperty, tenant, startDate, endDate));
        check("Failed contract leaves unavailable property unchanged",
                !unavailableProperty.getAvailable());

        System.out.println("\n===== TEST RESULTS =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}