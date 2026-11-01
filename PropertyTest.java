public class PropertyTest {
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
        // Valid constructor
        Property property = new Property(101, "Apartment A101", 5000.50);

        check("Valid constructor stores values",
                property.getPropertyId() == 101
                        && property.getDescription().equals("Apartment A101")
                        && property.getRentalRate() == 5000.50
                        && property.getAvailable());

        // Property ID validation
        expectInvalid("Property ID cannot be zero",
                () -> new Property(0, "Apartment", 5000));
        expectInvalid("Property ID cannot be negative",
                () -> new Property(-1, "Apartment", 5000));

        // Description validation
        expectInvalid("Description cannot be null",
                () -> new Property(102, null, 5000));
        expectInvalid("Description cannot be empty",
                () -> new Property(102, "", 5000));
        expectInvalid("Description cannot contain only whitespace",
                () -> new Property(102, "   ", 5000));

        // Rental rate validation in constructor
        expectInvalid("Rental rate cannot be zero",
                () -> new Property(103, "House", 0));
        expectInvalid("Rental rate cannot be negative",
                () -> new Property(103, "House", -50));
        expectInvalid("Rental rate cannot be NaN",
                () -> new Property(103, "House", Double.NaN));
        expectInvalid("Rental rate cannot be positive infinity",
                () -> new Property(103, "House", Double.POSITIVE_INFINITY));
        expectInvalid("Rental rate cannot be negative infinity",
                () -> new Property(103, "House", Double.NEGATIVE_INFINITY));

        // Valid rental rate update
        property.updateRentalRate(5500.75);
        check("Valid rental rate update",
                property.getRentalRate() == 5500.75);

        // Invalid rental rate updates must preserve the old rate
        double oldRate = property.getRentalRate();

        expectInvalid("Update rejects zero rental rate",
                () -> property.updateRentalRate(0));
        check("Zero-rate update preserves old rate",
                property.getRentalRate() == oldRate);

        expectInvalid("Update rejects negative rental rate",
                () -> property.updateRentalRate(-100));
        check("Negative-rate update preserves old rate",
                property.getRentalRate() == oldRate);

        expectInvalid("Update rejects NaN rental rate",
                () -> property.updateRentalRate(Double.NaN));
        check("NaN update preserves old rate",
                property.getRentalRate() == oldRate);

        expectInvalid("Update rejects positive infinity rental rate",
                () -> property.updateRentalRate(Double.POSITIVE_INFINITY));
        check("Positive-infinity update preserves old rate",
                property.getRentalRate() == oldRate);

        expectInvalid("Update rejects negative infinity rental rate",
                () -> property.updateRentalRate(Double.NEGATIVE_INFINITY));
        check("Negative-infinity update preserves old rate",
                property.getRentalRate() == oldRate);

        // Description update validation
        property.updateDescription("Apartment B202");
        check("Valid description update",
                property.getDescription().equals("Apartment B202"));

        String oldDescription = property.getDescription();

        expectInvalid("Description update rejects null",
                () -> property.updateDescription(null));
        check("Null description update preserves old description",
                property.getDescription().equals(oldDescription));

        expectInvalid("Description update rejects blank text",
                () -> property.updateDescription("   "));
        check("Blank description update preserves old description",
                property.getDescription().equals(oldDescription));

        // Summary
        System.out.println("\n===== TEST RESULTS =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}