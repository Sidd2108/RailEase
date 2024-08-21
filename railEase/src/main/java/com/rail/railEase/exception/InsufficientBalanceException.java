package com.rail.railEase.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String s) {
        super(s);
    }
}
