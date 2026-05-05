package abstractClasses;
import enums.UserRole;

public abstract class Employee extends User {
    private String branchLocation;
    private double salary;

    public Employee(String branchLocation, double salary, String name, String userID, UserRole role) {
        super(name, userID, role);
        this.branchLocation = branchLocation;
        this.salary = salary;
    }

    public String getBranchLocation() { return branchLocation; }
    public void setBranchLocation(String branchLocation) { this.branchLocation = branchLocation; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    @Override
    public String toString() {
        return super.toString() + "," + getBranchLocation() + "," + getSalary();
    }
}