package com.pluralsight;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountingLedger {
    //Implement the scanner
   static Scanner scanner = new Scanner(System.in);
    //Variables
    static String userInput;
    static String paymentDesc;
    static String paymentVendor;
    static String depoDesc;
    static String depoVendor;
    static int currentMonth;
    static int currentYear;
    static int reportInput;
    static int previousYear;
    static int previousMonth;
    static String ledgerInput;
    static String finalDateTime;
    static String line;
    static String input;
    static String vendor;
    static String searchVendor;
    static String transactionVendor;
    static String transactionDesc;
    static double paymentAmount;
    static double depositAmount;
    static double amount;
    static double transactionAmount;
    static boolean found;
    static boolean match;
    static LocalDate lastMonthDate;
    static LocalDate currentDate;
    static LocalDate lastYearDate;
    static LocalDate transDate;
    static LocalDateTime currentTime;
    static DateTimeFormatter formatDateTime;
    static LocalDate startDate = null;
    static LocalDate endDate = null;
    public static void main(String[] args) {

        Menu();
    }
    public static void Menu() {
        //Menu options
        System.out.println("Welcome to Accounting Firm LLC.");
        System.out.println("[P] Make Payment");
        System.out.println("[D] Deposit money");
        System.out.println("[L] Ledger");
        System.out.println("[M] Return to Menu");
        System.out.println("[Q] Quit");

        System.out.println("Please choose an option: ");
         userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("P")) {
            // Call addDeposit method.
            makePayment();
        } else if (userInput.equalsIgnoreCase("D")) {
            // Call makePayment method.
            addDeposit();
        } else if (userInput.equalsIgnoreCase("L")) {
            // Call ledger method.
            ledger();
        } else if (userInput.equalsIgnoreCase("M")) {
            return;
        } else if (userInput.equalsIgnoreCase("Q")) {
            // Quit.
            System.exit(0);
            // If user enters a wrong input:
        } else {
            System.out.print("Invalid input. Please try again: ");
            userInput = scanner.nextLine();
        }

    }

    private static void makePayment() {
        System.out.println("\nPlease enter Payment Information: ");

        //Prompt the user for their payment description
        System.out.println("Please enter payment description");
        paymentDesc = scanner.nextLine();

        //Prompt the user for the payment vendor
        System.out.println("What is your payment vendor");
        paymentVendor = scanner.nextLine();

        //Prompt the user to put in their payment amount
        System.out.println("Enter payment amount(in negative): ");
        paymentAmount = scanner.nextDouble();
        scanner.nextLine();

        //Format date and get current time.
        formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        currentTime =  LocalDateTime.now();
        finalDateTime = currentTime.format(formatDateTime);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))){
            writer.write("\n"+finalDateTime + "|" + paymentDesc + "|" + paymentVendor + "|" + paymentAmount);

            System.out.println("\n Payment information added to transactions sheet successfully.\n");
            //Catch error if user inputs something different
        } catch (IOException e){
            System.out.println("\n An error occurred while attempting to file. " +e.getMessage() + "\n");
            e.getStackTrace();
        }
        Menu();
    }
    // Create the addDeposit method.
    public static void addDeposit() {
        // Prompt user to enter their deposit information.
        System.out.println("\nPlease enter the deposit information:");

        // Prompt user to enter the deposit information.
        System.out.print("Enter deposit description: ");
        depoDesc = scanner.nextLine();

        // Prompt user to enter deposit vendor.
        System.out.print("Enter deposit vendor: ");
        depoVendor = scanner.nextLine();

        // Prompt user to enter deposit amount.

        System.out.print("Enter deposit amount: ");
        depositAmount = scanner.nextDouble();
        scanner.nextLine();

        // Get current date and time.
        currentTime = LocalDateTime.now();

        // Set the format for the date and time.
        formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");

        // Format the date and time.
        finalDateTime = currentTime.format(formatDateTime);

        // Write the deposit information into the csv.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write("\n" + finalDateTime + "|" + depoDesc + "|" + depoVendor + "|" + depositAmount);

            // Success message.
            System.out.println("\nDeposit information added to transactions.csv successfully.\n");
            // If an error occurred, print error.
        } catch (IOException e) {
            System.out.println("\nAn error occurred while writing to the file: " + e.getMessage() + "\n");
        }

        // Go back to menu.
        Menu();
    }
    // Create the ledger method.
    public static void ledger() {
        // Print the ledger menu options.
        System.out.println("\nA) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("M) Menu");

        // Ask the user what they want to do.
        System.out.print("Please select an option: ");
        ledgerInput = scanner.nextLine();

        if (ledgerInput.equalsIgnoreCase("a")) {
            // Call ledgerAll method.
            ledgerAll();
        } else if (ledgerInput.equalsIgnoreCase("d")) {
            // Call ledgerDeposits method.
            ledgerDeposits();
        } else if (ledgerInput.equalsIgnoreCase("p")) {
            // Call ledgerPayments method.
            ledgerPayments();
        } else if (ledgerInput.equalsIgnoreCase("r")) {
            // Call ledgerReports method.
            ledgerReports();
        } else if (ledgerInput.equalsIgnoreCase("m")) {
            // Return to the Menu
            Menu();
            // If user entered a wrong input.
        } else {
            System.out.print("Invalid input. Please try again: ");
            ledgerInput = scanner.nextLine();
        }
    }
        // Create ledgerAll method.
        public static void ledgerAll() {
            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                line = reader.readLine();

                // If CSV is empty, return "no transaction found" message
                if (line == null) {
                    System.out.println("No transactions found.");
                    return;
                }

                // Print title.
                System.out.println("\nAll Transactions:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Print all transactions.
                    System.out.println(line);
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger menu.
            ledger();
        }

        // Create the ledgerDeposits method.
        private static void ledgerDeposits() {
            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                line = reader.readLine();

                // If CSV is empty, print "no transaction found" message.
                if (line == null) {
                    System.out.println("No transactions found.");
                    return;
                }

                // Print title.
                System.out.println("\nAll Deposit Transactions:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");

                    // Set the amount.
                    amount = Double.parseDouble(parts[4]);

                    // Print all the deposits.
                    if (amount > 0) {
                        System.out.println(line);
                    }
                }
                // If an error occurred, print error.
            } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger menu.
            ledger();
        }

        // Create the ledgerPayments method.
        public static void ledgerPayments() {
            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                line = reader.readLine();

                // If CSV is empty, print message.
                if (line == null) {
                    System.out.println("No transactions found.");
                    return;
                }

                // Print title.
                System.out.println("\nAll Payment Transactions:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");

                    // Set the amount.
                    amount = Double.parseDouble(parts[4]);

                    // Print all the payments.
                    if (amount < 0) {
                        System.out.println(line);

                        // Return to ledger.
                        ledger();
                    }
                }
                // If an error occurred, print error.
            } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger menu.
            ledger();
        }

        // Create the ledgerReports method.
        public static void ledgerReports() {
            // Reports Menu
            System.out.println("\n1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year to Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            // Prompt user to choose an option.
            System.out.print("Choose an option: ");
            reportInput = scanner.nextInt();
            scanner.nextLine();

            if (reportInput == 1) {
                // Call monthToDate method.
                monthToDate();
            } else if (reportInput == 2) {
                // Call previousMonth method.
                previousMonth();
            } else if (reportInput == 3) {
                // Call yearToDate method.
                yearToDate();
            } else if (reportInput == 4) {
                // Call previousYear method.
                previousYear();
            } else if (reportInput == 5) {
                // Call searchByVendor method.
                searchByVendor();
            } else if (reportInput == 6) {
                customSearch();
            }
            else if (reportInput == 0) {
                // Call ledgerReports method.
                ledgerReports();
                // If user entered a wrong input.
            } else {
                System.out.print("Invalid input. Please try again: ");
                reportInput = scanner.nextInt();
            }
        }
        // Create monthToDate method.
        public static void monthToDate() {
            // Get the current month and year.
            currentDate = LocalDate.now();
            currentMonth = currentDate.getMonthValue();
            currentYear = currentDate.getYear();

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                reader.readLine();

                // Set and initialize the variables.
                String line;
                found = false;

                // Print title.
                System.out.println("\nMonth to Date Transactions:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");
                    transDate = LocalDate.parse(parts[0].split("\\|")[0]);

                    // Check if the date is in the current month and year.
                    if (transDate.getMonthValue() == currentMonth && transDate.getYear() == currentYear) {
                        System.out.println(line);
                        found = true;
                    }
                }

                // If no transactions found, print message.
                if (!found) {
                    System.out.println("No transactions found for the current month.");
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger reports menu.
            ledgerReports();
        }

        // Create previousMonth method.
        public static void previousMonth() {
            // Get the date for the last month.
            currentDate = LocalDate.now();
            lastMonthDate = currentDate.minusMonths(1);
            previousMonth = lastMonthDate.getMonthValue();
            previousYear = lastMonthDate.getYear();

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                reader.readLine();

                // Set and initialize the variables.
                String line;
                found = false;

                // Print title.
                System.out.println("\nPrevious Month Results:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");
                    transDate = LocalDate.parse(parts[0].split("\\|")[0]);

                    // Check if the date is in the last month.
                    if (transDate.getMonthValue() == previousMonth && transDate.getYear() == previousYear) {
                        System.out.println(line);
                        found = true;
                    }
                }

                // If no transactions found, print message.
                if (!found) {
                    System.out.println("No transactions found for the last month.");
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger reports menu.
            ledgerReports();
        }

        // Create yearToDate method.
        public static void yearToDate() {
            // Get the current year.
            currentYear = LocalDate.now().getYear();

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                reader.readLine();

                // Set and initialize the variables.
                String line;
                found = false;

                // Print title.
                System.out.println("\nYear to Date Results:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");
                    transDate = LocalDate.parse(parts[0].split("\\|")[0]);

                    // Check if the date is in the current year.
                    if (transDate.getYear() == currentYear) {
                        System.out.println(line);
                        found = true;
                    }
                }

                // If no transactions found, print message.
                if (!found) {
                    System.out.println("No transactions found for the current year.");
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger reports menu.
            ledgerReports();
        }

        // Create previousYear method.
        public static void previousYear() {
            // Get the date for the previous year.
            currentDate = LocalDate.now();
            lastYearDate = currentDate.minusYears(1);
            previousYear = lastYearDate.getYear();

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                reader.readLine();

                // Set and initialize the variables.
                String line;
                found = false;

                // Print title.
                System.out.println("\nPrevious Year Results:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");
                    LocalDate transactionDate = LocalDate.parse(parts[0].split("\\|")[0]);

                    // Check if the date is in the previous year.
                    if (transactionDate.getYear() == previousYear) {
                        System.out.println(line);
                        found = true;
                    }
                }

                // If no transactions found, print message.
                if (!found) {
                    System.out.println("No transactions found for the previous year.");
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger reports menu.
            ledgerReports();
        }

        // Create searchByVendor method.
        public static void searchByVendor() {
            // Ask the user to enter the vendor to search for.
            System.out.print("Enter the vendor name to search for: ");
            searchVendor = scanner.nextLine().trim().toLowerCase();

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                reader.readLine();

                // Set and initialize the variables.
                String line;
                found = false;

                // Print title.
                System.out.println("\nSearch by Vendor Results:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");
                    vendor = parts[3].trim().toLowerCase();

                    // Check if the vendor matches the user's input.
                    if (vendor.equalsIgnoreCase(searchVendor)) {
                        System.out.println(line);
                        found = true;
                    }
                }

                // If no transactions found for the vendor, print message.
                if (!found) {
                    System.out.println("No transactions found for the vendor: " + searchVendor);
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger reports menu.
            ledgerReports();
        }

        public static void customSearch() {
            // Title.
            System.out.println("Enter search values for ledger entry properties (press Enter to skip):");

            // Ask the user for the start date.
            System.out.print("Start date (YYYY-MM-DD): ");
            String startDateInput = scanner.nextLine().trim();

            // If there was an input, set the value.
            if (!startDateInput.isEmpty()) {
                startDate = LocalDate.parse(startDateInput);
            }

            // Ask the user for the end date.
            System.out.print("End date (YYYY-MM-DD): ");
            String endDateInput = scanner.nextLine().trim();

            // Set value if there's an input
            if (!endDateInput.isEmpty()) {
                endDate = LocalDate.parse(endDateInput);
            }

            // Prompt the user to input the description.
            System.out.print("Description: ");
            String description = scanner.nextLine().trim();

            // Prompt the user to input the vendor.
            System.out.print("Vendor: ");
            String vendor = scanner.nextLine().trim();

            // Prompt the user to input the amount.
            System.out.print("Amount: ");
            String amountInput = scanner.nextLine().trim();

            // If there was an input, set the value.
            Double amount = null;
            if (!amountInput.isEmpty()) {
                amount = Double.parseDouble(amountInput);
            }

            try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))) {
                // Skip the first line.
                reader.readLine();

                // Set and initialize the variables.
                String line;
                found = false;

                // Print title.
                System.out.println("\nCustom Search Results:");

                // Read the lines in the CSV.
                while ((line = reader.readLine()) != null) {
                    // Split the information into parts.
                    String[] parts = line.split("\\|");
                    transDate = LocalDate.parse(parts[0].split("\\|")[0]);
                    transactionDesc = parts[2];
                    transactionVendor = parts[3];
                    transactionAmount = Double.parseDouble(parts[4]);

                    // Check if the transaction matches the search criteria.
                    match = startDate == null || !transDate.isBefore(startDate);
                    if (endDate != null && transDate.isAfter(endDate)) {
                        match = false;
                    }
                    if (!description.isEmpty() && !transactionDesc.contains(description)) {
                        match = false;
                    }
                    if (!vendor.isEmpty() && !transactionVendor.equalsIgnoreCase(vendor)) {
                        match = false;
                    }
                    if (amount != null && transactionAmount != amount) {
                        match = false;
                    }

                    // Print the transaction if it matches all the criteria.
                    if (match) {
                        System.out.println(line);
                        found = true;
                    }
                }

                // If no transactions found, print message.
                if (!found) {
                    System.out.println("No transactions found matching the search criteria.");
                }
                // If an error occurred, print error.
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }

            // Go back to ledger reports menu.
            ledgerReports();
        }
    }
