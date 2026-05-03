package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class Van extends Vehicle {

    public Van(String licensePlate, String brandName, int mileage, VehicleStatus status, String branchLocation) {
        super(licensePlate, brandName, 400.0, mileage, status, branchLocation);
    }

    @Override
    public double calculateInsuranceCost() {
        return 4000.0;
    }
}