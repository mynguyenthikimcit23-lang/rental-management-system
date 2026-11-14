
// import java.time.LocalDate;

// import model.Property;
// import model.PropertyOwner;
// import model.RentalContract;
// import model.Tenant;

// public class Main {
//     public static void main(String[] args) {
//         // chủ sở hữu bất động sản và người thuê
//         PropertyOwner owner = new PropertyOwner(1, "Anna", "Nguyen", "anna@example.com");
//         Tenant tenant = new Tenant(2, "Minh", "Tran", "minh@example.com");
//         System.out.println(owner.getFullName());

//         Property property = owner.createProperty(101, "Apartment A101, two bedrooms", 5000);
//         System.out.println(owner.getProperties().size());

//         // Hợp đồng thuê nhà 1
//         RentalContract contract = tenant.rentProperty(1001, property, LocalDate.of(2026, 11, 1),
//                 LocalDate.of(2027, 10, 31));
//         System.out.println(tenant.getRentalContracts().size());

//         System.out.println(property.getAvailable());
        
//         System.out.println(contract.getActive());

//         // System.out.println(contract.getRentAmount());

//         property.updateRentalRate(5500);
//         contract.terminate();
//         System.out.println(contract.getActive());
//         System.out.println(property.getAvailable());

//     }
// }

import controller.RentalController;
import model.PropertyOwner;
import model.Tenant;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        PropertyOwner owner =
                new PropertyOwner(1, "Anna", "Nguyen", "anna@example.com");
        Tenant tenant =
                new Tenant(2, "Minh", "Tran", "minh@example.com");

        ConsoleView view =
                new ConsoleView(new RentalController(), owner, tenant);
        view.run();
    }
}