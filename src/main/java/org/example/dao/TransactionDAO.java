package org.example.dao;

import org.example.model.StockTransaction;
import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void recordTransaction(StockTransaction transaction) {
        String sql = "INSERT INTO stock_transactions (product_id, type, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getProductID());
            stmt.setString(2, transaction.getType());
            stmt.setInt(3, transaction.getQuantity());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to record transaction: " + e.getMessage(), e);
        }
    }

    public List<StockTransaction> getTransactionsByProductId(int productId) {
        String sql = "SELECT * FROM stock_transactions WHERE product_id = ? ORDER BY transaction_date DESC";
        List<StockTransaction> transactions = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch transactions for product " + productId + ": " + e.getMessage(), e);
        }

        return transactions;
    }

    public List<StockTransaction> getAllTransactions() {
        String sql = "SELECT * FROM stock_transactions ORDER BY transaction_date DESC";
        List<StockTransaction> transactions = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all transactions: " + e.getMessage(), e);
        }

        return transactions;
    }


    private StockTransaction mapRowToTransaction(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int product_id = rs.getInt("productid");
        String type = rs.getString("type");
        int quantity = rs.getInt("quantity");
        Timestamp transactionDate = rs.getTimestamp("transaction_date");

        return new StockTransaction(
                id,
                product_id,
                type,
                quantity,
                transactionDate != null ? LocalDateTime.parse(transactionDate.toLocalDateTime().toString()) : null
        );
    }
}