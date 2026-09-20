package org.example.dao;

import org.example.exception.ProductNotFoundException;
import org.example.model.Electronics;
import org.example.model.Grocery;
import org.example.model.Product;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements ProductDAOInterface {

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT into products(sku,name,quantity,price,category,warranty_months,expiry_date)values(?,?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getSku());
            stmt.setString(2, product.getName());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getCategory());

            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                stmt.setInt(6, electronics.getWarranty_months());
                stmt.setNull(7, Types.DATE);
            } else if (product instanceof Grocery) {
                Grocery grocery = (Grocery) product;
                stmt.setNull(6, Types.INTEGER);
                stmt.setString(7, grocery.getExpiry_date());
            }

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Failde to add product" + e.getMessage(), e);
        }
    }

        @Override
                public List<Product> getAllProduct(){
                String sql="Select * from products";
                List <Product> products=new ArrayList<>();

                try(Connection conn=DBConnection.getConnection();
                PreparedStatement stmt=conn.prepareStatement(sql)){

                    ResultSet rs= stmt.executeQuery();

                    while (rs.next()){
                        products.add(mapRowToProduct(rs));
                }
        } catch (SQLException e) {
                    throw new RuntimeException("Failed to fetch product: "+e.getMessage(),e);

                }
                return products;
    }


    @Override
    public  Product getProductById(int id) throws ProductNotFoundException{
        String sql="Select * from products WHERE id=?";

        try(Connection conn= DBConnection.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            try(ResultSet rs=stmt.executeQuery()){
                if(rs.next()){
                    return  mapRowToProduct(rs);
                }
                else{
                    throw new ProductNotFoundException("No Product found with id: "+id);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Failed to fetch product by id: "+ e.getMessage(),e);
        }
    }

    @Override
    public Product getProductBySku(String sku) throws ProductNotFoundException{
        String sql="Select * from products WHERE sku=?";

        try(Connection conn=DBConnection.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setString(1,sku);

            try(ResultSet rs=stmt.executeQuery()){
                if(rs.next()){
                    return mapRowToProduct(rs);
                }
                else{
                    throw new ProductNotFoundException("No product found with sku: "+ sku);
                }
            }

        }catch(SQLException e){
            throw new RuntimeException("Failed to fetch product by sku"+ e.getMessage(),e);
        }
    }


    @Override
    public boolean updateProduct(Product product){
        String sql="UPDATE products Set name=?, price=? WHERE id=?";

                try(Connection conn=DBConnection.getConnection();
                 PreparedStatement stmt=conn.prepareStatement(sql)){

                    stmt.setString(1,product.getName());
                    stmt.setDouble(2,product.getPrice());
                    stmt.setInt(3,product.getId());

                    int rowsaffected= stmt.executeUpdate();
                    return rowsaffected >0;

                }
                catch(SQLException e){
                    throw new RuntimeException("Failed to update message "+e.getMessage(),e);
                }

    }


    @Override
    public boolean deleteProduct(int id){
        String sql="DELETE from products WHERE id=?";

        try(Connection conn=DBConnection.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,id);

            int rowsaffected=stmt.executeUpdate();
            return rowsaffected>0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete the product : "+ e.getMessage(),e);
        }
    }


    @Override
    public boolean updateQuantity(int id,int newQuantity){
        String sql="Update products SET quantity=? WHERE id=?";

        try(Connection conn=DBConnection.getConnection();
        PreparedStatement stmt=conn.prepareStatement(sql)){

            stmt.setInt(1,newQuantity);
            stmt.setInt(2,id);

            int rowsaffected=stmt.executeUpdate();
            return rowsaffected>0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed ot update quantity: "+e.getMessage(),e);
        }
    }
    

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String sku = rs.getString("sku");
        String name = rs.getString("name");
        int quantity = rs.getInt("quantity");
        double price = rs.getDouble("price");
        String category = rs.getString("category");

        if ("Electronics".equalsIgnoreCase(category)) {
            int warrantyMonths = rs.getInt("warranty_months");
            return new Electronics(id, sku, name, quantity, price, warrantyMonths);

        } else if ("Grocery".equalsIgnoreCase(category)) {
            java.sql.Date expiry = rs.getDate("expiry_date");
            String expiry_date = (expiry != null) ? expiry.toLocalDate().toString() : null;
            return new Grocery(id, sku, name, quantity, price, expiry_date);

        } else {
            throw new IllegalStateException("Unknown product category in database: " + category);
        }
    }


}
