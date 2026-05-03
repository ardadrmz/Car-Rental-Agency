package CarRentalAgency;

import concreteClasses.FileManager;
import concreteClasses.*;
import abstractClasses.Vehicle; 
import java.util.List;

public class CarRentalAgency {

    public static final String AGENCY_NAME = "FleetRentPro"; 

    public static List<Customer> customerList;
    public static List<Vehicle> vehicleList;
    public static List<Reservation> reservationList;
    public static List<Invoice> invoiceList;

    public static void main(String[] args) {
        
        customerList = FileManager.loadCustomers();
        vehicleList = FileManager.loadVehicles();
        reservationList = FileManager.loadReservations(customerList, vehicleList);
        invoiceList = FileManager.loadInvoices(reservationList);

        GUIApp window = new GUIApp();
        window.createAndShowGUI();
    }
}
