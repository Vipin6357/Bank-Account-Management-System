package com.bankapp.model;
import java.io.*;
import java.util.*;

/**
 * Bank class manages multiple bank accounts using ArrayList
 * Handles file operations for persistence
 */
public class Bank {
    private ArrayList<BankAccount> accounts;
    private String bankname;
    private int nextAccountNumber;
    
    /**
     * Constructor initializes the bank with empty account list
     * @param filename - name of the file to store account data
     */
    public Bank(String bankname) {
        this.accounts = new ArrayList<>();
        this.bankname = bankname;
        this.nextAccountNumber = 1001; // Starting account number
        loadAccountsFromFile();
    }
    
    /**
     * Create a new bank account with auto-generated account number
     * @param name - account holder's name
     * @param initialBalance - initial deposit amount
     * @return the created BankAccount object
     */
    public BankAccount createAccount(String name, double initialBalance) {
        String accountNumber = String.format("ACC%04d", nextAccountNumber++);
        BankAccount newAccount = new BankAccount(accountNumber, name, initialBalance);
        accounts.add(newAccount);
        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
        return newAccount;
    }
    
    /**
     * Find account by account number
     * @param accountNumber - the account number to search for
     * @return BankAccount object if found, null otherwise
     */
    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    /**
     * Deposit money into an account
     * @param accountNumber - target account number
     * @param amount - amount to deposit
     * @return true if successful, false if account not found
     */
    public boolean deposit(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return false;
        }
        
        try {
            account.deposit(amount);
            System.out.printf("Deposit successful! New balance: $%.2f%n", account.getBalance());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Withdraw money from an account
     * @param accountNumber - target account number
     * @param amount - amount to withdraw
     * @return true if successful, false if account not found or insufficient balance
     */
    public boolean withdraw(String accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return false;
        }
        
        try {
            account.withdraw(amount);
            System.out.printf("Withdrawal successful! New balance: $%.2f%n", account.getBalance());
            return true;
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check balance of an account
     * @param accountNumber - target account number
     * @return account balance, -1 if account not found
     */
    public double checkBalance(String accountNumber) {
        BankAccount account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return -1;
        }
        
        System.out.printf("Account Balance: $%.2f%n", account.getBalance());
        return account.getBalance();
    }
    
    /**
     * Display all account details
     */
    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }
        
        System.out.println("\n=== ALL ACCOUNTS ===");
        System.out.println("Account Number | Account Holder | Balance");
        System.out.println("----------------------------------------");
        
        for (BankAccount account : accounts) {
            System.out.printf("%-14s | %-13s | $%.2f%n", 
                            account.getAccountNumber(), 
                            account.getName(), 
                            account.getBalance());
        }
        System.out.println("----------------------------------------");
        System.out.println("Total Accounts: " + accounts.size());
    }
    
    /**
     * Apply interest to all accounts
     * @param rate - interest rate (as percentage)
     */
    public void applyInterestToAllAccounts(double rate) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts to apply interest to!");
            return;
        }
        
        System.out.printf("Applying %.2f%% interest to all accounts...%n", rate);
        for (BankAccount account : accounts) {
            double oldBalance = account.getBalance();
            account.applyInterest(rate);
            double interestEarned = account.getBalance() - oldBalance;
            System.out.printf("Account %s: Interest earned: $%.2f, New balance: $%.2f%n", 
                            account.getAccountNumber(), interestEarned, account.getBalance());
        }
    }
    
    /**
     * Save all accounts to file in CSV format
     */
    public void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bankname))) {
            // Write header
            writer.write("AccountNumber,Name,Balance");
            writer.newLine();
            
            // Write account data
            for (BankAccount account : accounts) {
                writer.write(account.toCSV());
                writer.newLine();
            }
            
            System.out.println("Accounts saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving accounts to file: " + e.getMessage());
        }
    }
    
    /**
     * Load accounts from file in CSV format
     */
    public void loadAccountsFromFile() {
        File file = new File(bankname);
        if (!file.exists()) {
            System.out.println("No existing account file found. Starting with empty account list.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(bankname))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                try {
                    BankAccount account = BankAccount.fromCSV(line);
                    accounts.add(account);
                    
                    // Update next account number based on loaded accounts
                    String accNum = account.getAccountNumber();
                    if (accNum.startsWith("ACC")) {
                        try {
                            int num = Integer.parseInt(accNum.substring(3));
                            if (num >= nextAccountNumber) {
                                nextAccountNumber = num + 1;
                            }
                        } catch (NumberFormatException e) {
                            // Ignore if account number format is unexpected
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error loading account from line: " + line);
                    System.out.println("Error: " + e.getMessage());
                }
            }
            
            System.out.println("Accounts loaded from file successfully!");
            System.out.println("Loaded " + accounts.size() + " accounts.");
            
        } catch (IOException e) {
            System.out.println("Error loading accounts from file: " + e.getMessage());
        }
    }
    
    /**
     * Get total number of accounts
     * @return number of accounts
     */
    public int getAccountCount() {
        return accounts.size();
    }
    
    /**
     * Get all accounts (for external access if needed)
     * @return ArrayList of BankAccount objects
     */
    public ArrayList<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts); // Return a copy to maintain encapsulation
    }
}
