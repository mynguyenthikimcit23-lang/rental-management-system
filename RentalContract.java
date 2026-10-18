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

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public RentalContract(int contractID, Property property, Tenant tenant, LocalDate startDate, LocalDate endDate) {
        if (!property.getAvailable())
            throw new IllegalArgumentException("Property is not available for rent");
        if (!endDate.isAfter(startDate))
            throw new IllegalArgumentException("End date must be after start date");
        this.contractID = contractID;
        this.property = property;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentAmount = this.property.getRentalRate();
        this.isActive = true;
        this.property.setAvailable(false);
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
}
