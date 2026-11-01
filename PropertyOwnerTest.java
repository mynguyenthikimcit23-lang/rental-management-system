public class PropertyOwnerTest {
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
        PropertyOwner owner = new PropertyOwner(
                10, "Anna", "Nguyen", "anna@example.com");

        check("Valid owner starts with empty property list",
                owner.getProperties().isEmpty());

        // PropertyOwner must inherit User validation
        expectInvalid("Owner rejects non-positive user ID",
                () -> new PropertyOwner(
                        0, "Anna", "Nguyen", "anna@example.com"));

        expectInvalid("Owner rejects blank first name",
                () -> new PropertyOwner(
                        11, "   ", "Nguyen", "anna@example.com"));

        expectInvalid("Owner rejects malformed email",
                () -> new PropertyOwner(
                        12, "Anna", "Nguyen", "anna@@example.com"));

        // Successful creation adds and returns the Property object
        Property created = owner.createProperty(
                101, "Apartment A101", 5000.50);

        check("Valid property is returned",
                created != null);

        check("Created property is added to owner's list",
                owner.getProperties().size() == 1
                        && owner.getProperties().get(0) == created);

        // Failed creation must return null and leave the list unchanged
        int oldSize = owner.getProperties().size();

        Property invalidId = owner.createProperty(
                0, "Apartment", 5000);
        check("Invalid property ID returns null",
                invalidId == null);
        check("Invalid property ID does not change owner's list",
                owner.getProperties().size() == oldSize);

        Property invalidDescription = owner.createProperty(
                102, "   ", 5000);
        check("Blank description returns null",
                invalidDescription == null);
        check("Blank description does not change owner's list",
                owner.getProperties().size() == oldSize);

        Property invalidRate = owner.createProperty(
                103, "House", 0);
        check("Zero rental rate returns null",
                invalidRate == null);
        check("Invalid rental rate does not change owner's list",
                owner.getProperties().size() == oldSize);

        Property nonFiniteRate = owner.createProperty(
                104, "House", Double.NaN);
        check("Non-finite rental rate returns null",
                nonFiniteRate == null);
        check("Non-finite rental rate does not change owner's list",
                owner.getProperties().size() == oldSize);

        System.out.println("\n===== TEST RESULTS =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}