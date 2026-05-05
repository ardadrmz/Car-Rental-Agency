package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class Economy extends Vehicle {

    public Economy(String licensePlate, String brandName, int mileage, VehicleStatus status, String branchLocation) {
        super(licensePlate, brandName, 100.0, mileage, status, branchLocation);
    }
    @Override
    public double calculateInsuranceCost() {
        return 1000.0;
    }
    @Override
    public int getDailyMileageLimit() { return 300; }

    @Override
    public double getMileageOverageRate() { return 5.0; } // TL per extra mile

}
