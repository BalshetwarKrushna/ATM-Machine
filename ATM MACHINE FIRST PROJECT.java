import java.util.Scanner;

// Base Class
class BankAccount {

    // Static (shared)
    private static String bankName = "State Bank of India";
    private static int accountCounter = 1000;

    // Encapsulated data
    private String name;
    private String parentName;
    private String address;
    private int age;
    private int accountNumber;
    private int pin;
    protected double balance;

    // Security flag (IMPORTANT)
    private boolean isAuthenticated = false;

    // Constructor
    public BankAccount(String name, String parentName, String address, int age, int pin) {
        this.name = name;
        this.parentName = parentName;
        this.address = address;
        this.age = age;
        this.pin = pin;
        this.accountNumber = ++accountCounter;
        this.balance = 45000;
    }

    // Static method
    public static void showBankInfo() {
        System.out.println("Welcome to " + bankName);
    }

    // 🔐 LOGIN (Authentication)
    public boolean login(int inputPin) {
        if (this.pin == inputPin) {
            isAuthenticated = true;
            System.out.println("Login successful");
            return true;
        } else {
            System.out.println("Invalid PIN");
            return false;
        }
    }

    // 🔐 LOGOUT
    public void logout() {
        isAuthenticated = false;
        System.out.println("Logged out successfully");
    }

    // 🔐 Internal security check
    private boolean checkAccess() {
        if (!isAuthenticated) {
            System.out.println("Access denied! Please login first.");
            return false;
        }
        return true;
    }

    // 🔐 SECURE DISPLAY
    public void displayDetails() {
        if (!checkAccess()) return;

        if (age > 18) {
            System.out.println("\n--- Account Details ---");
            System.out.println("Bank: " + bankName);
            System.out.println("Account No: " + accountNumber);
            System.out.println("Name: " + name);
            System.out.println("Parent: " + parentName);
            System.out.println("Age: " + age);
            System.out.println("Address: " + address);
        } else {
            System.out.println("Invalid age (must be >18)");
        }
    }

    // 🔐 SECURE DEPOSIT
    public void deposit(double amount) {
        if (!checkAccess()) return;

        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
            System.out.println("Balance: " + balance);
        } else {
            System.out.println("Invalid amount");
        }
    }

    // 🔐 SECURE WITHDRAW (can be overridden)
    public void withdraw(double amount) {
        if (!checkAccess()) return;

        if (amount <= balance && amount > 0) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
            System.out.println("Balance: " + balance);
        } else {
            System.out.println("Invalid or insufficient balance");
        }
    }

    // 🔐 SECURE BALANCE CHECK
    public void checkBalance() {
        if (!checkAccess()) return;

        System.out.println("Current Balance: " + balance);
    }
}

// Derived Class (Polymorphism)
class SavingsAccount extends BankAccount {

    public SavingsAccount(String name, String parentName, String address, int age, int pin) {
        super(name, parentName, address, age, pin);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 10000) {
            System.out.println("Savings limit exceeded (max 10000)");
        } else {
            super.withdraw(amount);
        }
    }
}

// Main Class
public class SecureBankApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankAccount.showBankInfo();

        // Account creation
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Parent Name: ");
        String parent = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();

        System.out.print("Set PIN: ");
        int pin = sc.nextInt();

        BankAccount account = new SavingsAccount(name, parent, address, age, pin);

        int choice, ch;

        do {
            System.out.println("\n1. Login");
            System.out.println("2. Display Details");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Check Balance");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter PIN: ");
                    int inputPin = sc.nextInt();
                    account.login(inputPin);
                    break;

                case 2:
                    account.displayDetails();
                    break;

                case 3:
                    System.out.print("Enter amount: ");
                    account.deposit(sc.nextDouble());
                    break;

                case 4:
                    System.out.print("Enter amount: ");
                    account.withdraw(sc.nextDouble());
                    break;

                case 5:
                    account.checkBalance();
                    break;

                case 6:
                    account.logout();
                    break;

                default:
                    System.out.println("Invalid choice");
            }

            System.out.print("\n1. Continue  2. Exit: ");
            ch = sc.nextInt();

        } while (ch == 1);

        System.out.println("Thank you!");
    }
}
