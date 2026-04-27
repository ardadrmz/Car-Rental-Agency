/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concreteClasses;

import abstractClasses.Vehicle;
import enums.VehicleStatus;

public class Luxury extends Vehicle {

    public Luxury(String licensePlate, String brandName, double dailyRate, int mileage, VehicleStatus status) {
        super(licensePlate, brandName, dailyRate, mileage, status);
    }
   
}
