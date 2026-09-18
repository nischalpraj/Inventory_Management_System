package org.example.model;

import java.time.LocalDateTime;

public class StockTransaction {
    private int id;
    private int productId;
    private String type;
    private int quantity;
    private LocalDateTime transactionDate;


    public StockTransaction(int id,int productId,String type,int quantity,LocalDateTime transactionDate){
        this.id=id;
        this.productId=productId;
        this.type=type;
        this.quantity=quantity;
        this.transactionDate=transactionDate;
    }

    public StockTransaction(int productId,String type,int quantity,LocalDateTime transactionDate){
        this.productId=productId;
        this.type=type;
        this.quantity=quantity;
        this.transactionDate=transactionDate;
    }

    public int getId(){
        return  id;
    }

    public int getProductId(){
        return productId;
    }

    public String type(){
        return type;
    }

    public int getQuantity(){
        return quantity;
    }

    public LocalDateTime getTransactionDate(){
        return transactionDate;
    }


    @Override
    public String toString() {
        return "[" + id + "] Product ID: " + productId +
                " - Type: " + type +
                " - Quantity: " + quantity +
                " - Date: " + transactionDate;
    }


}
