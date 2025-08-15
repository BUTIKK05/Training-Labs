package com.example.lab8;

import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharacterInfoTag extends SimpleTagSupport {

    private String name;
    private String characterClass;
    private String weapon;
    private String createdAt;

    public void setName(String name) {
        this.name = name;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        // Приведение JspContext к PageContext
        PageContext pageContext = (PageContext) jspContext;

        // Получаем объекты запроса и ответа
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        // Проверка на корректность данных
        if (name == null || characterClass == null || weapon == null) {
            try {
                // Используем request для получения диспетчера запросов
                request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
            } catch (Exception e) {
                throw new JspException("Error during forwarding to error page", e);
            }
            return;
        }

        // Вывод информации о персонаже
        out.write("<h2>Character Information</h2>");
        out.write("<p><strong>Name:</strong> " + name + "</p>");
        out.write("<p><strong>Class:</strong> " + characterClass + "</p>");
        out.write("<p><strong>Weapon:</strong> " + weapon + "</p>");
        out.write("<p><strong>Created At:</strong> " + createdAt + "</p>");
    }
}
