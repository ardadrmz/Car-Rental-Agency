package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class SUV extends Vehicle {

    public SUV(String licensePlate, String brandName, int mileage, VehicleStatus status, String branchLocation) {
        super(licensePlate, brandName, 200.0, mileage, status, branchLocation);
    }
    @Override
    public double calculateInsuranceCost() {
        return 2000.0;
    }
    @Override
    public int getDailyMileageLimit() { return 250; }

    @Override
    public double getMileageOverageRate() { return 7.0; }

}
