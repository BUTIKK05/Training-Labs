package com.example.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class WelcomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
