// CharacterCheckTag.java
package com.example.lab7;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.SkipPageException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CharacterCheckTag extends SimpleTagSupport {
    private String name;
    private String errorPage;
    private Map<String, String> params = new HashMap<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public void addParam(String paramName, String paramValue) {
        params.put(paramName, paramValue);
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();

        if (name == null || name.trim().isEmpty()) {
            pageContext.setAttribute("errorMessage", "Имя персонажа не задано", PageContext.REQUEST_SCOPE);
            forwardToErrorPage(pageContext);
            return;
        }

        String weapon = params.get("weapon");
        String characterClass = params.get("characterClass");

        if (weapon != null && weapon.length() > 20) {
            pageContext.setAttribute("errorMessage", "Оружие '" + weapon + "' слишком длинное (максимум 20 символов)", PageContext.REQUEST_SCOPE);
            forwardToErrorPage(pageContext);
            return;
        }

        if (characterClass != null) {
            String[] validClasses = {"Warrior", "Mage", "Archer"};
            boolean isValidClass = false;
            for (String validClass : validClasses) {
                if (validClass.equalsIgnoreCase(characterClass)) {
                    isValidClass = true;
                    break;
                }
            }
            if (!isValidClass) {
                pageContext.setAttribute("errorMessage", "Недопустимый класс персонажа: " + characterClass, PageContext.REQUEST_SCOPE);
                forwardToErrorPage(pageContext);
                return;
            }
        }

        getJspBody().invoke(null);
    }

    private void forwardToErrorPage(PageContext pageContext) throws JspException {
        try {
            pageContext.forward(errorPage);
        } catch (Exception e) {
            throw new JspException("Error forwarding to " + errorPage, e);
        }
        throw new SkipPageException();
    }
}