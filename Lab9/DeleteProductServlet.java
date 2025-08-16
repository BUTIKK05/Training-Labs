package com.example.servlets;

import com.example.util.DatabaseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class DeleteProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String warehouseId = request.getParameter("warehouseId");
        String productName = request.getParameter("productName");

        if (warehouseId == null || productName == null || productName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM products WHERE warehouse_id = ? AND name = ?")) {
            stmt.setInt(1, Integer.parseInt(warehouseId));
            stmt.setString(2, productName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Ошибка при удалении товара", e);
        }

        request.setAttribute("warehouseId", warehouseId);
        request.getRequestDispatcher("viewProducts").forward(request, response);
    }
}
