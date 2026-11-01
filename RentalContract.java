import java.time.LocalDate;

public class RentalContract {
    protected int contractID;
    protected Property property;
    protected Tenant tenant;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double rentAmount;
    protected boolean isActive;

    public boolean getActive() {
        return isActive;
    }

    public RentalContract(int contractID, Property property, Tenant tenant,
            LocalDate startDate, LocalDate endDate) {

        if (contractID <= 0) {
            throw new IllegalArgumentException("ContractID must be positive");
        }
        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null");
        }

        if (tenant == null) {
            throw new IllegalArgumentException("Tenant cannot be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }

        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException(
                    "End date must be after start date");
        }
        if (!property.getAvailable()) {
            throw new IllegalArgumentException(
                    "Property is not available for rent");
        }
        this.contractID = contractID;
        this.property = property;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentAmount = property.getRentalRate();
        this.isActive = true;

        // 6. Update Property availability
        property.setAvailable(false);
    }

    public void terminate() {
        if (isActive) {
            isActive = false;
            property.setAvailable(true);
        }
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
