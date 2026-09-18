package org.example.model;

public class Grocery extends Product{

    private int expiry_date;

    public Grocery(int id, String sku,String name, int quantity, double price,int expiry_date){
        super(id,sku,name,quantity,price);
        this.expiry_date=expiry_date;
    }

    public Grocery(String sku,String name,int quanity,double price,int expiry_date){
        super(sku,name,quanity,price);
        this.expiry_date=expiry_date;
    }

    public int getExpiry_date(){
        return expiry_date;
    }

    @Override
    public int getRestockThreshold(){
        return 20;
    }

    @Override
    public String getCategory(){
        return "Grocery";
    }


    @Override
    public String toString(){
        return super.toString()+" -Expiry Date:"+ expiry_date;
    }
}
