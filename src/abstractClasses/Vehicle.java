package abstractClasses;

import enums.VehicleStatus;
import static enums.VehicleStatus.AVAILABLE;
import static enums.VehicleStatus.IN_MAINTENANCE;
import interfaces.Maintainable;

public abstract class Vehicle implements Maintainable {

    private String licensePlate;
    private String brandName;
    private double dailyRate;
    private int mileage;
    private VehicleStatus status;
    private String branchLocation;

    public Vehicle(String licensePlate, String brandName, double dailyRate, int mileage, VehicleStatus status, String branchLocation) {
        this.licensePlate = licensePlate;
        this.brandName = brandName;
        this.dailyRate = dailyRate;
        this.mileage = mileage;
        this.status = status;
        this.branchLocation = branchLocation;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    @Override
    public void scheduleMaintenance() {
        setStatus(IN_MAINTENANCE);
        System.out.println("Maintenance scheduled : " + getBrandName() + " (" + getLicensePlate() + ")");
    }

    @Override
    public void completeMaintenance() {
        setStatus(AVAILABLE);
        System.out.println("Maintenance completed : " + getBrandName() + " (" + getLicensePlate() + ")");
    }
    
    public abstract double calculateInsuranceCost();
    public abstract int getDailyMileageLimit();
    public abstract double getMileageOverageRate();

    @Override
    public String toString() {

        return getLicensePlate() + "," + getBrandName() + "," + getMileage() + "," + getStatus() + "," + getBranchLocation();
    }

}
