import java.time.LocalDate;

public class RentalContract {
    protected int contractID;
    protected Property property;
    protected Tenant tenant;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected double rentAmount;
    protected boolean isActive;

    public RentalContract(int contractID, Property property, Tenant tenant, LocalDate startDate, LocalDate endDate) {
        if (!property.getAvailable())
            throw new IllegalArgumentException("Bất động sản bất động");
        if (!endDate.isAfter(startDate))
            throw new IllegalArgumentException("ngày end phải ở sau ngày start");
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
        if (this.isActive) {
            this.isActive = false;
            this.property.setAvailable(true);
        }
    }
}
