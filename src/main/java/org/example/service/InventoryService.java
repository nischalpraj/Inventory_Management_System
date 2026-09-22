package org.example.service;

import org.example.dao.ProductDAOInterface;
import org.example.dao.TransactionDAO;
import org.example.exception.InsufficientStockException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Product;
import org.example.model.StockTransaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {

    private final ProductDAOInterface productDAO;
    private final TransactionDAO transactionDAO;

    public InventoryService(ProductDAOInterface productDAO, TransactionDAO transactionDAO) {
        this.productDAO = productDAO;
        this.transactionDAO = transactionDAO;
    }


    public void stockIn(int productid, int quantity) throws ProductNotFoundException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Stock-in quantity must be greater than zero.");
        }

        Product product = productDAO.getProductById(productid);

        int newQuantity = product.getQuantity() + quantity;
        productDAO.updateQuantity(productid, newQuantity);

        StockTransaction transaction = new StockTransaction(productid, "IN", quantity, null);
        transactionDAO.recordTransaction(transaction);
    }


    public void stockOut(int productid, int quantity) throws ProductNotFoundException, InsufficientStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Stock-out quantity must be greater than zero.");
        }

        Product product = productDAO.getProductById(productid);

        if (quantity > product.getQuantity()) {
            throw new InsufficientStockException(
                    "Cannot remove " + quantity + " units of " + product.getName() +
                            "; only " + product.getQuantity() + " in stock."
            );
        }

        int newQuantity = product.getQuantity() - quantity;
        productDAO.updateQuantity(productid, newQuantity);

        StockTransaction transaction = new StockTransaction(productid, "OUT", quantity, null);
        transactionDAO.recordTransaction(transaction);
    }


    public List<Product> getLowStockProducts() {
        List<Product> allProducts = productDAO.getAllProduct();
        List<Product> lowStock = new ArrayList<>();

        for (Product product : allProducts) {
            if (product.getQuantity() < product.getRestockThreshold()) {
                lowStock.add(product);
            }
        }

        return lowStock;
    }

    public Map<String,Double> getStockValueByCategory(){
        List<Product> allProduct=productDAO.getAllProduct();
        Map<String,Double> valueByCategory=new HashMap<>();

        for(Product product : allProduct){
            String category=product.getCategory();
            double value=product.getQuantity() * product.getPrice();

            valueByCategory.merge(category,value, Double :: sum);
        }

        return  valueByCategory;

    }

    public Map<Integer, Product> buildProductLookup(){

        List<Product> allProduct=productDAO.getAllProduct();
        Map<Integer, Product> lookup= new HashMap<>();

        for(Product product: allProduct){
            lookup.put(product.getId(),product);
        }
            return lookup;
    }

    public List<Product> getProductSortedByValue(){
        List<Product> allProduct=productDAO.getAllProduct();

        allProduct.sort(
                Comparator.comparingDouble((Product p) -> p.getQuantity() * p.getPrice()).reversed()
        );

        return  allProduct;
    }



}
