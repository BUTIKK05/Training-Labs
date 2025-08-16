package com.example.servlets;

import com.example.util.DatabaseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String warehouseId = request.getParameter("warehouseId");
        String productName = request.getParameter("productName");
        String quantityStr = request.getParameter("quantity");

        if (warehouseId == null || productName == null || quantityStr == null || productName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO products (warehouse_id, name, quantity) VALUES (?, ?, ?)")) {
            stmt.setInt(1, Integer.parseInt(warehouseId));
            stmt.setString(2, productName);
            stmt.setInt(3, Integer.parseInt(quantityStr));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Ошибка при добавлении товара", e);
        }

        request.setAttribute("warehouseId", warehouseId);
        request.getRequestDispatcher("viewProducts").forward(request, response);
    }
}
