package com.example.beans;

public class Warehouse {
    private int id;
    private String name;

    public Warehouse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
