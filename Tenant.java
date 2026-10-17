import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Tenant extends User {
    protected List<RentalContract> rentalContracts;

    public Tenant(int userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email);
        rentalContracts = new ArrayList<>();
    }

    public RentalContract rentProperty(int contractID, Property property, Date startDate, Date endDate) {
        
        RentalContract contract = new RentalContract(contractID, property, startDate, endDate);
        if (!rentalContracts.contains(contract))
            rentalContracts.add(contract);
        return contract;
    }

    public List getRentalContracts() {
        return rentalContracts;
    }
}
