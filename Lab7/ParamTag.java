// ParamTag.java
package com.example.lab7;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class ParamTag extends SimpleTagSupport {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void doTag() throws JspException, IOException {
        StringWriter writer = new StringWriter();
        getJspBody().invoke(writer);
        String value = writer.toString().trim();

        CharacterCheckTag parent = (CharacterCheckTag) findAncestorWithClass(this, CharacterCheckTag.class);
        if (parent == null) {
            throw new JspException("Тег <param> должен быть вложен в <characterCheck>");
        }

        parent.addParam(name, value);
    }
}