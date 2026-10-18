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
        try {
            RentalContract contract = new RentalContract(contractID, property, this, startDate, endDate);
            if (!rentalContracts.contains(contract))
                rentalContracts.add(contract);
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
