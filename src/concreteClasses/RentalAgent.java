package concreteClasses;

import abstractClasses.Employee;
import abstractClasses.Vehicle;
import enums.UserRole;
import static enums.VehicleStatus.AVAILABLE;
import static enums.VehicleStatus.RENTED;
import exceptions.InvalidReservationException;
import exceptions.VehicleNotAvailable;
import java.time.LocalDate;

public class RentalAgent extends Employee {

    public RentalAgent(String branchLocation, double salary, String name, String contactNo, String userID, UserRole role) {
        super(branchLocation, salary, name, contactNo, userID, UserRole.AGENT);
    }

    public Reservation createReservation(Customer c, Vehicle v, LocalDate start, LocalDate end) throws VehicleNotAvailable, InvalidReservationException {
        if (end.isBefore(start)) {
            throw new InvalidReservationException("End date must be after the start date!");
        }
        if (!v.getStatus().equals(AVAILABLE)) {
            throw new VehicleNotAvailable(v.getLicensePlate());
        }
        String newID = "R-" + System.currentTimeMillis();

        Reservation newRes = new Reservation(newID, c, v, start, end, "CONFIRMED");
        
        v.setStatus(RENTED);
        System.out.println("Vehicle " +v.getLicensePlate() + " is now rented to " + c.getName());
        
        return newRes;
    }

    public void processReturn(Vehicle v, boolean isDamaged) {
        System.out.println("Agent " + this.getName() + "is receiving vehicle : " + v.getLicensePlate());

        if (isDamaged) {
            System.out.println("Damage occured! Scheduling maintenance!");
            v.scheduleMaintenance();
        }
        v.setStatus(AVAILABLE);
        System.out.println("Vehicle " + v.getLicensePlate() + " is now available.");
    }

    public void generateEstimateCost(Vehicle v, int days) {
        double total = v.getDailyRate() * days;
        System.out.println("Estimated cost for vehicle is " + total + "TL");
    }

}
