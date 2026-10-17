public class Property {
    protected int propertyId ;
    protected String description;
    protected double rentalRate ;
    public boolean isAvailable;
    
    public Property (int propertyId, String description, double rentalRate){
        if (rentalRate <= 0) {
            throw new IllegalArgumentException("Rental rate must be greater than 0");
        }
        this.propertyId = propertyId;
        this.description = description;
        this.rentalRate = rentalRate;
        this.isAvailable = true;
    }

    public void updateDescription(String description){
        this.description =description;
    }

    public void updateRentalRate(double rentalRate) {
        if (rentalRate <= 0){
            throw new IllegalArgumentException("Rental rate must be greater than 0");
        }else{
            this.rentalRate = rentalRate;
        }
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
