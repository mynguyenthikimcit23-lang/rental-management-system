public class Property {
    protected int propertyId;
    protected String description;
    protected double rentalRate;
    protected boolean isAvailable;

    public Property(int propertyId, String description, double rentalRate) {
        if (propertyId <= 0) {
            throw new IllegalArgumentException("Property ID must be a positive integer.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (!Double.isFinite(rentalRate) || rentalRate <= 0 ){
            throw new IllegalArgumentException("RentalRate must be finite and positive decimal.");
        }
        this.propertyId = propertyId;
        this.description = description;
        this.rentalRate = rentalRate;
        this.isAvailable = true;
    }

    public void updateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    public void updateRentalRate(double rentalRate) {
        if(!Double.isFinite(rentalRate) || rentalRate <= 0 ) {
            throw new IllegalArgumentException("RentalRate must be finite and positive decimal.");
        }
        this.rentalRate = rentalRate;
        
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        if (propertyId <= 0) {
            throw new IllegalArgumentException("Property ID must be a positive integer.");
        }
        this.propertyId = propertyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        updateDescription(description);
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
