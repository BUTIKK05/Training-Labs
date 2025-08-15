package org.example.lab6_ds_v1;

import jakarta.xml.bind.annotation.*;

/**
 * <h2>Класс Product</h2>
 * <p>
 * Представляет сущность товара, хранящегося в XML-файле.
 * </p>
 *
 * @author LESHA
 * @see ProductList
 */
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
    private int id;
    private String name;
    private double price;

    /** Пустой конструктор для JAXB */
    public Product() {}

    /**
     * Конструктор для создания товара.
     *
     * @param id ID товара.
     * @param name Название товара.
     * @param price Цена товара.
     */
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /** @return ID товара */
    public int getId() { return id; }

    /** @param id ID товара */
    public void setId(int id) { this.id = id; }

    /** @return Название товара */
    public String getName() { return name; }

    /** @param name Название товара */
    public void setName(String name) { this.name = name; }

    /** @return Цена товара */
    public double getPrice() { return price; }

    /** @param price Цена товара */
    public void setPrice(double price) { this.price = price; }
}
