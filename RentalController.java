import model.Property;
import model.PropertyOwner;
import model.RentalContract;
import model.Tenant;

public class RentalController {
    // createProperty()
    public Property createProperty(PropertyOwner owner, int id, String description, double rate) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        return owner.createProperty(id, description, rate);
    }

    // rentProperty

    // terminateContract
    
}