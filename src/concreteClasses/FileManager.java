/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concreteClasses;

import java.io.*;
import java.util.List;
import abstractClasses.Vehicle;
import enums.LoyaltyTier;

public class FileManager {

    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String VEHICLE_FILE = "vehicles.txt";
    private static final String RESERVATION_FILE = "reservations.txt";
    private static final String INVOICE_FILE = "invoices.txt";

    public static void saveCustomer(Customer c) {
        BufferedWriter bWriter = null;
        try {
            FileWriter fWriter = new FileWriter(CUSTOMER_FILE, true);
            bWriter = new BufferedWriter(fWriter);
            bWriter.write(c.toString());
            bWriter.newLine();
            System.out.println("Customer " + c.getName() + " saved successfully.");

        } catch (IOException e) {
            System.err.println("An error occured during writing to file!" + e.getMessage());
        } finally {
            try {
                if (bWriter != null) {
                    bWriter.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occured while closing the file" + ex.getMessage());
            }
        }
    }

    public static void saveVehicle(Vehicle v) {
        BufferedWriter bWriter = null;
        try {
            FileWriter fWriter = new FileWriter(VEHICLE_FILE, true);
            bWriter = new BufferedWriter(fWriter);

            bWriter.write(v.getClass().getSimpleName() + "," + v.toString());
            bWriter.newLine();
            System.out.println(v.getClass().getSimpleName() + " " + v.getLicensePlate() + " saved successfully! Vroom! 💨");

        } catch (IOException e) {
            System.err.println("An error occurred during writing to file: " + e.getMessage());
        } finally {
            try {
                if (bWriter != null) {
                    bWriter.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
    }

    public static void saveReservation(Reservation res) {
        BufferedWriter bWriter = null;
        try {
            FileWriter fWriter = new FileWriter(RESERVATION_FILE, true);
            bWriter = new BufferedWriter(fWriter);
            bWriter.write(res.toString());
            bWriter.newLine();
            System.out.println("Reservation " + res.getReservationID() + " saved successfully.");

        } catch (IOException e) {
            System.err.println("An error occured during writing to file!" + e.getMessage());
        } finally {
            try {
                if (bWriter != null) {
                    bWriter.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occured while closing the file" + ex.getMessage());
            }
        }
    }

    public static void saveInvoice(Invoice i) {
        BufferedWriter bWriter = null;
        try {
            FileWriter fWriter = new FileWriter(INVOICE_FILE, true);
            bWriter = new BufferedWriter(fWriter);
            bWriter.write(i.toString());
            bWriter.newLine();
            System.out.println("Invoice " + i.getInvoiceID() + " saved successfully.");

        } catch (IOException e) {
            System.err.println("An error occured during writing to file!" + e.getMessage());
        } finally {
            try {
                if (bWriter != null) {
                    bWriter.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occured while closing the file" + ex.getMessage());
            }
        }
    }

    public static java.util.List<Customer> loadCustomers() {

        java.util.List<Customer> customerList = new java.util.ArrayList<>();
        BufferedReader bReader = null;

        try {

            FileReader fReader = new FileReader(CUSTOMER_FILE);
            bReader = new BufferedReader(fReader);

            String line;

            while ((line = bReader.readLine()) != null) {

                String[] data = line.split(",");

                String userID = data[0];
                String name = data[1];
                String contactNo = data[2];
                int loyaltyPts = Integer.parseInt(data[5]);

                Customer loadedCustomer = new Customer(name, contactNo, userID, enums.UserRole.CUSTOMER);
                loadedCustomer.addLoyaltyPts(loyaltyPts);
                customerList.add(loadedCustomer);
            }

            System.out.println("Customers loaded successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("No customer file found.");

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }

        return customerList;
    }

    public static java.util.List<Vehicle> loadVehicles() {

        java.util.List<Vehicle> vehicleList = new java.util.ArrayList<>();
        BufferedReader bReader = null;

        try {

            FileReader fReader = new FileReader(VEHICLE_FILE);
            bReader = new BufferedReader(fReader);

            String line;

            while ((line = bReader.readLine()) != null) {

                String[] data = line.split(",");

                String vehicleType = data[0];
                String licensePlate = data[1];
                String brandName = data[2];
                int mileage = Integer.parseInt(data[3]);
                enums.VehicleStatus status = enums.VehicleStatus.valueOf(data[4]);

                Vehicle loadedVehicle = null;

                switch (vehicleType) {
                    case "Economy":
                        loadedVehicle = new Economy(licensePlate, brandName, mileage, status);
                        break;
                    case "Luxury":
                        loadedVehicle = new Luxury(licensePlate, brandName, mileage, status);
                        break;
                    case "SUV":
                        loadedVehicle = new SUV(licensePlate, brandName, mileage, status);
                        break;
                    case "Van":
                        loadedVehicle = new Van(licensePlate, brandName, mileage, status);
                        break;
                }

                if (loadedVehicle != null) {
                    vehicleList.add(loadedVehicle);
                }
            }

            System.out.println("Vehicles loaded successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("No vehicle file found.");

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }

        return vehicleList;
    }

    public static java.util.List<Reservation> loadReservations(java.util.List<Customer> customers, java.util.List<Vehicle> vehicles) {

        java.util.List<Reservation> reservationList = new java.util.ArrayList<>();
        BufferedReader bReader = null;

        try {

            FileReader fReader = new FileReader(RESERVATION_FILE);
            bReader = new BufferedReader(fReader);

            String line;

            while ((line = bReader.readLine()) != null) {

                String[] data = line.split(",");

                String resID = data[0];
                String customerName = data[1];
                String licensePlate = data[2];
                java.time.LocalDate startDate = java.time.LocalDate.parse(data[3]);
                java.time.LocalDate endDate = java.time.LocalDate.parse(data[4]);
                String status = data[5];

                Customer linkedCustomer = null;
                for (Customer c : customers) {
                    if (c.getName().equals(customerName)) {
                        linkedCustomer = c;
                        break;
                    }
                }

                Vehicle linkedVehicle = null;
                for (Vehicle v : vehicles) {
                    if (v.getLicensePlate().equals(licensePlate)) {
                        linkedVehicle = v;
                        break;
                    }
                }

                if (linkedCustomer != null && linkedVehicle != null) {
                    Reservation res = new Reservation(resID, linkedCustomer, linkedVehicle, startDate, endDate, status);
                    reservationList.add(res);
                }
            }

            System.out.println("Reservations loaded successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("No reservation file found.");

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }

        return reservationList;
    }

    public static java.util.List<Invoice> loadInvoices(java.util.List<Reservation> reservations) {

        java.util.List<Invoice> invoiceList = new java.util.ArrayList<>();
        BufferedReader bReader = null;

        try {

            FileReader fReader = new FileReader(INVOICE_FILE);
            bReader = new BufferedReader(fReader);

            String line;

            while ((line = bReader.readLine()) != null) {

                String[] data = line.split(",");

                String invoiceID = data[0];
                String reservationID = data[1];

                Reservation linkedReservation = null;
                for (Reservation r : reservations) {
                    if (r.getReservationID().equals(reservationID)) {
                        linkedReservation = r;
                        break;
                    }
                }

                if (linkedReservation != null) {
                    Invoice inv = new Invoice(invoiceID, linkedReservation);
                    invoiceList.add(inv);
                }
            }

            System.out.println("Invoices loaded successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("No invoice file found.");

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }

        return invoiceList;
    }

}
