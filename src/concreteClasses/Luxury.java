package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class Luxury extends Vehicle {

    public Luxury(String licensePlate, String brandName, int mileage, VehicleStatus status, String branchLocation) {
        super(licensePlate, brandName, 800.0, mileage, status, branchLocation);
    }
    @Override
    public double calculateInsuranceCost() {
        return 8000.0;
    }
    @Override
    public int getDailyMileageLimit() { return 100; }

    @Override
    public double getMileageOverageRate() { return 15.0; }
    
}