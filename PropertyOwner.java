import java.util.ArrayList;
import java.util.List;

public class PropertyOwner extends User {
    protected List<Property> ownedProperties;

    public PropertyOwner(int userID, String firstName, String lastName, String email) {
        super(userID, firstName, lastName, email);
        ownedProperties = new ArrayList<>();
    }

    public Property PropertyOwner(int propertyID, String description, double rentalRate) {
        Property property = new Property(propertyID, description, rentalRate);
        if (!ownedProperties.contains(property))
            ownedProperties.add(property);
        return property;
    }

    public List getOwnedProperties() {
        return ownedProperties;
    }
}