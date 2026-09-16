package org.example;

public abstract class Product {
    private  int id;
    private String name;
    private int quantity;
    private double price;

    public  Product(int id, String name, int quantity,double price){
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }

    public Product(String name,int quantity,double price){
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public abstract int getRestockThreshold();

    @Override
    public String toString(){
        return "[" + id + "] " + name +
                " - Qty: " + quantity +
                " - $" + price;
    }



}
