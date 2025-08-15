package com.example.lab7;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfirmServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        LocalDateTime now = LocalDateTime.now();
        String createdAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        session.setAttribute("createdAt", createdAt);

        String name = (String) session.getAttribute("name");
        if (name != null) {
            session.setAttribute("lastCharacter", name);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/confirm.jsp");
        dispatcher.forward(req, resp);

    }
}
