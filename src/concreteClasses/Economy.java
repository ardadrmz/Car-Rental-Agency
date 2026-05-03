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
}
