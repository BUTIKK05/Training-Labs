package org.example.lab4_ds_v6;

import java.util.*;

public class Warehouse {
    private String id;
    private String name;
    private List<Product> products = new ArrayList<>();

    public Warehouse() {}

    public Warehouse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}