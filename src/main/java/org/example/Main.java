package org.example;

import org.example.dao.ProductDAO;
import org.example.dao.ProductDAOInterface;
import org.example.dao.TransactionDAO;
import org.example.exception.InsufficientStockException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Electronics;
import org.example.model.Grocery;
import org.example.model.Product;
import org.example.model.StockTransaction;
import org.example.service.InventoryService;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final ProductDAOInterface dao = new ProductDAO();
    private static final TransactionDAO transactionDAO = new TransactionDAO();
    private static final InventoryService service = new InventoryService(dao, transactionDAO);

    public static void main(String[] args) {

        boolean running=true;

        while(running){
            printMenu();
            int choice=checkInt("Enter a choice: ");
            sc.nextLine();
            switch (choice){
                case 1:
                   addProduct();
                   break;

                case 2:
                    viewAllProduct();
                    break;

                case 3:
                    searchProduct();
                    break;

                case 4:
                    updateProduct();
                    break;


                case 5:
                    deleteProduct();
                    break;

                case 6:
                    stockIn();
                    break;

                case 7:
                    stockOut();
                    break;

                case 8:
                    lowStockAlert();
                    break;

                case 9:
                    stockValueReport();
                    break;

                case 10:
                    transactionHistoy();
                    break;

                case 11:
                    running=false;
                    System.out.println("Exiting.........");
                    break;

                default:
                    System.out.println("Invalid choice please select between 1 to 11");
            }
        }

    }

    private static void addProduct(){
        System.out.println("Enter the category (Electronics/Grocery): ");
        String category=sc.nextLine().trim();

        String sku=checkNonEmptyLine("Sku: ");
        String Name=checkNonEmptyLine("Name: ");
        int quantity=checkPositivenumber("Quantity: ");
        double price=checkPositiveDouble("Price: ");

        Product product;

        if(category.equalsIgnoreCase("Electronics")){
            int warrantyMonths=checkInt("Warranty Month: ");
            sc.nextLine();
            product=new Electronics(sku,Name,quantity,price,warrantyMonths);
        }else if (category.equalsIgnoreCase("Grocery")){
            String expiryDate=checkNonEmptyLine("expiryDate: ");
            product= new Grocery(sku,Name,quantity,price,expiryDate);
        }else{
            System.out.println("Invalid category.Product not added");
            return;
        }

        dao.addProduct(product);
        System.out.println("Product added successfully");
    }

    private static void viewAllProduct(){
        List<Product> products= dao.getAllProduct();

        if(products.isEmpty()){
            System.out.println("No product found");
        }

        for(Product product:products){
            System.out.println(product);
        }
    }

    private static void searchProduct(){
        System.out.println("Search by (1) ID \n (2) SKU");
        int option =checkInt("");
        sc.nextLine();

        try{
            Product product;

            if(option==1){
                System.out.println("Enter id: ");
                int id=checkInt("ID: ");
                sc.nextLine();
                product= dao.getProductById(id);
            }else if(option==2){
                System.out.println("Enter SKU: ");
                String sku=checkNonEmptyLine("SKU: ");
                product= dao.getProductBySku(sku);
            }else{
                System.out.println("Invalid choice");
                return;
            }
            System.out.println(product);
        }catch(ProductNotFoundException e){
            System.out.println(e.getMessage());
        }
    }


    private static void updateProduct(){
       String sku=checkNonEmptyLine("Enter product SKU to update: ");


       try{

           Product existing= dao.getProductBySku(sku);
           System.out.println("Current: "+existing);


           double price=checkPositiveDouble("Enter updated price: ");
           existing.setPrice(price);

           boolean updated= dao.updateProduct(existing);
           System.out.println(updated ? "Product updated." : "Update failed");
       }catch (ProductNotFoundException e){
           System.out.println("Not found "+e.getMessage());
       }
    }

    private static void  deleteProduct(){
        String sku=checkNonEmptyLine("Enter SKU to delete product: ");


        boolean deleted=dao.deleteProduct(sku);
        System.out.println(deleted ? "Product deleted" : "Product not found with that SKU");
    }


    private static void stockIn(){
        int productid=checkInt("Enter product id: ");
        int quantity=checkInt("Enter quantity to add: ");
        sc.nextLine();

        try{
            service.stockIn(productid,quantity);
            System.out.println("Stock added sucessfully");
        }catch (ProductNotFoundException e){
            System.out.println("Error : "+ e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("Invalid input: "+e.getMessage());
        }
    }

    private static void stockOut(){
        int productid=checkInt("Enter product id: ");
        int quantity=checkInt("Enter quantity to delete: ");
        sc.nextLine();

        try{
            service.stockOut(productid,quantity);
            System.out.println("Stock removed successfully");
        }catch (ProductNotFoundException e){
            System.out.println("Error: "+e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("Invalid input: "+e.getMessage());
        }catch (InsufficientStockException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private static void lowStockAlert(){
        List <Product> lowstock= service.getLowStockProducts();

        if(lowstock.isEmpty()){
            System.out.println("Every product are adequately stocked");
            return;
        }
        System.out.println("Low stock product");

        for(Product product: lowstock){
            System.out.println(product);
        }
    }

    private static void stockValueReport(){
        Map<String,Double> valueBycategory= service.getStockValueByCategory();

        if(valueBycategory.isEmpty()){
            System.out.println("No stock data available");
            return;
        }
        System.out.println("Stock value by category: ");
        for(Map.Entry<String, Double> entry: valueBycategory.entrySet()){
            System.out.printf("%-15s $%.2f%n", entry.getKey(),entry.getValue());
        }
    }


    private static void transactionHistoy(){
        List<StockTransaction> transaction=transactionDAO.getAllTransactions();

        if(transaction.isEmpty()){
            System.out.println("No transaction history");
            return;
        }
        for(StockTransaction transactions: transaction){
            System.out.println(transactions);
        }
    }


    private static void printMenu() {
        System.out.println("===== Inventory Management System =====");
        System.out.println("1.  Add Product");
        System.out.println("2.  View All Products");
        System.out.println("3.  Search Product");
        System.out.println("4.  Update Product");
        System.out.println("5.  Delete Product");
        System.out.println("6.  Stock In");
        System.out.println("7.  Stock Out");
        System.out.println("8.  Low Stock Alert");
        System.out.println("9.  Stock Value Report by Category");
        System.out.println("10. Transaction History");
        System.out.println("11. Exit");
        System.out.println("=========================================");

    }


    private static int checkInt(String value){
        while(true){
            System.out.println(value);

            try{
                return sc.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid whole number");
                sc.nextLine();
            }
        }
    }

    private static int checkPositivenumber(String value){
        while (true){
            int num=checkInt(value);
            sc.nextLine();

            if(num>0){
                return num;
            }else{
                System.out.println("number must be greater than 0");
            }
        }

    }

    private static double checkPositiveDouble(String value){
        while(true){
            System.out.println(value);

            try{
                double num= sc.nextDouble();
                sc.nextLine();
                if(num>0){
                    return num;
                }
                System.out.println("Value must be greater than 0");
            }catch(InputMismatchException e){
                System.out.println("Please enter valid number");
                sc.nextLine();
            }
        }
    }

    private static String checkNonEmptyLine(String value){
        while(true){
            System.out.println(value);
            String str=sc.nextLine().trim();
            if(!str.isEmpty()){
                return str;
            }
            System.out.println("This field cannot be empty");
        }
    }



}