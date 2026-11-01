
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // chủ sở hữu bất động sản và người thuê
        PropertyOwner owner = new PropertyOwner(1, "Anna", "Nguyen", "anna@example.com");
        Tenant tenant = new Tenant(2, "Minh", "Tran", "minh@example.com");
        System.out.println(owner.getFullName());

        Property property = owner.createProperty(101, "Apartment A101, two bedrooms", 5000);
        System.out.println(owner.getProperties().size());

        // Hợp đồng thuê nhà 1
        RentalContract contract = tenant.rentProperty(1001, property, LocalDate.of(2026, 11, 1),
                LocalDate.of(2027, 10, 31));
        System.out.println(tenant.getRentalContracts().size());

        System.out.println(property.getAvailable());
        
        System.out.println(contract.getActive());

        // System.out.println(contract.getRentAmount());

        property.updateRentalRate(5500);
        contract.terminate();
        System.out.println(contract.getActive());
        System.out.println(property.getAvailable());

        //VALIDATE propertyOwner
        System.out.println("\nValidate propertyOwner");
        PropertyOwner testOwner = new PropertyOwner(2, "Nga", "Pham", "ngapham@mail.com");
        // T1 (valid): id=101, rate=5000
        // Expect: non-null returned, size = 1
        check("T1 valid", testOwner.createProperty(201, "Apartment", 4300), testOwner);
        // T2 (invalid): rate = 0
        // Expect: null returned, size stays 1 (nothing added)
        check("T2 rate=0", testOwner.createProperty(202, "Studio", 0), testOwner);
        // T3 (invalid): rate < 0
        // Expect: null returned, size stays 1 (nothing added)
        check("T3 rate=-10", testOwner.createProperty(203, "House", -10), testOwner);
    }
    static void check(String test, Property result, PropertyOwner owner) {
        System.out.println(test + ": returnedNull=" + (result == null) + ", size=" + owner.getProperties().size());
    }
}