package concreteClasses;

import java.io.*;
import java.util.List;
import abstractClasses.Vehicle;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Sistemdeki tüm bilgileri kaybetmemek için txt dosyalarına yazıp okuyan sınıfımız.
 * Bilgileri yan yana, aralarına virgül koyarak saklıyor (CSV mantığı).
 */
public class FileManager {

    // Bilgileri kaydedeceğimiz dosyaların isimleri
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String VEHICLE_FILE = "vehicles.txt";
    private static final String RESERVATION_FILE = "reservations.txt";
    private static final String INVOICE_FILE = "invoices.txt";

    // --- DOSYANIN SONUNA YENİ BİLGİ EKLEME İŞLEMLERİ ---
    // Bu fonksiyonlar eski yazıları silmez, sadece listenin en altına yeni bir satır ekler.

    public static void saveCustomer(Customer c) {
        BufferedWriter bWriter = null;
        try {
            // 'true' yazmamızın sebebi, mevcut listeyi bozmayıp altına ekleme yapmak istememiz
            FileWriter fWriter = new FileWriter(CUSTOMER_FILE, true);
            bWriter = new BufferedWriter(fWriter);
            bWriter.write(c.toString());
            bWriter.newLine();
            System.out.println("Customer " + c.getName() + " saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred during writing to file: " + e.getMessage());
        } finally {
            try {
                if (bWriter != null) bWriter.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
    }

    public static void saveVehicle(Vehicle v) {
        BufferedWriter bWriter = null;
        try {
            FileWriter fWriter = new FileWriter(VEHICLE_FILE, true);
            bWriter = new BufferedWriter(fWriter);
            // Daha sonra dosyadan okurken bu aracın "Luxury" mi yoksa "SUV" mu olduğunu anlayalım diye sınıf adını başa yazıyoruz
            bWriter.write(v.getClass().getSimpleName() + "," + v.toString());
            bWriter.newLine();
            System.out.println(v.getClass().getSimpleName() + " " + v.getLicensePlate() + " saved successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred during writing to file: " + e.getMessage());
        } finally {
            try {
                if (bWriter != null) bWriter.close();
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
            System.err.println("An error occurred during writing to file: " + e.getMessage());
        } finally {
            try {
                if (bWriter != null) bWriter.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
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
            System.err.println("An error occurred during writing to file: " + e.getMessage());
        } finally {
            try {
                if (bWriter != null) bWriter.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
    }

    // --- DOSYADAN BİLGİLERİ OKUYUP SİSTEME YÜKLEME İŞLEMLERİ ---
    // Uygulama ilk açıldığında txt dosyalarındaki yazıları okuyup gerçek Java nesnelerine dönüştürür.

    public static List<Customer> loadCustomers() {
        List<Customer> customerList = new ArrayList<>();
        BufferedReader bReader = null;
        try {
            FileReader fReader = new FileReader(CUSTOMER_FILE);
            bReader = new BufferedReader(fReader);
            String line;

            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue; // Eğer dosyada eksik/bozuk bir satır varsa onu es geçiyoruz

                String userID = data[0];
                String name = data[1];
                String contactNo = data[2];
                int loyaltyPts = Integer.parseInt(data[5]);

                Customer loadedCustomer = new Customer(name, contactNo, userID);
                loadedCustomer.addLoyaltyPts(loyaltyPts); // Müşterinin önceden biriktirdiği sadakat puanlarını geri veriyoruz
                customerList.add(loadedCustomer);
            }
            System.out.println("Customers loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No customer file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (bReader != null) bReader.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
        return customerList;
    }

    public static List<Vehicle> loadVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
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
                int mileage = (int) Double.parseDouble(data[3]);
                enums.VehicleStatus status = enums.VehicleStatus.valueOf(data[4].toUpperCase());

                String branchLocation = "İstanbul";
                if (data.length > 5) branchLocation = data[5];

                Vehicle loadedVehicle = null;
                // Dosyada yazan araç tipine göre doğru sınıftan nesne üretiyoruz (Economy, Van vb.)
                switch (vehicleType) {
                    case "Economy":
                        loadedVehicle = new Economy(licensePlate, brandName, mileage, status, branchLocation);
                        break;
                    case "Luxury":
                        loadedVehicle = new Luxury(licensePlate, brandName, mileage, status, branchLocation);
                        break;
                    case "SUV":
                        loadedVehicle = new SUV(licensePlate, brandName, mileage, status, branchLocation);
                        break;
                    case "Van":
                        loadedVehicle = new Van(licensePlate, brandName, mileage, status, branchLocation);
                        break;
                }
                if (loadedVehicle != null) vehicleList.add(loadedVehicle);
            }
            System.out.println("Vehicles loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No vehicle file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (bReader != null) bReader.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
        return vehicleList;
    }

    /**
     * Rezervasyonları dosyadan okur ve içindeki isim/plaka bilgisine bakarak
     * sistemdeki gerçek Müşteri ve Araç nesneleriyle eşleştirir.
     */
    public static List<Reservation> loadReservations(List<Customer> customers, List<Vehicle> vehicles) {
        List<Reservation> reservationList = new ArrayList<>();
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
                LocalDate startDate = LocalDate.parse(data[3]);
                LocalDate endDate = LocalDate.parse(data[4]);
                String status = data[5];

                // Sistemin içinde bu rezervasyonun sahibini (Müşteriyi) arıyoruz
                Customer linkedCustomer = null;
                for (Customer c : customers) {
                    if (c.getName().equals(customerName)) {
                        linkedCustomer = c;
                        break;
                    }
                }

                // Aynı şekilde sistemde kayıtlı olan aracı buluyoruz
                Vehicle linkedVehicle = null;
                for (Vehicle v : vehicles) {
                    if (v.getLicensePlate().equals(licensePlate)) {
                        linkedVehicle = v;
                        break;
                    }
                }

                // Eğer hem aracı hem müşteriyi bulabildiysek rezervasyonu sorunsuzca listeye ekliyoruz
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
                if (bReader != null) bReader.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
        return reservationList;
    }

    /**
     * Faturaları okur ve faturanın kesildiği o eski rezervasyonu bulup bağlar.
     */
    public static List<Invoice> loadInvoices(List<Reservation> reservations) {
        List<Invoice> invoiceList = new ArrayList<>();
        BufferedReader bReader = null;
        try {
            FileReader fReader = new FileReader(INVOICE_FILE);
            bReader = new BufferedReader(fReader);
            String line;

            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue;

                String invoiceID = data[0];
                String reservationID = data[1];

                // Faturanın ait olduğu rezervasyonu arıyoruz
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
                if (bReader != null) bReader.close();
            } catch (IOException ex) {
                System.err.println("An error occurred while closing the file: " + ex.getMessage());
            }
        }
        return invoiceList;
    }

    // --- DOSYALARI SİLİP BAŞTAN YAZMA İŞLEMLERİ ---
    // Mevcut bir şey değiştiğinde (mesela müşteri puan kazandığında veya araba kiralandığında) 
    // yeni bilgiyi dosyanın en altına eklemek yerine, listeyi baştan aşağı tertemiz güncel haliyle yeniden yazar.

    public static void updateCustomerFile(List<Customer> updatedList) {
        // 'false' yazmamızın sebebi, eski dosyanın içini tamamen temizleyip yeni listemizi yazdırmak istememiz
        try (FileWriter writer = new FileWriter(CUSTOMER_FILE, false); BufferedWriter bWriter = new BufferedWriter(writer)) {
            for (Customer c : updatedList) {
                bWriter.write(c.toString());
                bWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating customer file: " + e.getMessage());
        }
    }

    public static void updateVehicleFile(List<Vehicle> updatedList) {
        try (FileWriter writer = new FileWriter(VEHICLE_FILE, false); BufferedWriter bWriter = new BufferedWriter(writer)) {
            for (Vehicle v : updatedList) {
                bWriter.write(v.getClass().getSimpleName() + "," + v.toString());
                bWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating vehicle file: " + e.getMessage());
        }
    }

    public static void updateReservationFile(List<Reservation> updatedList) {
        try (FileWriter writer = new FileWriter(RESERVATION_FILE, false); BufferedWriter bWriter = new BufferedWriter(writer)) {
            for (Reservation res : updatedList) {
                bWriter.write(res.toString());
                bWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating reservation file: " + e.getMessage());
        }
    }
}