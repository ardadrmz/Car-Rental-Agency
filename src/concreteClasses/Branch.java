package concreteClasses;

import java.util.ArrayList;
import java.util.List;
import abstractClasses.Employee;
import abstractClasses.Vehicle;

public class Branch {
    private String cityName;
    private List<Employee> branchEmployees;
    private List<Vehicle> branchFleet;
    private double monthlyRevenue; 

    public Branch(String cityName) {
        this.cityName = cityName;
        this.branchEmployees = new ArrayList<>();
        this.branchFleet = new ArrayList<>();
        this.monthlyRevenue = 0.0;
    }

    public String getCityName() { return cityName; }
    public List<Employee> getBranchEmployees() { return branchEmployees; }
    public List<Vehicle> getBranchFleet() { return branchFleet; }
    public double getMonthlyRevenue() { return monthlyRevenue; }

    public void addEmployee(Employee e) { branchEmployees.add(e); }
    public void addVehicle(Vehicle v) { branchFleet.add(v); }
    public void addRevenue(double amount) { this.monthlyRevenue += amount; }

    public double calculateMonthlyOngoings() {
        double totalSalaries = 0.0;
        for (Employee e : branchEmployees) {
            totalSalaries += e.getSalary();
        }
        return this.monthlyRevenue - totalSalaries;
    }
}