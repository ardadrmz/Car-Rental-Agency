
package concreteClasses;

import abstractClasses.Employee;
import abstractClasses.Vehicle;
import enums.UserRole;


public class Mechanic extends Employee {

    public Mechanic(String branchLocation, double salary, String name, String contactNo, String userID, UserRole role) {
        super(branchLocation, salary, name, contactNo, userID, role.MECHANIC);
    }
    
    public void performRepair(Vehicle v){
        System.out.println("Mechanic " + getName() + " is now working on the vehicle : "+v.getLicensePlate());
        v.scheduleMaintenance();    
    }
    
    public void finishRepair(Vehicle v){
        v.completeMaintenance();
        System.out.println("Mechanic " + getName() + " has finished repairing vehicle : " + v.getLicensePlate());
    }
    
    
}
