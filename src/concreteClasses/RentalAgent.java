
package concreteClasses;

import abstractClasses.Employee;
import abstractClasses.Vehicle;
import enums.UserRole;
import static enums.VehicleStatus.AVAILABLE;
import static enums.VehicleStatus.RENTED;


public class RentalAgent extends Employee {

    public RentalAgent(String branchLocation, double salary, String name, String contactNo, String userID, UserRole role) {
        super(branchLocation, salary, name, contactNo, userID, role);
    }
    
    public void processPickUp(Vehicle v){
        System.out.println("Agent " + this.getName()+ " is processing pick-up for:" + v.getLicensePlate());
        v.setStatus(RENTED);
    }
    
    public void processReturn(Vehicle v,boolean isDamaged){
        System.out.println("Agent " + this.getName() + "is receiving vehicle : "+ v.getLicensePlate());
        
        if(isDamaged){
            System.out.println("Damage occured! Scheduling maintenance!");
            v.scheduleMaintenance();
        }
        v.setStatus(AVAILABLE);
    }
    
    public void generateEstimateCost(Vehicle v,int days){
        double total = v.getDailyRate() * days;
        System.out.println("Estimated cost for vehicle is "  + total + "TL");
    }
    
}
