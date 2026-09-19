package org.example.dao;

import org.example.exception.ProductNotFoundException;
import org.example.model.Product;

import java.util.List;

public interface ProductDAOInterface {

    void addProduct(Product product);

    List<Product> getAllProduct();

    Product getProductById(int id) throws ProductNotFoundException;

    Product getProductBySku(String sku) throws ProductNotFoundException;

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);

    boolean updateQuantity(int id,int newQuantity);

}
