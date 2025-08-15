package com.example.lab8;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/step1")
public class Step1Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            resp.sendRedirect("errorPage.jsp");
            return;
        }

        req.getSession().setAttribute("name", name);
        resp.sendRedirect("step2.jsp");
    }
}
