package com.example.lab7;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Step2Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String characterClass = req.getParameter("characterClass");
        if (characterClass == null || characterClass.trim().isEmpty()) {
            resp.sendRedirect("step2.jsp?error=Class is required");
            return;
        }
        req.getSession().setAttribute("characterClass", characterClass); // Исправлено на "characterClass"
        resp.sendRedirect("step3.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обработка GET-запроса (возвращение на step2.jsp)
        req.getRequestDispatcher("step2.jsp").forward(req, resp);
    }
}