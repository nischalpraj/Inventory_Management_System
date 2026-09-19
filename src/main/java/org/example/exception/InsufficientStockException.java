package org.example.exception;

public class InsufficientStockException extends Exception {

    public InsufficientStockException(String message){
        super(message);
    }

    public InsufficientStockException(String message,Throwable cause){
        super(message,cause);
    }
}
