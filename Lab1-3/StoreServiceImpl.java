package org.example.lab3_ds_v5;

import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class StoreServiceImpl extends UnicastRemoteObject implements StoreService {
    private final Connection conn;

    public StoreServiceImpl() throws Exception {
        this.conn = DriverManager.getConnection("jdbc:sqlite:store.db");
        initDB();
    }

    private void initDB() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS warehouse (
                    id TEXT PRIMARY KEY,
                    name TEXT UNIQUE NOT NULL
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS product (
                    id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    price REAL NOT NULL,
                    warehouse_id TEXT NOT NULL,
                    FOREIGN KEY (warehouse_id) REFERENCES warehouse(id) ON DELETE CASCADE
                )
            """);
        }
    }

    public void addWarehouse(String warehouseName) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO warehouse (id, name) VALUES (?, ?)")) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, warehouseName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления склада: " + e.getMessage());
        }
    }

    public void removeWarehouse(String warehouseName) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM warehouse WHERE LOWER(name) = LOWER(?)")) {
            stmt.setString(1, warehouseName);
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new RuntimeException("Склад не найден");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления склада: " + e.getMessage());
        }
    }

    public void addProduct(String warehouseName, String name, double price) {
        try (PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO product (id, name, price, warehouse_id)
            VALUES (?, ?, ?, (SELECT id FROM warehouse WHERE LOWER(name) = LOWER(?)))
        """)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.setString(4, warehouseName);
            int updated = stmt.executeUpdate();
            if (updated == 0) throw new RuntimeException("Склад не найден");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка добавления товара: " + e.getMessage());
        }
    }

    public void removeProduct(String warehouseName, String name) {
        try (PreparedStatement stmt = conn.prepareStatement("""
            DELETE FROM product 
            WHERE LOWER(name) = LOWER(?) AND warehouse_id = (
                SELECT id FROM warehouse WHERE LOWER(name) = LOWER(?)
            )
        """)) {
            stmt.setString(1, name);
            stmt.setString(2, warehouseName);
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new RuntimeException("Товар или склад не найден");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления товара: " + e.getMessage());
        }
    }

    public void discountProduct(String productName, double percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("Некорректный процент");

        try (PreparedStatement stmt = conn.prepareStatement("""
            UPDATE product 
            SET price = price * (1 - ? / 100.0)
            WHERE LOWER(name) = LOWER(?)
        """)) {
            stmt.setDouble(1, percent);
            stmt.setString(2, productName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка применения скидки: " + e.getMessage());
        }
    }

    public List<Product> getProductsByWarehouseName(String warehouseName) {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT p.name, p.price, w.name
            FROM product p
            JOIN warehouse w ON p.warehouse_id = w.id
            WHERE LOWER(w.name) = LOWER(?)
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, warehouseName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getString(1),
                            rs.getDouble(2),
                            rs.getString(3)
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении товаров: " + e.getMessage());
        }
        return list;
    }
}
