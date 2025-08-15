package com.example.lab8;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/step2")

public class Step2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String characterClass = req.getParameter("characterClass");
        req.getSession().setAttribute("class", characterClass);
        resp.sendRedirect("step3.jsp");
    }
}
