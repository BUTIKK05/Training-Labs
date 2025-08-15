package com.example.lab7;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Step3Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String weapon = req.getParameter("weapon");
        if (weapon == null || weapon.trim().isEmpty()) {
            resp.sendRedirect("step3.jsp?error=Weapon is required");
            return;
        }
        req.getSession().setAttribute("weapon", weapon);
        resp.sendRedirect("confirm.jsp"); // Изменено на "confirm.jsp"
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обработка GET-запроса (возвращение на step3.jsp)
        req.getRequestDispatcher("step3.jsp").forward(req, resp);
    }
}