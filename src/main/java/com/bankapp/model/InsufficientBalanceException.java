package com.bankapp.model;
/**
 * Custom exception class for insufficient balance scenarios
 * Extends Exception to create a checked exception
 */
public class InsufficientBalanceException extends Exception {
    
    /**
     * Constructor with message
     * @param message - error message describing the insufficient balance issue
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * @param message - error message
     * @param cause - the underlying cause of this exception
     */
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
