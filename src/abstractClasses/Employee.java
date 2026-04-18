package abstractClasses;
import enums.UserRole;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ardad
 */
public abstract class Employee extends User{
    private String employeeID;
    private String branchLocation;
    private double salary;
    private UserRole role;
}
