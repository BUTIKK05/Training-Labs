package com.example.servlets;

import com.example.beans.Warehouse;
import com.example.util.DatabaseUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class WarehouseSelectionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Warehouse> warehouses = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM warehouses");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                warehouses.add(new Warehouse(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении списка складов", e);
        }

        request.setAttribute("warehouses", warehouses);
        request.getRequestDispatcher("selectWarehouse.jsp").forward(request, response);
    }
}
