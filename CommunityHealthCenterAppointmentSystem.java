/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package communityhealthcenterappointmentsystem;
import java.util.Scanner;

/**
 * A console-based, menu-driven Java system that manages patient appointments
 * using arrays. Demonstrates variables, operators, decision structure,
 * loops, branching statements, arrays, and input validation.
 */
public class CommunityHealthCenterAppointmentSystem {

    // Maximum number of appointments the system can store
    static final int MAX_APPOINTMENTS = 50;

    // Parallel arrays to store appointment records
    static String[] patientNames = new String[MAX_APPOINTMENTS];
    static int[] ages = new int[MAX_APPOINTMENTS];
    static String[] contactNumbers = new String[MAX_APPOINTMENTS];
    static String[] appointmentDates = new String[MAX_APPOINTMENTS];
    static String[] appointmentTimes = new String[MAX_APPOINTMENTS];
    static String[] services = new String[MAX_APPOINTMENTS];
    static double[] consultationFees = new double[MAX_APPOINTMENTS];
    static String[] statuses = new String[MAX_APPOINTMENTS]; // Scheduled, Completed, Cancelled1
    

    // Counter for the number of current appointments
    static int appointmentCount = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayWelcome();
        int choice;

        do {
            displayMainMenu();
            choice = getValidMenuChoice(1, 8);

            switch (choice) {
                case 1:
                    addAppointment();
                    break;
                case 2:
                    viewAllAppointments();
                    break;
                case 3:
                    searchAppointment();
                    break;
                case 4:
                    updateAppointment();
                    break;
                case 5:
                    deleteAppointment();
                    break;
                case 6:
                    computeAndProcess();
                    break;
                case 7:
                    displayHelp();
                    break;
                case 8:
                    exitSystem();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

            if (choice != 8) {
                pressEnterToContinue();
            }

        } while (choice != 8);

        scanner.close();
    }

    // ==================== MENU & DISPLAY METHODS ====================

    static void displayWelcome() {
        System.out.println("=======================================================");
        System.out.println("   COMMUNITY HEALTH CENTER APPOINTMENT SYSTEM");
        System.out.println("=======================================================");
        System.out.println("  Welcome! This system helps manage patient appointments");
        System.out.println("  for a community health center.");
        System.out.println("=======================================================\n");
    }

    static void displayMainMenu() {
        System.out.println("\n==================== MAIN MENU ====================");
        System.out.println("1. Add / Register New Appointment");
        System.out.println("2. View All Appointments");
        System.out.println("3. Search Appointment");
        System.out.println("4. Update Appointment");
        System.out.println("5. Delete / Cancel Appointment");
        System.out.println("6. Computation & Reports");
        System.out.println("7. Help / System Info");
        System.out.println("8. Exit System");
        System.out.println("===================================================");
        System.out.print("Enter your choice (1-8): ");
    }

    // ==================== INPUT VALIDATION ====================

    static int getValidMenuChoice(int min, int max) {
        int choice = -1;
        boolean valid = false;

        while (!valid) {
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    valid = true;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
        return choice;
    }

    static int getValidAge() {
        int age = -1;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Enter patient age (1-120): ");
                String input = scanner.nextLine().trim();
                age = Integer.parseInt(input);
                if (age >= 1 && age <= 120) {
                    valid = true;
                } else {
                    System.out.println("Age must be between 1 and 120.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for age.");
            }
        }
        return age;
    }

    static String getNonEmptyString(String prompt) {
        String value = "";
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    static String getValidContact() {
        String contact = "";
        while (true) {
            System.out.print("Enter contact number (e.g., 09XXXXXXXXX): ");
            contact = scanner.nextLine().trim();
            // Simple validation: at least 10 digits
            if (contact.matches("\\d{10,15}")) {
                return contact;
            }
            System.out.println("Invalid contact number. Please enter 10-15 digits only.");
        }
    }

    static String getValidDate() {
        String date = "";
        while (true) {
            System.out.print("Enter appointment date (MM/DD/YYYY): ");
            date = scanner.nextLine().trim();
            // Basic format check
            if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Invalid date format. Please use MM/DD/YYYY (example: 11/20/2026).");
                continue;
                }
                String[] parts = date.split("/");
                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                if (month < 1 || month > 12) {
                	System.out.println("Invalid month. Month must be 1-12.");
                	continue;
                }
                if (year < 1) {
                    System.out.println("Invalid year");
                }
                
                if (day < 1 || day > 31) {
                	System.out.println("Invalid day. Day must be 1-31."); 
                	continue;
        }
                
        if ( month == 2 && day == 29 && !(year % 400 == 0 || (year % 4 == 0 && year % 100 !=0))) {
                	System.out.println("Invalid date. February only has 28 days this year."); 
                	continue;
   	 }
        
   	 if (month == 2 && day > 29) {
   	 	System.out.println("February only has up to 29 days.");
   	 	continue;
    }
    return date;
        }
    }
    static String getValidTime() {
        String time = "";
        while (true) {
            System.out.print("Enter appointment time (e.g., 09:00 AM or 14:30): ");
            time = scanner.nextLine().trim();
            if (!time.isEmpty()) {
                return time;
            }
            System.out.println("Time cannot be empty.");
        }
    }

    static double getValidFee() {
        double fee = -1;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Enter consultation fee (PHP): ");
                String input = scanner.nextLine().trim();
                fee = Double.parseDouble(input);
                if (fee >= 0) {
                    valid = true;
                } else {
                    System.out.println("Fee cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
        return fee;
    }

    // ==================== CORE FEATURES ====================

    /**
     * 1. Add / Register Records
     */
    static void addAppointment() {
        System.out.println("\n---------- ADD NEW APPOINTMENT ----------");

        if (appointmentCount >= MAX_APPOINTMENTS) {
            System.out.println("Sorry, the appointment list is full. Maximum of "
                    + MAX_APPOINTMENTS + " appointments allowed.");
            return;
        }

        System.out.println("Please enter the patient and appointment details:\n");

        String name = getNonEmptyString("Patient Full Name: ");
        int age = getValidAge();
        String contact = getValidContact();
        String date = getValidDate();
        String time = getValidTime();

        System.out.println("\nAvailable Services:");
        System.out.println("1. General Consultation");
        System.out.println("2. Maternal & Child Health");
        System.out.println("3. Immunization");
        System.out.println("4. Dental Check-up");
        System.out.println("5. Laboratory / Diagnostics");
        System.out.println("6. Family Planning");
        System.out.println("7. Other");
        System.out.print("Select service (1-7): ");
        int serviceChoice = getValidMenuChoice(1, 7);

        String service = "";
        switch (serviceChoice) {
            case 1: service = "General Consultation"; break;
            case 2: service = "Maternal & Child Health"; break;
            case 3: service = "Immunization"; break;
            case 4: service = "Dental Check-up"; break;
            case 5: service = "Laboratory / Diagnostics"; break;
            case 6: service = "Family Planning"; break;
            case 7: service = getNonEmptyString("Please specify the service: "); break;
        }

        double fee = getValidFee();

        // Store the record
        patientNames[appointmentCount] = name;
        ages[appointmentCount] = age;
        contactNumbers[appointmentCount] = contact;
        appointmentDates[appointmentCount] = date;
        appointmentTimes[appointmentCount] = time;
        services[appointmentCount] = service;
        consultationFees[appointmentCount] = fee;
        statuses[appointmentCount] = "Scheduled";

        appointmentCount++;

        System.out.println("\n*** Appointment successfully registered! ***");
        System.out.println("Appointment ID (index): " + (appointmentCount));
        System.out.println("Patient: " + name);
        System.out.println("Date & Time: " + date + " at " + time);
        System.out.println("Service: " + service);
        System.out.println("Fee: PHP " + String.format("%.2f", fee));
    }

    /**
     * 2. View / Display Records
     */
    static void viewAllAppointments() {
        System.out.println("\n---------- ALL APPOINTMENTS ----------");

        if (appointmentCount == 0) {
            System.out.println("No appointments found. The list is currently empty.");
            return;
        }

        System.out.println("Total Appointments: " + appointmentCount);
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-4s %-22s %-5s %-13s %-12s %-10s %-12s%n",
                "ID", "Patient Name", "Age", "Contact", "Date", "Time", "Status");
        System.out.println("----------------------------------------------------------------------");

        for (int i = 0; i < appointmentCount; i++) {
            System.out.printf("%-4d %-22s %-5d %-13s %-12s %-10s %-12s%n",
                    (i + 1),
                    truncate(patientNames[i], 22),
                    ages[i],
                    contactNumbers[i],
                    appointmentDates[i],
                    appointmentTimes[i],
                    statuses[i]);
        }
        System.out.println("----------------------------------------------------------------------");

        // Show more details option
        System.out.print("\nEnter ID to view full details (or 0 to return): ");
        int detailId = getValidMenuChoice(0, appointmentCount);
        if (detailId > 0) {
            displayFullDetails(detailId - 1);
        }
    }

    static void displayFullDetails(int index) {
        System.out.println("\n===== APPOINTMENT DETAILS =====");
        System.out.println("ID              : " + (index + 1));
        System.out.println("Patient Name    : " + patientNames[index]);
        System.out.println("Age             : " + ages[index]);
        System.out.println("Contact Number  : " + contactNumbers[index]);
        System.out.println("Appointment Date: " + appointmentDates[index]);
        System.out.println("Appointment Time: " + appointmentTimes[index]);
        System.out.println("Service         : " + services[index]);
        System.out.println("Consultation Fee: PHP " + String.format("%.2f", consultationFees[index]));
        System.out.println("Status          : " + statuses[index]);
        System.out.println("================================");
    }

    /**
     * 3. Search Records
     */
    static void searchAppointment() {
        System.out.println("\n---------- SEARCH APPOINTMENT ----------");

        if (appointmentCount == 0) {
            System.out.println("No appointments available to search.");
            return;
        }

        System.out.println("Search by:");
        System.out.println("1. Patient Name");
        System.out.println("2. Contact Number");
        System.out.println("3. Appointment Date");
        System.out.print("Choose search type (1-3): ");
        int searchType = getValidMenuChoice(1, 3);

        String keyword = getNonEmptyString("Enter search keyword: ").toLowerCase();

        boolean found = false;
        System.out.println("\n----- Search Results -----");

        for (int i = 0; i < appointmentCount; i++) {
            boolean match = false;

            switch (searchType) {
                case 1:
                    if (patientNames[i].toLowerCase().contains(keyword)) {
                        match = true;
                    }
                    break;
                case 2:
                    if (contactNumbers[i].contains(keyword)) {
                        match = true;
                    }
                    break;
                case 3:
                    if (appointmentDates[i].contains(keyword)) {
                        match = true;
                    }
                    break;
            }

            if (match) {
                found = true;
                System.out.println("\nID: " + (i + 1));
                System.out.println("Name: " + patientNames[i] + " | Age: " + ages[i]);
                System.out.println("Contact: " + contactNumbers[i]);
                System.out.println("Date/Time: " + appointmentDates[i] + " " + appointmentTimes[i]);
                System.out.println("Service: " + services[i] + " | Fee: PHP " + String.format("%.2f", consultationFees[i]));
                System.out.println("Status: " + statuses[i]);
                System.out.println("-------------------------");
            }
        }

        if (!found) {
            System.out.println("No matching appointment found for: \"" + keyword + "\"");
        }
    }

    /**
     * 4. Update Records
     */
    static void updateAppointment() {
        System.out.println("\n---------- UPDATE APPOINTMENT ----------");

        if (appointmentCount == 0) {
            System.out.println("No appointments available to update.");
            return;
        }

        viewAllAppointmentsSimple();
        System.out.print("Enter the ID of the appointment to update: ");
        int id = getValidMenuChoice(1, appointmentCount);
        int index = id - 1;

        System.out.println("\nCurrent details:");
        displayFullDetails(index);

        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Patient Name");
        System.out.println("2. Age");
        System.out.println("3. Contact Number");
        System.out.println("4. Appointment Date");
        System.out.println("5. Appointment Time");
        System.out.println("6. Service");
        System.out.println("7. Consultation Fee");
        System.out.println("8. Status (Scheduled / Completed / Cancelled)");
        System.out.println("9. Cancel Update");
        System.out.print("Enter choice: ");
        int field = getValidMenuChoice(1, 9);

        switch (field) {
            case 1:
                patientNames[index] = getNonEmptyString("New Patient Name: ");
                break;
            case 2:
                ages[index] = getValidAge();
                break;
            case 3:
                contactNumbers[index] = getValidContact();
                break;
            case 4:
                appointmentDates[index] = getValidDate();
                break;
            case 5:
                appointmentTimes[index] = getValidTime();
                break;
            case 6:
            System.out.println("==================================================");
                System.out.println("1. General Consultation  2. Maternal & Child Health");
                System.out.println("3. Immunization  4. Dental Check-up");
                System.out.println("5. Laboratory / Diagnostics \n 6. Family Planning  7. Other");
                System.out.print("Select new service: ");
                int s = getValidMenuChoice(1, 7);
                switch (s) {
                    case 1: services[index] = "General Consultation"; 
                   	 break;
                    case 2: services[index] = "Maternal & Child Health"; 
                   	 break;
                    case 3: services[index] = "Immunization"; 
                   	 break;
                    case 4: services[index] = "Dental Check-up"; 
                   	 break;
                    case 5: services[index] = "Laboratory / Diagnostics"; 
                  	  break;
                    case 6: services[index] = "Family Planning"; break;
                    case 7: services[index] = getNonEmptyString("Specify: "); 
                  	  break;
                }
                break;
            case 7:
                consultationFees[index] = getValidFee();
                break;
            case 8:
                System.out.println("1. Scheduled  2. Completed  3. Cancelled");
                System.out.print("Select new status: ");
                int st = getValidMenuChoice(1, 3);
                if (st == 1) statuses[index] = "Scheduled";
                else if (st == 2) statuses[index] = "Completed";
                else statuses[index] = "Cancelled";
                break;
            case 9:
                System.out.println("Update cancelled.");
                return;
        }

        System.out.println("\n*** Appointment successfully updated! ***");
        displayFullDetails(index);
    }

    /**
     * 5. Delete Records
     */
    static void deleteAppointment() {
        System.out.println("\n---------- DELETE / CANCEL APPOINTMENT ----------");

        if (appointmentCount == 0) {
            System.out.println("No appointments available to delete.");
            return;
        }

        viewAllAppointmentsSimple();
        System.out.print("Enter the ID of the appointment to delete: ");
        int id = getValidMenuChoice(1, appointmentCount);
        int index = id - 1;

        System.out.println("\nYou are about to delete:");
        displayFullDetails(index);

        System.out.print("Are you sure you want to delete this appointment? (Y/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();

        if (confirm.equals("Y") || confirm.equals("YES")) {
            // Shift remaining records to the left
            for (int i = index; i < appointmentCount - 1; i++) {
                patientNames[i] = patientNames[i + 1];
                ages[i] = ages[i + 1];
                contactNumbers[i] = contactNumbers[i + 1];
                appointmentDates[i] = appointmentDates[i + 1];
                appointmentTimes[i] = appointmentTimes[i + 1];
                services[i] = services[i + 1];
                consultationFees[i] = consultationFees[i + 1];
                statuses[i] = statuses[i + 1];
            }

            // Clear the last slot
            int last = appointmentCount - 1;
            patientNames[last] = null;
            ages[last] = 0;
            contactNumbers[last] = null;
            appointmentDates[last] = null;
            appointmentTimes[last] = null;
            services[last] = null;
            consultationFees[last] = 0;
            statuses[last] = null;

            appointmentCount--;
            System.out.println("\n*** Appointment successfully deleted. ***");
        } else {
            System.out.println("Deletion cancelled. No changes made.");
        }
    }

    /**
     * 6. Computation / Processing
     */
    static void computeAndProcess() {
        System.out.println("\n---------- COMPUTATION & REPORTS ----------");

        if (appointmentCount == 0) {
            System.out.println("No data available for computation.");
            return;
        }

        System.out.println("1. Total number of appointments");
        System.out.println("2. Total consultation fees collected / expected");
        System.out.println("3. Count by Status (Scheduled / Completed / Cancelled)");
        System.out.println("4. Count by Service Type");
        System.out.println("5. Average age of patients");
        System.out.println("6. Appointments for a specific date");
        System.out.print("Select report (1-6): ");
        int report = getValidMenuChoice(1, 6);

        switch (report) {
            case 1:
                System.out.println("\nTotal Appointments Registered: " + appointmentCount);
                break;

            case 2:
                double totalFees = 0;
                for (int i = 0; i < appointmentCount; i++) {
                    totalFees += consultationFees[i];
                }
                System.out.println("\nTotal Consultation Fees: PHP " + String.format("%.2f", totalFees));
                System.out.println("(Based on " + appointmentCount + " appointment(s))");
                break;

            case 3:
                int scheduled = 0, completed = 0, cancelled = 0;
                for (int i = 0; i < appointmentCount; i++) {
                    if (statuses[i].equalsIgnoreCase("Scheduled")) scheduled++;
                    else if (statuses[i].equalsIgnoreCase("Completed")) completed++;
                    else if (statuses[i].equalsIgnoreCase("Cancelled")) cancelled++;
                }
                System.out.println("\n--- Status Summary ---");
                System.out.println("Scheduled : " + scheduled);
                System.out.println("Completed : " + completed);
                System.out.println("Cancelled : " + cancelled);
                System.out.println("Total     : " + appointmentCount);
                break;

            case 4:
                System.out.println("\n--- Appointments by Service ---");
                String[] serviceList = {
                    "General Consultation", "Maternal & Child Health", "Immunization",
                    "Dental Check-up", "Laboratory / Diagnostics", "Family Planning"
                };
                for (String svc : serviceList) {
                    int count = 0;
                    for (int i = 0; i < appointmentCount; i++) {
                        if (services[i].equalsIgnoreCase(svc)) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        System.out.println(svc + ": " + count);
                    }
                }
                // Count "Other"
                int other = 0;
                for (int i = 0; i < appointmentCount; i++) {
                    boolean isKnown = false;
                    for (String svc : serviceList) {
                        if (services[i].equalsIgnoreCase(svc)) {
                            isKnown = true;
                            break;
                        }
                    }
                    if (!isKnown) other++;
                }
                if (other > 0) {
                    System.out.println("Other: " + other);
                }
                break;

            case 5:
                int totalAge = 0;
                for (int i = 0; i < appointmentCount; i++) {
                    totalAge += ages[i];
                }
                double averageAge = (double) totalAge / appointmentCount;
                System.out.println("\nAverage Patient Age: " + String.format("%.1f", averageAge) + " years");
                break;

            case 6:
                String targetDate = getValidDate();
                int matchCount = 0;
                System.out.println("\nAppointments on " + targetDate + ":");
                for (int i = 0; i < appointmentCount; i++) {
                    if (appointmentDates[i].equals(targetDate)) {
                        matchCount++;
                        System.out.println((matchCount) + ". " + patientNames[i] + " - "
                                + appointmentTimes[i] + " (" + services[i] + ")");
                    }
                }
                if (matchCount == 0) {
                    System.out.println("No appointments found on that date.");
                } else {
                    System.out.println("Total: " + matchCount + " appointment(s)");
                }
                break;
        }
    }

    // ==================== HELPER METHODS ====================

    static void viewAllAppointmentsSimple() {
        System.out.println("\nCurrent Appointments:");
        System.out.println("ID | Patient Name              | Date       | Status");
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < appointmentCount; i++) {
            System.out.printf("%-3d| %-25s | %-10s | %s%n",
                    (i + 1), truncate(patientNames[i], 25),
                    appointmentDates[i], statuses[i]);
        }
        System.out.println("---------------------------------------------------");
    }

    static String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    static void displayHelp() {
        System.out.println("\n========== SYSTEM INFORMATION ==========");
        System.out.println("System Name : Community Health Center Appointment System");
        System.out.println("Purpose     : Manage patient appointments in a community health center");
        System.out.println("Max Records : " + MAX_APPOINTMENTS);
        System.out.println("Current     : " + appointmentCount + " appointment(s) stored");
        System.out.println();
        System.out.println("Main Features:");
        System.out.println("  - Add new patient appointments");
        System.out.println("  - View all or detailed appointments");
        System.out.println("  - Search by name, contact, or date");
        System.out.println("  - Update any appointment detail");
        System.out.println("  - Delete/Cancel appointments");
        System.out.println("  - Generate simple reports & computations");
        System.out.println();
        System.out.println("Tips:");
        System.out.println("  - Always enter dates in MM/DD/YYYY format");
        System.out.println("  - Contact numbers should be 10-15 digits");
        System.out.println("  - Use option 6 for totals, averages, and summaries");
        System.out.println("========================================");
    }

    static void exitSystem() {
        System.out.println("\n=========================================");
        System.out.println("  Thank you for using the");
        System.out.println("  Community Health Center");
        System.out.println("  Appointment System!");
        System.out.println("  Have a healthy day!");
        System.out.println("=========================================");
        System.out.println("Program terminated.");
    }

    static void pressEnterToContinue() {
        System.out.print("\nPress Enter to return to the Main Menu...");
        scanner.nextLine();
    }
}
