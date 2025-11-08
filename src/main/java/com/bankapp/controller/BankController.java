package com.bankapp.controller;

import com.bankapp.model.Bank;
import com.bankapp.model.BankAccount;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    // Create Bank instance with name
    private final Bank bank = new Bank("VipinBank");

    // Create new account
    @PostMapping("/create")
    public BankAccount createAccount(@RequestParam String name, @RequestParam double initialBalance) {
        return bank.createAccount(name, initialBalance);
    }

    // Deposit money
    @PostMapping("/deposit")
    public String deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        boolean success = bank.deposit(accountNumber, amount);
        return success ? "Deposit successful!" : "Deposit failed!";
    }

    // Withdraw money
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber, @RequestParam double amount) {
        boolean success = bank.withdraw(accountNumber, amount);
        return success ? "Withdrawal successful!" : "Withdrawal failed!";
    }

    // Check balance
    @GetMapping("/balance")
    public String checkBalance(@RequestParam String accountNumber) {
        double balance = bank.checkBalance(accountNumber);
        return balance >= 0 ? "Balance: $" + balance : "Account not found!";
    }

    // List all accounts
    @GetMapping("/all")
    public ArrayList<BankAccount> getAllAccounts() {
        return bank.getAllAccounts();
    }
}
