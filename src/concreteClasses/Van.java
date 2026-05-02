/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class Van extends Vehicle {

    public Van(String licensePlate, String brandName, int mileage, VehicleStatus status) {
        super(licensePlate, brandName, 400.0, mileage, status);
    }

    @Override
    public double calculateInsuranceCost() {
        return 4000.0;
    }

}
