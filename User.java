public class User {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String email;

    public User(int userID, String firstName, String lastName, String email) {
        if (userID <= 0) {
            throw new IllegalArgumentException("User ID is invalid. User ID must be a positive number greater than 0");
        }
        // Validate all values before into constructor
        String validFirstName = validateName(firstName);
        String validLastName = validateName(lastName);
        String validEmail = validEmail(email);

        this.userID = userID;
        this.firstName = validFirstName;
        this.lastName = validLastName;
        this.email = validEmail;
    }

    // UpdateProfile: Validates data before executing the profile update
    public void updateProfile(int idUpdate, String firstNameUpdate, String lastNameUpdate, String emailUpdate) {
        if (idUpdate <= 0) {
            throw new IllegalArgumentException("User ID is invalid. User ID must be a positive number greater than 0");
        }
        this.userID = idUpdate;
        // Validate all values before into constructor
        String validFirstName = validateName(firstNameUpdate);
        String validLastName = validateName(lastNameUpdate);
        String validEmail = validEmail(emailUpdate);

        this.firstName = validFirstName;
        this.lastName = validLastName;
        this.email = validEmail;
    }

    private static String validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        // trim remove whitespace for name
        name = name.trim();
        // if name dont have whitespace -> check it is empty or not.
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        // check name only valid if it contains only valid letters.
        String nameRegex = "^[\\p{L}\\p{M}]+(?:[ '-][\\p{L}\\p{M}]+)*$";
        // Name allows letters, spaces, hyphens, and apostrophes.
        if (!name.matches(nameRegex)) {
            throw new IllegalArgumentException("Invalid name format");
        }

        return name;
    }

    private static String validEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        email = email.trim();
        // if email dont have whitespace -> check it is empty or not.
        if (email.isEmpty()) {
            throw new IllegalArgumentException("email cannot be empty");
        }
        // check email define it -> a practical Regex string that approximates RFC 5322
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // email allows letters, spaces, hyphens, and apostrophes.
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void updateProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
