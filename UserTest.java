public class UserTest {
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
        // Constructor: valid input and allowed name characters
        User user = new User(
                2,
                " Mary-Jane ",
                "O'Neil Smith",
                "mary.jane@example.com");

        check("Constructor trims names",
                user.getFullName().equals("Mary-Jane O'Neil Smith"));
        check("Constructor trims email",
                user.email.equals("mary.jane@example.com"));
        check("Constructor stores user ID",
                user.userID == 2);

        // Constructor: invalid user ID
        expectInvalid("User ID cannot be zero",
                () -> new User(0, "Anna", "Nguyen", "anna@example.com"));
        expectInvalid("User ID cannot be negative",
                () -> new User(-1, "Anna", "Nguyen", "anna@example.com"));

        // Constructor: invalid first and last names
        expectInvalid("First name cannot be blank",
                () -> new User(3, "   ", "Nguyen", "anna@example.com"));
        expectInvalid("Last name cannot be blank",
                () -> new User(3, "Anna", "   ", "anna@example.com"));
        expectInvalid("First name cannot be null",
                () -> new User(3, null, "Nguyen", "anna@example.com"));
        expectInvalid("Last name cannot be null",
                () -> new User(3, "Anna", null, "anna@example.com"));
        expectInvalid("Name rejects invalid characters",
                () -> new User(3, "Anna1", "Nguyen", "anna@example.com"));

        // Constructor: invalid email
        expectInvalid("Email rejects malformed format",
                () -> new User(3, "Anna", "Nguyen", "anna@@example.com"));
        expectInvalid("Email cannot be blank",
                () -> new User(3, "Anna", "Nguyen", "   "));
        expectInvalid("Email cannot be null",
                () -> new User(3, "Anna", "Nguyen", null));

        // UpdateProfile: valid input; UserID must remain unchanged
        user.updateProfile("Minh", "Van Tran", "minh@example.com");

        check("Valid profile update changes name and email",
                user.getFullName().equals("Minh Van Tran")
                        && user.email.equals("minh@example.com"));
        check("Profile update keeps user ID unchanged",
                user.userID == 2);

        // Failed updates must leave the existing profile unchanged
        String oldName = user.getFullName();
        String oldEmail = user.email;
        int oldUserID = user.userID;

        expectInvalid("Update rejects blank first name",
                () -> user.updateProfile("   ", "Tran", "new@example.com"));
        check("Blank first name update preserves old profile",
                user.getFullName().equals(oldName)
                        && user.email.equals(oldEmail)
                        && user.userID == oldUserID);

        expectInvalid("Update rejects blank last name",
                () -> user.updateProfile("Minh", "   ", "new@example.com"));
        check("Blank last name update preserves old profile",
                user.getFullName().equals(oldName)
                        && user.email.equals(oldEmail)
                        && user.userID == oldUserID);

        expectInvalid("Update rejects malformed email",
                () -> user.updateProfile("Minh", "Tran", "anna@@example.com"));
        check("Malformed email update preserves old profile",
                user.getFullName().equals(oldName)
                        && user.email.equals(oldEmail)
                        && user.userID == oldUserID);

        System.out.println("\n===== TEST RESULTS =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }
}