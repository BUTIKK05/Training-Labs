package com.example.lab8;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@WebServlet("/confirm")

public class ConfirmServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        LocalDateTime now = LocalDateTime.now();
        String createdAt = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        session.setAttribute("createdAt", createdAt);

        String name = (String) session.getAttribute("name");
        if (name != null) {
            Cookie cookie = new Cookie("lastCharacter", name);
            cookie.setMaxAge(60 * 60 * 24);
            resp.addCookie(cookie);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/confirm.jsp");
        dispatcher.forward(req, resp);
        session.invalidate();
    }
}
