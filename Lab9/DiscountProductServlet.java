package com.example.servlets;

import com.example.util.DatabaseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class DiscountProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String warehouseId = request.getParameter("warehouseId");
        String productName = request.getParameter("productName");
        String discountPercentStr = request.getParameter("discountPercent");

        if (warehouseId == null || productName == null || discountPercentStr == null ||
                productName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные");
            return;
        }

        int discountPercent;
        try {
            discountPercent = Integer.parseInt(discountPercentStr);
            if (discountPercent < 0 || discountPercent > 100) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Процент должен быть от 0 до 100");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный процент");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            int currentQuantity = 0;
            try (PreparedStatement selectStmt = conn.prepareStatement(
                    "SELECT quantity FROM products WHERE warehouse_id = ? AND name = ?")) {
                selectStmt.setInt(1, Integer.parseInt(warehouseId));
                selectStmt.setString(2, productName);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        currentQuantity = rs.getInt("quantity");
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Товар не найден");
                        return;
                    }
                }
            }

            int newQuantity = currentQuantity - (currentQuantity * discountPercent / 100);
            if (newQuantity < 0) newQuantity = 0;

            try (PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE products SET quantity = ? WHERE warehouse_id = ? AND name = ?")) {
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, Integer.parseInt(warehouseId));
                updateStmt.setString(3, productName);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка при уценении товара", e);
        }

        request.setAttribute("warehouseId", warehouseId);
        request.getRequestDispatcher("viewProducts").forward(request, response);
    }
}
