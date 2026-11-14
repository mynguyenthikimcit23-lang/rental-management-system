package controller;

import java.time.LocalDate;
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
    public RentalContract rentProperty(int contractID, Tenant tenant, Property property, LocalDate startDate, LocalDate endDate) {
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant cannot be null.");
        }
        return tenant.rentProperty(contractID, property, startDate, endDate);
    }

    // terminateContract
    public void terminateContract(RentalContract contract) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract cannot be null.");
        }
        contract.terminate();
    }

}
