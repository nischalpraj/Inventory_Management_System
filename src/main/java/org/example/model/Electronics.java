package org.example.model;

public class Electronics extends Product {

    private int warranty_months;

    public Electronics(int id, String sku,String name, int quantity, double price, int warranty_months) {
        super(id,sku, name, quantity, price);
        this.warranty_months = warranty_months;
    }

    public Electronics(String sku,String name,int quantity,double price, int warranty_months){
        super(sku,name,quantity,price);
        this.warranty_months=warranty_months;
    }

    public int getWarranty_months() {
        return warranty_months;
    }

    @Override
    public int getRestockThreshold() {
        return 5;
    }

    @Override
    public String getCategory(){
        return "Electronics";
    }

    @Override
    public String toString() {
        return super.toString() +
                " - Warranty: " + warranty_months + " months";
    }
}
