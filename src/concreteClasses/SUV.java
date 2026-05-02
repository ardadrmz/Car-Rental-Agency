/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class SUV extends Vehicle {

    public SUV(String licensePlate, String brandName, int mileage, VehicleStatus status) {
        super(licensePlate, brandName, 200.0, mileage, status);
    }

    @Override
    public double calculateInsuranceCost() {
        return 2000.0;
    }

}
