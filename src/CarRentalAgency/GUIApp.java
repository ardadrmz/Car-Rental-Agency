package CarRentalAgency;

import javax.swing.*;
import java.awt.*;
import concreteClasses.Customer;
import concreteClasses.FileManager;
import abstractClasses.Vehicle;
import abstractClasses.Employee;
import concreteClasses.RentalAgent;
import concreteClasses.Mechanic;
import concreteClasses.BranchManager;
import concreteClasses.Branch;
import exceptions.InvalidReservationException;
import exceptions.VehicleNotAvailable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * FleetRentPro programının ana ekranı. 
 * Kullanıcının tıkladığı her şey, ekranlar ve hesaplamalar burada dönüyor.
 */
public class GUIApp {

    // Ekranda işlem yapacak olan aktif personelimiz (şubeye göre değişecekler)
    private RentalAgent activeAgent;
    private Mechanic activeMechanic;
    private BranchManager activeManager;

    // Şubelerimizi tuttuğumuz liste ve şu an işlem yaptığımız aktif şube
    private List<Branch> agencyBranches = new ArrayList<>();
    private Branch activeBranch;

    /**
     * Uygulama ilk açılırken şubeleri ve çalışanları hazırladığımız kısım.
     */
    public GUIApp() {
        Branch istanbul = new Branch("Istanbul");
        Branch izmir = new Branch("Izmir");
        Branch ankara = new Branch("Ankara");

        // Her şubenin kendi elemanlarını işe alıyoruz.
        istanbul.addEmployee(new RentalAgent("Istanbul", 35000, "Ahmet (Agent)", "A01"));
        istanbul.addEmployee(new Mechanic("Istanbul", 40000, "Mehmet (Mechanic)", "M01"));
        istanbul.addEmployee(new BranchManager("Istanbul", 60000, "Ayse (Manager)", "B01"));

        izmir.addEmployee(new RentalAgent("Izmir", 35000, "Ali (Agent)", "A02"));
        izmir.addEmployee(new Mechanic("Izmir", 40000, "Veli (Mechanic)", "M02"));
        izmir.addEmployee(new BranchManager("Izmir", 60000, "Fatma (Manager)", "B02"));

        ankara.addEmployee(new RentalAgent("Ankara", 35000, "Can (Agent)", "A03"));
        ankara.addEmployee(new Mechanic("Ankara", 40000, "Cem (Mechanic)", "M03"));
        ankara.addEmployee(new BranchManager("Ankara", 60000, "Ceren (Manager)", "B03"));

        agencyBranches.add(istanbul);
        agencyBranches.add(izmir);
        agencyBranches.add(ankara);

        // Uygulama ilk açıldığında Istanbul şubesinde başlıyoruz
        activeBranch = istanbul;
        updateActiveEmployees(); // Aktif çalışanları Istanbul'a göre ayarlıyoruz
    }

    /**
     * Menüden başka bir şube seçtiğimizde o şubenin elemanlarını sisteme yükler.
     */
    private void updateActiveEmployees() {
        for (Employee e : activeBranch.getBranchEmployees()) {
            if (e instanceof RentalAgent) activeAgent = (RentalAgent) e;
            else if (e instanceof Mechanic) activeMechanic = (Mechanic) e;
            else if (e instanceof BranchManager) activeManager = (BranchManager) e;
        }
    }

    /**
     * Ekrandaki butonları, kutucukları ve panelleri oluşturup gösterdiğimiz yer.
     */
    public void createAndShowGUI() {
        JFrame frame = new JFrame(CarRentalAgency.AGENCY_NAME + " - Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 750);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- ŞUBE SEÇİCİ ÜST MENÜ ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        JLabel headerLabel = new JLabel(CarRentalAgency.AGENCY_NAME);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(new Color(44, 62, 80));
        
        JPanel selectorPanel = new JPanel();
        selectorPanel.add(new JLabel("Active Branch: "));
        String[] branchNames = {"Istanbul", "Izmir", "Ankara"};
        JComboBox<String> branchSelector = new JComboBox<>(branchNames);
        
        // Açılır menüden yeni bir şube seçildiğinde olacaklar
        branchSelector.addActionListener(e -> {
            String selected = (String) branchSelector.getSelectedItem();
            for (Branch b : agencyBranches) {
                if (b.getCityName().equals(selected)) {
                    activeBranch = b;
                    updateActiveEmployees(); // Personelleri değiştir
                    JOptionPane.showMessageDialog(frame, "Switched to " + selected + " branch! New manager: " + activeManager.getName(), "Branch Changed", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        });
        selectorPanel.add(branchSelector);

        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(selectorPanel, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Ekranı daha düzenli göstermek için butonları kategori kategori kutulara (panellere) ayırıyoruz
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel pnlVehicles = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlVehicles.setBorder(BorderFactory.createTitledBorder("Vehicles"));

        JPanel pnlCustomer = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlCustomer.setBorder(BorderFactory.createTitledBorder("Customer Management"));

        JPanel pnlAgent = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlAgent.setBorder(BorderFactory.createTitledBorder("Agent Operations"));

        JPanel pnlMechanic = new JPanel(new GridLayout(1, 1, 10, 10));
        pnlMechanic.setBorder(BorderFactory.createTitledBorder("Mechanic Operations"));

        JPanel pnlManager = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlManager.setBorder(BorderFactory.createTitledBorder("Manager Operations"));

        JPanel pnlExit = new JPanel(new GridLayout(1, 1, 10, 10));
        pnlExit.setBorder(BorderFactory.createTitledBorder("EXIT"));

        // Ekranda göreceğimiz butonlarımızı yaratıyoruz
        JButton btnVehicles = new JButton("View All Vehicles");
        JButton btnAddVehicle = new JButton("Register New Vehicle");
        JButton btnCustomer = new JButton("Register New Customer");
        JButton btnListCustomer = new JButton("View All Customers");
        JButton btnReservation = new JButton("Make a Reservation");
        JButton btnReturn = new JButton("Return Vehicle");
        JButton btnInvoice = new JButton("Generate Invoice");
        JButton btnListReservations = new JButton("View All Reservations");
        JButton btnRepair = new JButton("Finish Repair");
        JButton btnManager = new JButton("Give Raise");
        JButton btnViewInvoices = new JButton("View All Invoices");
        JButton btnOngoings = new JButton("Branch Dashboard");
        JButton btnExit = new JButton("Exit System");

        // Hangi butona tıklanırsa hangi pencerenin açılacağını söylüyoruz
        btnVehicles.addActionListener(e -> showVehicleWindow());
        btnListCustomer.addActionListener(e -> showCustomerWindow());
        btnCustomer.addActionListener(e -> showRegisterCustomerWindow());
        btnAddVehicle.addActionListener(e -> showRegisterVehicleWindow());
        btnReservation.addActionListener(e -> showReservationWindow());
        btnReturn.addActionListener(e -> showReturnWindow());
        btnInvoice.addActionListener(e -> showInvoiceWindow());
        btnRepair.addActionListener(e -> showRepairWindow());
        btnManager.addActionListener(e -> showManagerWindow());
        btnViewInvoices.addActionListener(e -> showInvoiceListWindow());
        btnOngoings.addActionListener(e -> showOngoingsWindow());
        btnListReservations.addActionListener(e -> showReservationListWindow());

        btnExit.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to exit " + CarRentalAgency.AGENCY_NAME + "?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Butonları kendi kutularının içine ekliyoruz
        pnlVehicles.add(btnVehicles);
        pnlVehicles.add(btnAddVehicle);
        pnlCustomer.add(btnCustomer);
        pnlCustomer.add(btnListCustomer);
        pnlAgent.add(btnReservation);
        pnlAgent.add(btnReturn);
        pnlAgent.add(btnInvoice);
        pnlAgent.add(btnListReservations);
        pnlMechanic.add(btnRepair);
        pnlManager.add(btnManager);
        pnlManager.add(btnViewInvoices);
        pnlManager.add(btnOngoings);
        pnlManager.add(new JLabel("")); 
        pnlExit.add(btnExit);

        // En son bütün kutuları alt alta ana ekrana ekliyoruz
        centerPanel.add(pnlVehicles);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pnlCustomer);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pnlAgent);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pnlMechanic);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pnlManager);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pnlExit);
        centerPanel.add(Box.createVerticalGlue());

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // --- SADECE BİLGİ GÖSTEREN (LİSTELEME) EKRANLARI ---

    private void showCustomerWindow() {
        JFrame customerFrame = new JFrame(CarRentalAgency.AGENCY_NAME + " - Customer List");
        customerFrame.setSize(700, 400);

        String[] cols = {"Name", "Contact No", "User ID", "Loyalty Tier", "Loyalty Points"};
        Object[][] data = new Object[CarRentalAgency.customerList.size()][5];

        // Sistemdeki tüm müşterileri tek tek dolaşıp tabloya dolduruyoruz
        for (int i = 0; i < CarRentalAgency.customerList.size(); i++) {
            Customer c = CarRentalAgency.customerList.get(i);
            data[i][0] = c.getName();
            data[i][1] = c.getContactNo();
            data[i][2] = c.getUserID();
            data[i][3] = c.getLoyaltyTier();
            data[i][4] = c.getLoyaltyPts();
        }

        JTable table = new JTable(data, cols);
        customerFrame.add(new JScrollPane(table));
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setVisible(true);
    }

    private void showVehicleWindow() {
        JFrame vehicleFrame = new JFrame(CarRentalAgency.AGENCY_NAME + " - Vehicle Fleet");
        vehicleFrame.setSize(700, 400);

        String[] columns = {"Type", "Plate", "Brand", "Mileage", "Status", "Branch"};
        Object[][] data = new Object[CarRentalAgency.vehicleList.size()][6];

        // Sistemdeki tüm araçları tabloya ekliyoruz
        for (int i = 0; i < CarRentalAgency.vehicleList.size(); i++) {
            Vehicle v = CarRentalAgency.vehicleList.get(i);
            data[i][0] = v.getClass().getSimpleName();
            data[i][1] = v.getLicensePlate();
            data[i][2] = v.getBrandName();
            data[i][3] = v.getMileage();
            data[i][4] = v.getStatus();
            data[i][5] = v.getBranchLocation();
        }

        JTable table = new JTable(data, columns);
        vehicleFrame.add(new JScrollPane(table));
        vehicleFrame.setLocationRelativeTo(null);
        vehicleFrame.setVisible(true);
    }

    // --- SİSTEME YENİ BİR ŞEY EKLEME EKRANLARI ---

    private void showRegisterCustomerWindow() {
        JFrame regFrame = new JFrame("Register Customer");
        regFrame.setSize(400, 300);
        regFrame.setLayout(new GridLayout(5, 2, 10, 10));

        // Müşteri ID'sini elle yazdırmıyoruz, sistemdeki kişi sayısına göre otomatik oluşturuyoruz
        regFrame.add(new JLabel("  User ID (Auto):"));
        JTextField txtID = new JTextField();
        txtID.setEditable(false);
        txtID.setBackground(new Color(240, 240, 240));
        int nextIdNum = CarRentalAgency.customerList.size() + 1;
        txtID.setText("CUS-" + String.format("%04d", nextIdNum));
        regFrame.add(txtID);

        regFrame.add(new JLabel("  Name:"));
        JTextField txtName = new JTextField();
        regFrame.add(txtName);

        regFrame.add(new JLabel("  Contact No:"));
        JTextField txtContact = new JTextField();
        regFrame.add(txtContact);

        JButton btnSave = new JButton("Save Customer");
        regFrame.add(new JLabel(""));
        regFrame.add(btnSave);

        btnSave.addActionListener(e -> {
            Customer newCust = new Customer(txtName.getText(), txtContact.getText(), txtID.getText());
            CarRentalAgency.customerList.add(newCust);
            FileManager.saveCustomer(newCust); // Bilgiler kaybolmasın diye hemen txt dosyasına da kaydediyoruz
            JOptionPane.showMessageDialog(regFrame, "Customer saved successfully!");
            regFrame.dispose();
        });

        regFrame.setLocationRelativeTo(null);
        regFrame.setVisible(true);
    }

    private void showRegisterVehicleWindow() {
        JFrame vehFrame = new JFrame("Register Vehicle");
        vehFrame.setSize(400, 450);
        vehFrame.setLayout(new GridLayout(8, 2, 10, 10));

        vehFrame.add(new JLabel("  Vehicle Type:"));
        String[] types = {"Economy", "Luxury", "SUV", "Van"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        vehFrame.add(typeBox);

        vehFrame.add(new JLabel("  License Plate:"));
        JTextField txtPlate = new JTextField();
        vehFrame.add(txtPlate);

        vehFrame.add(new JLabel("  Brand Name:"));
        JTextField txtBrand = new JTextField();
        vehFrame.add(txtBrand);

        vehFrame.add(new JLabel("  Initial Mileage:"));
        JTextField txtMileage = new JTextField();
        vehFrame.add(txtMileage);

        vehFrame.add(new JLabel("  Branch Location:"));
        String[] branches = {"Istanbul", "Izmir", "Ankara"};
        JComboBox<String> branchBox = new JComboBox<>(branches);
        vehFrame.add(branchBox);

        JButton btnSave = new JButton("Save Vehicle");
        vehFrame.add(new JLabel(""));
        vehFrame.add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                String type = (String) typeBox.getSelectedItem();
                String plate = txtPlate.getText();
                String brand = txtBrand.getText();
                int mileage = Integer.parseInt(txtMileage.getText());
                String branch = (String) branchBox.getSelectedItem();
                enums.VehicleStatus status = enums.VehicleStatus.AVAILABLE;
                abstractClasses.Vehicle newVeh;

                // Kullanıcı listeden ne seçtiyse, ona uygun araç sınıfından nesne üretiyoruz
                switch (type) {
                    case "Luxury":
                        newVeh = new concreteClasses.Luxury(plate, brand, mileage, status, branch);
                        break;
                    case "SUV":
                        newVeh = new concreteClasses.SUV(plate, brand, mileage, status, branch);
                        break;
                    case "Van":
                        newVeh = new concreteClasses.Van(plate, brand, mileage, status, branch);
                        break;
                    default:
                        newVeh = new concreteClasses.Economy(plate, brand, mileage, status, branch);
                        break;
                }

                CarRentalAgency.vehicleList.add(newVeh);
                FileManager.saveVehicle(newVeh); // Dosyaya kaydet
                JOptionPane.showMessageDialog(vehFrame, "Vehicle registered successfully!");
                vehFrame.dispose();
            } catch (NumberFormatException ex) {
                // Kilometreye harf falan girerlerse program çökmesin diye uyarı veriyoruz
                JOptionPane.showMessageDialog(vehFrame, "Please enter a valid whole number for mileage.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        vehFrame.setLocationRelativeTo(null);
        vehFrame.setVisible(true);
    }

    // --- TEMSİLCİ (GİŞE) İŞLEMLERİ ---

    private void showReservationWindow() {
        JFrame resFrame = new JFrame("Agent - New Reservation");
        resFrame.setSize(500, 350);
        resFrame.setLayout(new GridLayout(6, 2, 10, 10));

        resFrame.add(new JLabel("  Select Customer:"));
        JComboBox<String> customerDropdown = new JComboBox<>();
        for (Customer c : CarRentalAgency.customerList) {
            customerDropdown.addItem(c.getName() + " - " + c.getUserID());
        }
        resFrame.add(customerDropdown);

        resFrame.add(new JLabel("Select Vehicle:"));
        JComboBox<String> vehicleDropdown = new JComboBox<>();
        for (Vehicle v : CarRentalAgency.vehicleList) {
            vehicleDropdown.addItem(v.getBrandName() + " - " + v.getLicensePlate());
        }
        resFrame.add(vehicleDropdown);

        resFrame.add(new JLabel("  Start Date (YYYY-MM-DD):"));
        JTextField txtStartDate = new JTextField();
        resFrame.add(txtStartDate);

        resFrame.add(new JLabel("End Date (YYYY-MM-DD):"));
        JTextField txtEndDate = new JTextField();
        resFrame.add(txtEndDate);

        JButton btnConfirm = new JButton("Confirm Reservation");
        resFrame.add(new JLabel(""));
        resFrame.add(btnConfirm);

        btnConfirm.addActionListener(e -> {
            int selectedCustomerIndex = customerDropdown.getSelectedIndex();
            int selectedVehicleIndex = vehicleDropdown.getSelectedIndex();

            if (selectedCustomerIndex != -1 && selectedVehicleIndex != -1) {
                try {
                    LocalDate startDate = LocalDate.parse(txtStartDate.getText());
                    LocalDate endDate = LocalDate.parse(txtEndDate.getText());

                    Customer selectedCustomer = CarRentalAgency.customerList.get(selectedCustomerIndex);
                    Vehicle selectedVehicle = CarRentalAgency.vehicleList.get(selectedVehicleIndex);

                    // Aracı kiralama işini direkt o şubedeki aktif gişe görevlisine (Agent) bırakıyoruz. 
                    concreteClasses.Reservation newRes = activeAgent.createReservation(selectedCustomer, selectedVehicle, startDate, endDate);

                    CarRentalAgency.reservationList.add(newRes);
                    // Arabanın durumu "Kiralandı" olarak değiştiği için araç dosyasını güncelliyoruz
                    FileManager.updateVehicleFile(CarRentalAgency.vehicleList);
                    FileManager.saveReservation(newRes);

                    JOptionPane.showMessageDialog(resFrame, "Reservation Confirmed by " + activeAgent.getName() + "!\nYour ID is: " + newRes.getReservationID());
                    resFrame.dispose();

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(resFrame, "Please enter the dates exactly as YYYY-MM-DD!", "Date Format Error", JOptionPane.ERROR_MESSAGE);
                } catch (VehicleNotAvailable | InvalidReservationException ex) {
                    // Tarihler ters girilirse veya araba zaten başkasındaysa burada uyarı veriyoruz
                    JOptionPane.showMessageDialog(resFrame, ex.getMessage(), "Reservation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resFrame.setLocationRelativeTo(null);
        resFrame.setVisible(true);
    }

    private void showReturnWindow() {
        JFrame retFrame = new JFrame("Agent - Return Vehicle");
        retFrame.setSize(450, 250);
        retFrame.setLayout(new GridLayout(4, 1, 10, 10));

        retFrame.add(new JLabel("Select Active Reservation to Return:"));
        JComboBox<String> activeResDropdown = new JComboBox<>();
        // Sadece "ONAYLANMIŞ" yani şu an arabayı kullanan rezervasyonları listeliyoruz
        for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
            if (r.getStatus().equalsIgnoreCase("CONFIRMED")) {
                activeResDropdown.addItem(r.getReservationID() + " - " + r.getVehicle().getLicensePlate());
            }
        }
        retFrame.add(activeResDropdown);

        JCheckBox chkDamaged = new JCheckBox("Vehicle is Damaged (Needs Mechanic)");
        retFrame.add(chkDamaged);

        JButton btnReturn = new JButton("Process Return");
        retFrame.add(btnReturn);

        btnReturn.addActionListener(e -> {
            if (activeResDropdown.getSelectedIndex() != -1) {
                String selectedText = (String) activeResDropdown.getSelectedItem();
                String resID = selectedText.split(" ")[0];

                for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
                    if (r.getReservationID().equals(resID)) {
                        // Arabayı teslim alırken gişe görevlisi araca bakıyor. 
                        // Hasar varsa direkt tamirciye, yoksa tekrar kiralamaya açıyor.
                        activeAgent.processReturn(r.getVehicle(), chkDamaged.isSelected());
                        r.setStatus(chkDamaged.isSelected() ? "Completed-Damaged" : "Completed");

                        FileManager.updateVehicleFile(CarRentalAgency.vehicleList);
                        FileManager.updateReservationFile(CarRentalAgency.reservationList);

                        JOptionPane.showMessageDialog(retFrame, "Return processed by " + activeAgent.getName() + ".");
                        retFrame.dispose();
                        break;
                    }
                }
            }
        });

        retFrame.setLocationRelativeTo(null);
        retFrame.setVisible(true);
    }

    // --- TAMİRCİ VE MÜDÜR İŞLEMLERİ ---

    private void showRepairWindow() {
        JFrame repFrame = new JFrame("Mechanic - Finish Repairs");
        repFrame.setSize(400, 200);
        repFrame.setLayout(new GridLayout(3, 1, 10, 10));

        repFrame.add(new JLabel("Select Vehicle to Finish Repair:"));
        JComboBox<String> damagedDropdown = new JComboBox<>();

        // Sadece "BAKIMDA" olan araçları listeliyoruz ki tamirci sadece bozuk arabaları görsün
        for (Vehicle v : CarRentalAgency.vehicleList) {
            if (v.getStatus() == enums.VehicleStatus.IN_MAINTENANCE) {
                damagedDropdown.addItem(v.getLicensePlate() + " - " + v.getBrandName());
            }
        }
        repFrame.add(damagedDropdown);

        JButton btnRepair = new JButton("Mark as Repaired & Available");
        repFrame.add(btnRepair);

        btnRepair.addActionListener(e -> {
            if (damagedDropdown.getSelectedIndex() != -1) {
                String plate = ((String) damagedDropdown.getSelectedItem()).split(" - ")[0];

                for (Vehicle v : CarRentalAgency.vehicleList) {
                    if (v.getLicensePlate().equals(plate)) {
                        activeMechanic.finishRepair(v); // Arabanın durumunu iyileşti (AVAILABLE) olarak güncelliyoruz
                        FileManager.updateVehicleFile(CarRentalAgency.vehicleList);
                        JOptionPane.showMessageDialog(repFrame, "Repair finished by " + activeMechanic.getName() + ".");
                        repFrame.dispose();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(repFrame, "No vehicles currently need maintenance.");
            }
        });

        repFrame.setLocationRelativeTo(null);
        repFrame.setVisible(true);
    }

    private void showManagerWindow() {
        JFrame mgrFrame = new JFrame("Manager - Give Raise");
        mgrFrame.setSize(400, 250);
        mgrFrame.setLayout(new GridLayout(4, 2, 10, 10));

        // Hangi şubedeysek o şubenin elemanlarına zam yapabiliyoruz
        mgrFrame.add(new JLabel("  Select Employee:"));
        JComboBox<String> empDropdown = new JComboBox<>();
        for (Employee emp : activeBranch.getBranchEmployees()) {
            empDropdown.addItem(emp.getName() + " - TL" + emp.getSalary());
        }
        mgrFrame.add(empDropdown);

        mgrFrame.add(new JLabel("  Raise Amount (TL):"));
        JTextField txtRaise = new JTextField();
        mgrFrame.add(txtRaise);

        JButton btnRaise = new JButton("Approve Raise");
        mgrFrame.add(new JLabel(""));
        mgrFrame.add(btnRaise);

        btnRaise.addActionListener(e -> {
            try {
                int selectedIndex = empDropdown.getSelectedIndex();
                double raiseAmt = Double.parseDouble(txtRaise.getText());

                if (selectedIndex != -1) {
                    Employee target = activeBranch.getBranchEmployees().get(selectedIndex);
                    activeManager.raise(target, raiseAmt);
                    JOptionPane.showMessageDialog(mgrFrame, "Raise approved by " + activeManager.getName() + "!\nNew Salary: " + target.getSalary());
                    mgrFrame.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mgrFrame, "Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mgrFrame.setLocationRelativeTo(null);
        mgrFrame.setVisible(true);
    }

    private void showOngoingsWindow() {
        JFrame ongFrame = new JFrame("Manager - Branch Dashboard");
        ongFrame.setSize(400, 250);
        ongFrame.setLayout(new GridLayout(4, 1, 10, 10));
        ongFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblBranch = new JLabel("Financial Report: " + activeBranch.getCityName() + " Branch");
        lblBranch.setFont(new Font("SansSerif", Font.BOLD, 16));
        ongFrame.add(lblBranch);

        double revenue = activeBranch.getMonthlyRevenue();
        ongFrame.add(new JLabel("Total Monthly Revenue: TL " + revenue));

        // Şubenin net kârını bulmak için kazanılan paradan o şubedeki çalışanların maaşlarını çıkarıyoruz
        double netProfit = activeBranch.calculateMonthlyOngoings();
        double expenses = revenue - netProfit;
        ongFrame.add(new JLabel("Total Employee Expenses: TL " + expenses));

        JLabel lblProfit = new JLabel("NET ONGOINGS (PROFIT): TL " + netProfit);
        lblProfit.setFont(new Font("SansSerif", Font.BOLD, 14));
        // Zarar ediyorsak kırmızı, kâr ediyorsak yeşil yazıyoruz
        lblProfit.setForeground(netProfit < 0 ? Color.RED : new Color(0, 128, 0));
        ongFrame.add(lblProfit);

        ongFrame.setLocationRelativeTo(null);
        ongFrame.setVisible(true);
    }

    // --- FATURA VE HESAP-KİTAP İŞLERİ ---

    /**
     * Müşteri arabayı getirdiğinde faturasını kestiğimiz yer.
     * Burada kaç gün kullandığına, sigorta isteyip istemediğine,
     * arabaya hasar verip vermediğine ve kilometre sınırını aşıp aşmadığına bakıp
     * hepsini tek bir fişte topluyoruz. Kazanılan parayı da o an bulunduğumuz şubenin kasasına atıyoruz.
     */
    private void showInvoiceWindow() {
        JFrame invFrame = new JFrame("Generate Invoice");
        invFrame.setSize(450, 480);
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        topPanel.add(new JLabel("Select Completed Reservation:"));

        JComboBox<String> activeResDropdown = new JComboBox<>();
        for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
            if (r.getStatus().toLowerCase().startsWith("completed")) {
                activeResDropdown.addItem(r.getReservationID() + " - " + r.getCustomer().getName());
            }
        }
        topPanel.add(activeResDropdown);

        JCheckBox chkInsurance = new JCheckBox("Add Full Insurance Coverage");
        topPanel.add(chkInsurance);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JTextArea txtReceipt = new JTextArea();
        txtReceipt.setEditable(false);
        txtReceipt.setFont(new Font("Monospaced", Font.PLAIN, 14));
        mainPanel.add(new JScrollPane(txtReceipt), BorderLayout.CENTER);

        JButton btnCalculate = new JButton("Generate Receipt");
        mainPanel.add(btnCalculate, BorderLayout.SOUTH);
        invFrame.add(mainPanel);

        btnCalculate.addActionListener(e -> {
            if (activeResDropdown.getSelectedIndex() != -1) {
                String selectedText = (String) activeResDropdown.getSelectedItem();
                String resID = selectedText.split(" - ")[0].trim();

                concreteClasses.Reservation selectedRes = null;
                for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
                    if (r.getReservationID().equals(resID)) {
                        selectedRes = r;
                        break;
                    }
                }

                if (selectedRes != null) {
                    // Aynı müşteri için kazara iki kere fatura kesilmesini önlüyoruz
                    if (selectedRes.getStatus().equalsIgnoreCase("Billed")) {
                        JOptionPane.showMessageDialog(invFrame, "This invoice was already generated!", "Double Billing Error", JOptionPane.ERROR_MESSAGE);
                        activeResDropdown.removeItem(selectedText);
                        return;
                    }

                    // Fazla kilometre yapıp yapmadığını anlamak için arabayla ne kadar yol yaptıklarını soruyoruz
                    String KMSInput = JOptionPane.showInputDialog(invFrame, "Enter Total KM's Driven during this reservation:", "Mileage Check", JOptionPane.QUESTION_MESSAGE);
                    if (KMSInput == null || KMSInput.trim().isEmpty()) return;

                    int KMSDriven;
                    try {
                        KMSDriven = Integer.parseInt(KMSInput);
                        if (KMSDriven < 0) throw new NumberFormatException();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(invFrame, "Please enter a valid positive number for KM's.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Önce (Gün x Aracın Günlük Fiyatı) üzerinden temel faturayı yaratıyoruz
                    String newInvID = "INV-" + System.currentTimeMillis();
                    concreteClasses.Invoice newInvoice = new concreteClasses.Invoice(newInvID, selectedRes);

                    // 1. Arabayı teslim ederken hasarlı işaretlediysek 500 TL ceza kesiyoruz
                    double damageFine = 0.0;
                    if (selectedRes.getStatus().equalsIgnoreCase("Completed-Damaged")) {
                        damageFine = 500.0;
                        newInvoice.setFinalAmount(newInvoice.getFinalAmount() + damageFine);
                    }

                    // 2. Sigorta kutucuğunu işaretledilerse, o aracın sigorta ücretini ekliyoruz
                    double insuranceFee = 0.0;
                    if (chkInsurance.isSelected()) {
                        insuranceFee = selectedRes.getVehicle().calculateInsuranceCost();
                        newInvoice.setFinalAmount(newInvoice.getFinalAmount() + insuranceFee);
                    }

                    // 3. Aracın günlük kilometre sınırını geçtiyse eklenen fazlalık ücreti hesaplıyoruz
                    long days = selectedRes.calculateTotalDays();
                    if (days == 0) days = 1; // 0 gün kiralanmış gibi olmasın diye en az 1 gün sayıyoruz
                    
                    int totalAllowedKMS = selectedRes.getVehicle().getDailyMileageLimit() * (int) days;
                    double mileageFee = 0.0;

                    if (KMSDriven > totalAllowedKMS) {
                        int extraKMS = KMSDriven - totalAllowedKMS;
                        mileageFee = extraKMS * selectedRes.getVehicle().getMileageOverageRate();
                        newInvoice.setFinalAmount(newInvoice.getFinalAmount() + mileageFee);
                    }

                    // Arabanın kilometre sayacını gerçek hayattaki gibi güncelliyoruz
                    selectedRes.getVehicle().setMileage(selectedRes.getVehicle().getMileage() + KMSDriven); 
                    
                    // Faturadan çıkan toplam parayı şubenin kasasına (gelirine) atıyoruz
                    activeBranch.addRevenue(newInvoice.getFinalAmount()); 

                    // Müşteriye hediye sadakat puanlarını veriyoruz
                    Customer c = selectedRes.getCustomer();
                    int earnedPoints = (int) days * 10;
                    c.addLoyaltyPts(earnedPoints);

                    selectedRes.setStatus("Billed"); // Durumu faturalandırıldı yapıyoruz ki bir daha faturası kesilemesin

                    // Değişen her şeyi txt dosyalarına kaydediyoruz
                    FileManager.updateCustomerFile(CarRentalAgency.customerList);
                    FileManager.updateReservationFile(CarRentalAgency.reservationList);
                    FileManager.updateVehicleFile(CarRentalAgency.vehicleList);

                    // Ekranda basılacak olan şık fiş metni
                    String receipt = "==============================\n"
                            + "        " + CarRentalAgency.AGENCY_NAME + "\n"
                            + "==============================\n"
                            + " Invoice ID:      " + newInvoice.getInvoiceID() + "\n"
                            + " Reservation ID:  " + selectedRes.getReservationID() + "\n"
                            + " Customer: " + c.getName() + " [" + c.getLoyaltyTier() + "]\n"
                            + "------------------------------\n"
                            + " Base Rent:       " + newInvoice.getBaseAmount() + " TL\n";

                    if (newInvoice.getDiscountAmount() > 0) receipt += " Loyalty Discount: -" + newInvoice.getDiscountAmount() + " TL\n";
                    if (insuranceFee > 0) receipt += " Insurance Fee:   +" + insuranceFee + " TL\n";
                    if (mileageFee > 0) receipt += " Mileage Penalty: +" + mileageFee + " TL\n  (" + (KMSDriven - totalAllowedKMS) + " extra KM's)\n";
                    if (damageFine > 0) receipt += " DAMAGE FINE:     +" + damageFine + " TL\n";

                    receipt += "------------------------------\n"
                            + " TOTAL DUE:       " + newInvoice.getFinalAmount() + " TL\n"
                            + "==============================\n"
                            + " Points Earned: " + earnedPoints + "\n"
                            + " New Point Balance: " + c.getLoyaltyPts();

                    txtReceipt.setText(receipt);
                    FileManager.saveInvoice(newInvoice);
                    activeResDropdown.removeItem(selectedText);
                }
            } else {
                JOptionPane.showMessageDialog(invFrame, "No completed reservations found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        invFrame.setLocationRelativeTo(null);
        invFrame.setVisible(true);
    }

    private void showInvoiceListWindow() {
        JFrame listFrame = new JFrame(CarRentalAgency.AGENCY_NAME + " - Invoice History");
        listFrame.setSize(750, 400);

        // Faturaları dosyadan okuyup listemize çekiyoruz
        java.util.List<concreteClasses.Invoice> invoices = FileManager.loadInvoices(CarRentalAgency.reservationList);

        String[] cols = {"Invoice ID", "Reservation ID", "Customer", "Base Amount", "Discount", "Total Paid"};
        Object[][] data = new Object[invoices.size()][6];

        for (int i = 0; i < invoices.size(); i++) {
            concreteClasses.Invoice inv = invoices.get(i);
            data[i][0] = inv.getInvoiceID();
            data[i][1] = inv.getReservation().getReservationID();
            data[i][2] = inv.getReservation().getCustomer().getName();
            data[i][3] = String.format("TL %.2f", inv.getBaseAmount());
            data[i][4] = String.format("-TL %.2f", inv.getDiscountAmount());
            data[i][5] = String.format("TL %.2f", inv.getFinalAmount());
        }

        JTable table = new JTable(data, cols);
        listFrame.add(new JScrollPane(table));
        listFrame.setLocationRelativeTo(null);
        listFrame.setVisible(true);
    }

    private void showReservationListWindow() {
        JFrame resListFrame = new JFrame(CarRentalAgency.AGENCY_NAME + " - All Reservations");
        resListFrame.setSize(750, 400);

        String[] cols = {"Res ID", "Customer", "Vehicle Plate", "Start Date", "End Date", "Status"};
        Object[][] data = new Object[CarRentalAgency.reservationList.size()][6];

        for (int i = 0; i < CarRentalAgency.reservationList.size(); i++) {
            concreteClasses.Reservation r = CarRentalAgency.reservationList.get(i);
            data[i][0] = r.getReservationID();
            data[i][1] = r.getCustomer().getName();
            data[i][2] = r.getVehicle().getLicensePlate();
            data[i][3] = r.getStartDate().toString();
            data[i][4] = r.getEndDate().toString();
            data[i][5] = r.getStatus();
        }

        JTable table = new JTable(data, cols);
        resListFrame.add(new JScrollPane(table));
        resListFrame.setLocationRelativeTo(null);
        resListFrame.setVisible(true);
    }
}