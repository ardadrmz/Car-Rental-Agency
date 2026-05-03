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
import exceptions.InvalidReservationException;
import exceptions.VehicleNotAvailable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class GUIApp {

    private RentalAgent activeAgent = new RentalAgent("İstanbul", 35000, "Ahmet (Agent)", "555-0001", "A01", enums.UserRole.AGENT);
    private Mechanic activeMechanic = new Mechanic("İstanbul", 40000, "Mehmet (Mechanic)", "555-0002", "M01", enums.UserRole.MECHANIC);
    private BranchManager activeManager = new BranchManager("İstanbul", 60000, "Ayse (Manager)", "555-0003", "B01");

    private java.util.List<Employee> branchEmployees = new ArrayList<>();

    public GUIApp() {
        branchEmployees.add(activeAgent);
        branchEmployees.add(activeMechanic);
        branchEmployees.add(activeManager);
    }

    public void createAndShowGUI() {

        JFrame frame = new JFrame(CarRentalAgency.AGENCY_NAME + " - Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel headerLabel = new JLabel(CarRentalAgency.AGENCY_NAME);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(new Color(44, 62, 80));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));

        JButton btnVehicles = new JButton("View All Vehicles");
        JButton btnCustomer = new JButton("Register New Customer");
        JButton btnAddVehicle = new JButton("Register New Vehicle");

        JButton btnReservation = new JButton("Agent: Make a Reservation");
        JButton btnReturn = new JButton("Agent: Return Vehicle");
        JButton btnInvoice = new JButton("Agent: Generate Invoice");

        JButton btnRepair = new JButton("Mechanic: Finish Repair");
        JButton btnManager = new JButton("Manager: Give Raise");

        JButton btnExit = new JButton("Exit System");

        btnVehicles.addActionListener(e -> showVehicleWindow());
        btnCustomer.addActionListener(e -> showRegisterCustomerWindow());
        btnAddVehicle.addActionListener(e -> showRegisterVehicleWindow());
        btnReservation.addActionListener(e -> showReservationWindow());
        btnReturn.addActionListener(e -> showReturnWindow());
        btnInvoice.addActionListener(e -> showInvoiceWindow());
        btnRepair.addActionListener(e -> showRepairWindow());
        btnManager.addActionListener(e -> showManagerWindow());

        btnExit.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to exit " + CarRentalAgency.AGENCY_NAME + "?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonPanel.add(btnVehicles);
        buttonPanel.add(btnCustomer);
        buttonPanel.add(btnAddVehicle);
        buttonPanel.add(btnReservation);
        buttonPanel.add(btnReturn);
        buttonPanel.add(btnInvoice);
        buttonPanel.add(btnRepair);
        buttonPanel.add(btnManager);
        buttonPanel.add(btnExit);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showVehicleWindow() {
        JFrame vehicleFrame = new JFrame(CarRentalAgency.AGENCY_NAME + " - Vehicle Fleet");
        vehicleFrame.setSize(700, 400);

        String[] columns = {"Type", "Plate", "Brand", "Mileage", "Status", "Branch"};
        Object[][] data = new Object[CarRentalAgency.vehicleList.size()][6];

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
        JScrollPane scrollPane = new JScrollPane(table);

        vehicleFrame.add(scrollPane);
        vehicleFrame.setLocationRelativeTo(null);
        vehicleFrame.setVisible(true);
    }

    private void showRegisterCustomerWindow() {
        JFrame regFrame = new JFrame("Register Customer");
        regFrame.setSize(400, 300);
        regFrame.setLayout(new GridLayout(5, 2, 10, 10));

        regFrame.add(new JLabel("  User ID:"));
        JTextField txtID = new JTextField();
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
            Customer newCust = new Customer(txtName.getText(), txtContact.getText(), txtID.getText(), enums.UserRole.CUSTOMER);
            CarRentalAgency.customerList.add(newCust);
            FileManager.saveCustomer(newCust);
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
                FileManager.saveVehicle(newVeh);
                JOptionPane.showMessageDialog(vehFrame, "Vehicle registered successfully!");
                vehFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vehFrame, "Please enter a valid whole number for mileage.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        vehFrame.setLocationRelativeTo(null);
        vehFrame.setVisible(true);
    }

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

        resFrame.add(new JLabel("  Select Vehicle:"));
        JComboBox<String> vehicleDropdown = new JComboBox<>();
        for (Vehicle v : CarRentalAgency.vehicleList) {
            vehicleDropdown.addItem(v.getBrandName() + " - " + v.getLicensePlate());
        }
        resFrame.add(vehicleDropdown);

        resFrame.add(new JLabel("  Start Date (YYYY-MM-DD):"));
        JTextField txtStartDate = new JTextField();
        resFrame.add(txtStartDate);

        resFrame.add(new JLabel("  End Date (YYYY-MM-DD):"));
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

                    concreteClasses.Reservation newRes = activeAgent.createReservation(selectedCustomer, selectedVehicle, startDate, endDate);

                    CarRentalAgency.reservationList.add(newRes);
                    FileManager.updateVehicleFile(CarRentalAgency.vehicleList);

                    JOptionPane.showMessageDialog(resFrame, "Reservation Confirmed by " + activeAgent.getName() + "!\nYour ID is: " + newRes.getReservationID());
                    resFrame.dispose();

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(resFrame, "Please enter the dates exactly as YYYY-MM-DD!", "Date Format Error", JOptionPane.ERROR_MESSAGE);
                } catch (VehicleNotAvailable | InvalidReservationException ex) {
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
        for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
            if (r.getStatus().equals("CONFIRMED")) {
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

                        activeAgent.processReturn(r.getVehicle(), chkDamaged.isSelected());
                        r.setStatus("Completed");

                        FileManager.updateVehicleFile(CarRentalAgency.vehicleList);
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

    private void showRepairWindow() {
        JFrame repFrame = new JFrame("Mechanic - Finish Repairs");
        repFrame.setSize(400, 200);
        repFrame.setLayout(new GridLayout(3, 1, 10, 10));

        repFrame.add(new JLabel("Select Vehicle to Finish Repair:"));
        JComboBox<String> damagedDropdown = new JComboBox<>();

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
                String plate = ((String) damagedDropdown.getSelectedItem()).split(" ")[0];

                for (Vehicle v : CarRentalAgency.vehicleList) {
                    if (v.getLicensePlate().equals(plate)) {

                        activeMechanic.finishRepair(v);
                        FileManager.updateVehicleFile(CarRentalAgency.vehicleList);

                        JOptionPane.showMessageDialog(repFrame, "Repair finished by " + activeMechanic.getName() + ". Vehicle is now available.");
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

        mgrFrame.add(new JLabel("  Select Employee:"));
        JComboBox<String> empDropdown = new JComboBox<>();
        for (Employee emp : branchEmployees) {
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
                    Employee target = branchEmployees.get(selectedIndex);

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

    private void showInvoiceWindow() {
        JFrame invFrame = new JFrame("Generate Invoice");
        invFrame.setSize(450, 350);
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.add(new JLabel("Select Completed Reservation:"));

        JComboBox<String> activeResDropdown = new JComboBox<>();
        for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
            if (r.getStatus().equals("Completed")) {
                activeResDropdown.addItem(r.getReservationID() + " - " + r.getCustomer().getName());
            }
        }
        topPanel.add(activeResDropdown);
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
                String resID = selectedText.split(" ")[0];

                concreteClasses.Reservation selectedRes = null;
                for (concreteClasses.Reservation r : CarRentalAgency.reservationList) {
                    if (r.getReservationID().equals(resID)) {
                        selectedRes = r;
                        break;
                    }
                }

                if (selectedRes != null) {
                    long totalDays = selectedRes.calculateTotalDays();
                    double dailyRate = selectedRes.getVehicle().getDailyRate();
                    double subTotal = totalDays * dailyRate;

                    Customer c = selectedRes.getCustomer();
                    String tier = c.getLoyaltyTier();
                    double discountAmount = subTotal * c.getDiscountRate();
                    double finalTotal = subTotal - discountAmount;

                    int earnedPoints = (int) totalDays * 10;
                    c.addLoyaltyPts(earnedPoints);

                    selectedRes.setStatus("Billed");

                    FileManager.updateCustomerFile(CarRentalAgency.customerList);
                    FileManager.updateReservationFile(CarRentalAgency.reservationList);

                    String receipt = "==============================\n"
                            + "        " + CarRentalAgency.AGENCY_NAME + "\n"
                            + "==============================\n"
                            + " Reservation ID: " + selectedRes.getReservationID() + "\n"
                            + " Customer: " + c.getName() + " [" + tier + " Member]\n"
                            + " Vehicle: " + selectedRes.getVehicle().getBrandName() + "\n"
                            + "------------------------------\n"
                            + " Total Days Rent: " + totalDays + "\n"
                            + " Daily Rate:      $" + dailyRate + "\n"
                            + " Subtotal:        $" + subTotal + "\n";

                    if (discountAmount > 0) {
                        receipt += " Loyalty Discount: -$" + discountAmount + " (" + (int) (c.getDiscountRate() * 100) + "%)\n";
                    }

                    receipt += "------------------------------\n"
                            + " TOTAL DUE:       $" + finalTotal + "\n"
                            + "==============================\n"
                            + " Points Earned: " + earnedPoints + "\n"
                            + " New Point Balance: " + c.getLoyaltyPts();

                    txtReceipt.setText(receipt);
                    activeResDropdown.removeItem(selectedText);
                }
            } else {
                JOptionPane.showMessageDialog(invFrame, "No completed reservations found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        invFrame.setLocationRelativeTo(null);
        invFrame.setVisible(true);
    }
}
