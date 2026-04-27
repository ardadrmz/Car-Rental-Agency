
package concreteClasses;

import abstractClasses.User;
import enums.UserRole;


public class Customer extends User {

    public Customer(String name, String contactNo, String userID, UserRole role) {
        super(name, contactNo, userID, role);
    }
    
}
