1. Project Overview
FleetRentPro is a comprehensive desktop application developed to manage the daily operations of a multi-branch car rental agency. It provides a unified graphical user interface for managing a dynamic vehicle fleet, processing customer reservations and returns, generating automated invoices, and tracking branch-specific financial revenues.

2. Technical Stack
Language: Java (JDK 8+)

GUI Framework: Java Swing (AWT/Swing components, Layout Managers)

Database / Storage: Custom Flat-File Database (CSV format via .txt files)

Architecture: Object-Oriented Programming (OOP)

3. System Architecture & OOP Principles
The system is built strictly upon core Object-Oriented principles to ensure scalability and modularity:

Inheritance & Abstraction: The system utilizes abstract base classes such as Vehicle, Employee, and User. Concrete classes (e.g., Economy, SUV, RentalAgent, Mechanic) inherit and expand upon these base properties.

Polymorphism: Vehicle behavior is polymorphic. The getDailyMileageLimit() and calculateInsuranceCost() methods are dynamically resolved at runtime depending on the specific vehicle object selected.

Encapsulation: All sensitive data fields (salaries, loyalty points, system IDs) are kept private and accessed only through secure getter and setter methods.

File I/O Management: The FileManager class centralizes all data persistence logic, ensuring that customer records, vehicle states, reservations, and invoices are safely written to and read from text files without data loss.

4. User Roles and Modules
The application features a role-based operational flow handled from a single, dynamic dashboard.

Rental Agent: Acts as the front-desk operator. Responsible for registering new customers, creating reservations, checking vehicle availability, processing returns, and calculating final invoices.

Mechanic: Responsible for fleet maintenance. Views a filtered list of damaged vehicles and updates their status from "In Maintenance" back to "Available" once repairs are complete.

Branch Manager: Handles administrative tasks. Approves salary raises for branch employees and views the branch dashboard to calculate monthly net profit (Revenue minus Employee Expenses).

5. Core Business Logic
Multi-Branch Tracking: The system supports dynamic branch switching (Istanbul, Izmir, Ankara). Revenue and employee expenses are tracked independently for each branch.

Dynamic Billing & Mileage Policy: Invoices automatically calculate the base rent (days multiplied by daily rate), add optional insurance fees, and enforce mileage limits. If a customer exceeds the daily mileage limit, a penalty fee specific to the vehicle type is applied.

Damage Control: Vehicles returned with damage incur an automatic 500 TL penalty fee on the final invoice and are immediately locked from future reservations until a Mechanic clears them.

Customer Loyalty Program: Customers earn 10 loyalty points for every day they rent a vehicle. Accumulated points can unlock discount tiers (Bronze, Silver, Gold) applied to future invoices.

6. System Diagrams
(Note: Insert the generated Mermaid diagrams here)

Use Case Diagram: Illustrates actor interactions with system modules.

Class Diagram: Details the inheritance tree and properties of system entities.

Activity Diagram: Maps the lifecycle of a reservation from booking to invoice generation.

Sequence Diagram: Shows the chronological communication between objects during the billing process.

7. Installation & Setup
Environment: Ensure Java Development Kit (JDK) is installed on your machine.

IDE Setup: Import the project source code into an IDE of your choice (Eclipse, IntelliJ IDEA, or NetBeans).

File System: No external database setup is required. The system will automatically generate customers.txt, vehicles.txt, reservations.txt, and invoices.txt in the root directory upon the first save action.

Execution: Run the Main class (which calls GUIApp.createAndShowGUI()) to launch the application.

8. Usage Guide (Basic Workflow)
Initialize System: Select the active branch from the top dropdown menu.

Register Entities: Register at least one Customer and one Vehicle using the left-hand panel.

Make Reservation: Under Agent Operations, select a customer, a vehicle, and input the start/end dates (Format: YYYY-MM-DD).

Return Vehicle: Once the customer returns, process the return. Check the damage box if necessary.

Generate Invoice: Select the completed reservation, input the total kilometers driven, and generate the final receipt. The system will update branch revenue and customer points automatically.

Maintenance (If Damaged): The Mechanic selects the damaged vehicle from the repair menu and marks it as available again.
