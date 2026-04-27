
package concreteClasses;

import abstractClasses.Employee;
import enums.UserRole;


public class RentalAgent extends Employee {

    public RentalAgent(String branchLocation, double salary, String name, String contactNo, String userID, UserRole role) {
        super(branchLocation, salary, name, contactNo, userID, role);
    }
    
}
