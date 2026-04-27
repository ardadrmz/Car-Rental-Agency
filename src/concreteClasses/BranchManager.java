
package concreteClasses;

import abstractClasses.Employee;
import enums.UserRole;

public class BranchManager extends Employee {

    public BranchManager(String branchLocation, double salary, String name, String contactNo, String userID) {
        super(branchLocation, salary, name, contactNo, userID, UserRole.MANAGER);
    }
   
}
