package concreteClasses;

import abstractClasses.Employee;
import abstractClasses.Vehicle;
import enums.UserRole;

public class Mechanic extends Employee {

    public Mechanic(String branchLocation, double salary, String name, String userID) {
        super(branchLocation, salary, name, userID, UserRole.MECHANIC);
    }

    public void finishRepair(Vehicle v) {
        v.completeMaintenance();
        System.out.println("Mechanic " + getName() + " has finished repairing vehicle : " + v.getLicensePlate());
    }

}
