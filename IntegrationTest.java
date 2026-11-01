import java.time.LocalDate;

public class IntegrationTest {
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

    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2026, 11, 1);
        LocalDate endDate = LocalDate.of(2027, 10, 31);

        PropertyOwner owner = new PropertyOwner(
                1, "Anna", "Nguyen", "anna@example.com");
        Tenant tenant = new Tenant(
                2, "Minh", "Tran", "minh@example.com");

        check("Owner and tenant profiles are initialized",
                owner.getFullName().equals("Anna Nguyen")
                        && tenant.getFullName().equals("Minh Tran"));

        // Owner creates a property
        Property property = owner.createProperty(
                101, "Apartment A101, two bedrooms", 5000);

        check("Owner successfully creates property",
                property != null);
        check("Created property is registered with owner",
                owner.getProperties().size() == 1
                        && owner.getProperties().get(0) == property);
        check("New property starts available",
                property.getAvailable());

        // Failed property creation must not change the owner's list
        int ownerPropertyCount = owner.getProperties().size();

        Property invalidProperty = owner.createProperty(
                102, "   ", 5000);

        check("Owner rejects property with blank description",
                invalidProperty == null);
        check("Failed property creation leaves owner's list unchanged",
                owner.getProperties().size() == ownerPropertyCount);

        // Tenant rents the property
        RentalContract firstContract = tenant.rentProperty(
                1001, property, startDate, endDate);

        check("Tenant successfully rents available property",
                firstContract != null);
        check("Rental contract is registered with tenant",
                tenant.getRentalContracts().size() == 1
                        && tenant.getRentalContracts().get(0) == firstContract);
        check("Renting makes property unavailable",
                !property.getAvailable());
        check("New contract starts active",
                firstContract.getActive());
        check("Contract stores rent agreed at creation",
                firstContract.getRentAmount() == 5000);

        // Changing the property's rate must not alter an existing contract
        property.updateRentalRate(5500);

        check("Property rental rate is updated",
                property.getRentalRate() == 5500);
        check("Existing contract keeps its original rent",
                firstContract.getRentAmount() == 5000);

        // A second rental attempt while the property is unavailable must fail
        int contractCount = tenant.getRentalContracts().size();

        RentalContract rejectedContract = tenant.rentProperty(
                1002, property, startDate, endDate);

        check("Unavailable property cannot be rented again",
                rejectedContract == null);
        check("Rejected rental does not add a contract",
                tenant.getRentalContracts().size() == contractCount);
        check("Rejected rental leaves property unavailable",
                !property.getAvailable());

        // Terminating the contract should release the property
        firstContract.terminate();

        check("Terminated contract becomes inactive",
                !firstContract.getActive());
        check("Terminating contract makes property available",
                property.getAvailable());
        check("Terminated contract remains in tenant's history",
                tenant.getRentalContracts().size() == 1
                        && tenant.getRentalContracts().get(0) == firstContract);

        // Termination should be safe to call more than once
        firstContract.terminate();

        check("Repeated termination leaves contract inactive",
                !firstContract.getActive());
        check("Repeated termination leaves property available",
                property.getAvailable());

        // The released property can be rented again
        RentalContract secondContract = tenant.rentProperty(
                1003, property, startDate, endDate);

        check("Available property can be rented again",
                secondContract != null);
        check("Second contract is added to tenant's history",
                tenant.getRentalContracts().size() == 2
                        && tenant.getRentalContracts().get(1) == secondContract);
        check("Second contract is active",
                secondContract.getActive());
        check("Second rental makes property unavailable",
                !property.getAvailable());
        check("Second contract uses the updated rental rate",
                secondContract.getRentAmount() == 5500);

        // Calling terminate on the old inactive contract must not release a
        // property that has since been rented under a new contract.
        firstContract.terminate();

        check("Old contract remains inactive after second rental",
                !firstContract.getActive());
        check("Old contract cannot make newly rented property available",
                !property.getAvailable());

        // Failed rental with invalid dates must leave state unchanged
        Property anotherProperty = owner.createProperty(
                103, "Studio B103", 3000);
        int contractsBeforeInvalidDates = tenant.getRentalContracts().size();

        RentalContract invalidDateContract = tenant.rentProperty(
                1004,
                anotherProperty,
                LocalDate.of(2027, 10, 31),
                LocalDate.of(2026, 11, 1));

        check("Rental with reversed dates is rejected",
                invalidDateContract == null);
        check("Invalid-date rental does not add a contract",
                tenant.getRentalContracts().size() == contractsBeforeInvalidDates);
        check("Invalid-date rental leaves property available",
                anotherProperty.getAvailable());

        System.out.println("\n===== INTEGRATION TEST RESULTS =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}