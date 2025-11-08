package com.bankapp.model;
/**
 * BankAccount class represents a single bank account with basic operations
 * Uses encapsulation with private fields and public getters/setters
 */
public class BankAccount {
    private String accountNumber;
    private String name;
    private double balance;
    
    // Constructor
    public BankAccount(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public double getBalance() {
        return balance;
    }
    
    // Setters
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    /**
     * Deposit money into the account
     * @param amount - amount to deposit
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(double amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        this.balance += amount;
    }
    
    /**
     * Withdraw money from the account
     * @param amount - amount to withdraw
     * @throws InsufficientBalanceException if insufficient balance
     * @throws IllegalArgumentException if amount is negative
     */
    public void withdraw(double amount) throws InsufficientBalanceException, IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }
        if (amount > this.balance) {
            throw new InsufficientBalanceException("Insufficient balance. Available: $" + this.balance);
        }
        this.balance -= amount;
    }
    
    /**
     * Calculate interest on the account balance
     * @param rate - interest rate (as percentage, e.g., 5.0 for 5%)
     * @return interest amount
     */
    public double calculateInterest(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        return (this.balance * rate) / 100.0;
    }
    
    /**
     * Apply interest to the account balance
     * @param rate - interest rate (as percentage)
     */
    public void applyInterest(double rate) {
        double interest = calculateInterest(rate);
        this.balance += interest;
    }
    
    /**
     * Convert account to CSV format for file storage
     * @return CSV string representation
     */
    public String toCSV() {
        return accountNumber + "," + name + "," + balance;
    }
    
    /**
     * Create BankAccount from CSV string
     * @param csvLine - CSV formatted string
     * @return BankAccount object
     */
    public static BankAccount fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid CSV format");
        }
        String accountNumber = parts[0].trim();
        String name = parts[1].trim();
        double balance = Double.parseDouble(parts[2].trim());
        return new BankAccount(accountNumber, name, balance);
    }
    
    @Override
    public String toString() {
        return String.format("Account: %s | Name: %s | Balance: $%.2f", 
                           accountNumber, name, balance);
    }
}
