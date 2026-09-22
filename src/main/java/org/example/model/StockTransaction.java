package org.example.model;

import java.time.LocalDateTime;

public class StockTransaction {
    private int id;
    private int productid;
    private String type;
    private int quantity;
    private LocalDateTime transactionDate;


    public StockTransaction(int id, int productid,String type,int quantity,LocalDateTime transactionDate){
        this.id=id;
        this.productid=productid;
        this.type=type;
        this.quantity=quantity;
        this.transactionDate=transactionDate;
    }

    public StockTransaction(int productid,String type,int quantity,LocalDateTime transactionDate){
        this.productid=productid;
        this.type=type;
        this.quantity=quantity;
        this.transactionDate=transactionDate;
    }

    public int getId(){
        return  id;
    }

    public int getProductID(){
        return productid;
    }

    public String getType(){
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
        return "[" + id + "] Product ID: " + productid +
                " - Type: " + type +
                " - Quantity: " + quantity +
                " - Date: " + transactionDate;
    }


}
