import java.util.ArrayList;
import java.util.List;

public class PropertyOwner extends User {
    protected List<Property> ownedProperties;

    public PropertyOwner(int userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email);
        this.ownedProperties = new ArrayList<>();
    }
    public Property createProperty(int propertyID, String description, double rentalRate) {
        try {
            Property property = new Property(propertyID, description, rentalRate);
            if (ownedProperties.contains(property)){
                System.err.println("Error creating property: Property already exists in owner's list.");
                return null;
            }
            ownedProperties.add(property);
            return property;
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating property: " + e.getMessage());
            return null;
        }
    }
    public List<Property> getProperties() {
        return ownedProperties;
    }
}