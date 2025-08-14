package org.example.lab3_ds_v5;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private double price;
    private String warehouseName;

    public Product() {}

    public Product(String name, double price, String warehouseName) {
        this.name = name;
        this.price = price;
        this.warehouseName = warehouseName;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getWarehouseName() { return warehouseName; }
}
