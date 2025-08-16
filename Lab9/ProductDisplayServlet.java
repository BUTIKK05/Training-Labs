package com.example.servlets;

import com.example.beans.Product;
import com.example.util.DatabaseUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ProductDisplayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String warehouseId = request.getParameter("warehouseId");
        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT name, quantity FROM products WHERE warehouse_id = ?")) {
            stmt.setInt(1, Integer.parseInt(warehouseId));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(rs.getString("name"), rs.getInt("quantity")));
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении товаров", e);
        }

        request.setAttribute("products", products);
        request.getRequestDispatcher("viewProducts.jsp").forward(request, response);
    }

}
