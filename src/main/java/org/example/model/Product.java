package org.example.model;

public abstract class Product {
    private  int id;
    private String sku;
    private String name;
    private int quantity;
    private double price;

    public  Product(int id,String sku, String name, int quantity,double price){
        this.id=id;
        this.name=name;
        this.sku=sku;
        this.quantity=quantity;
        this.price=price;
    }

    public Product(String sku,String name,int quantity,double price){
        this.sku=sku;
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

    public String getSku(){
        return sku;
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

    public abstract String getCategory();

    @Override
    public String toString(){
        return "[" + id + "] " + name +
                " - Qty: " + quantity +
                " - $" + price;
    }



}
