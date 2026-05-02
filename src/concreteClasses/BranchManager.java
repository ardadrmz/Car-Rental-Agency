package concreteClasses;

import abstractClasses.Employee;
import enums.UserRole;

public class BranchManager extends Employee {

    public BranchManager(String branchLocation, double salary, String name, String contactNo, String userID) {
        super(branchLocation, salary, name, contactNo, userID, UserRole.MANAGER);
    }

    public void raise(Employee e, double raiseAmount) {
        if (raiseAmount <= 0) {
            System.err.println("Please enter a valid salary!");
            return;
        }
        e.setSalary(e.getSalary() + raiseAmount);
        System.out.println("Employee" + e.getUserID() + " is promoted by " + raiseAmount + "TL");
    }
    
    public double calculateOngoings(java.util.List<Employee> employeeList){
        double totalSalaries = 0;
        for(Employee e : employeeList){
            if(e.getBranchLocation().equals(this.getBranchLocation())){
                totalSalaries += e.getSalary();
            }
        }
        System.out.println("Total monthly ongoing : " + totalSalaries+ "TL");
        return totalSalaries;    
    }

}
