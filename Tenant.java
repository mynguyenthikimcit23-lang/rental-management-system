import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tenant extends User {
    protected List<RentalContract> rentalContracts;

    public Tenant(int userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email);
        rentalContracts = new ArrayList<>();
    }

    public RentalContract rentProperty(int contractID, Property property, LocalDate startDate, LocalDate endDate) {
        // check invalid rental date
        if (startDate == null || endDate == null) {
            System.err.println("Error creating rental contract: Start date and end date cannot be null.");
            return null;
        }
        // Property unavailable: request rejected.
        if (property == null || !property.isAvailable) {
            System.err.println("Error creating rental contract: Property is null or already unavailable.");
            return null;
        }
        try {
            RentalContract contract = new RentalContract(contractID, property, this, startDate, endDate);
            if (rentalContracts.contains(contract)) {
                System.err.println("Error creating rental contract: Contract already exists in tenant's list.");
            }
            rentalContracts.add(contract);
            // Update Property availability
            property.setAvailable(false);
            return contract;
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating rental contract: " + e.getMessage());
            return null;
        }
    }

    public List<RentalContract> getRentalContracts() {
        return rentalContracts;
    }
}
