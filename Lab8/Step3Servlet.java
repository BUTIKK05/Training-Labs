package com.example.lab8;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/step3")

public class Step3Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String weapon = req.getParameter("weapon");
        req.getSession().setAttribute("weapon", weapon);
        resp.sendRedirect("confirm");
    }
}
