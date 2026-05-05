package concreteClasses;

import abstractClasses.Employee;
import enums.UserRole;

public class BranchManager extends Employee {

    public BranchManager(String branchLocation, double salary, String name, String userID) {
        super(branchLocation, salary, name, userID, UserRole.MANAGER);
    }

    public void raise(Employee e, double raiseAmount) {
        if (raiseAmount <= 0) {
            System.err.println("Please enter a valid salary!");
            return;
        }
        e.setSalary(e.getSalary() + raiseAmount);
        System.out.println("Employee" + e.getUserID() + " is promoted by " + raiseAmount + "TL");
    }
}
