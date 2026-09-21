package org.example;

import org.example.dao.ProductDAO;
import org.example.dao.ProductDAOInterface;
import org.example.dao.TransactionDAO;
import org.example.service.InventoryService;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

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

                default:
                    System.out.println("Invalid choice please select between 1 to 11");
            }
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
                return value;
            }
            System.out.println("This field cannot be empty");
        }
    }


}