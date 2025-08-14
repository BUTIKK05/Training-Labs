package org.example.lab5_ds_v5;

import jakarta.xml.bind.annotation.*;
import java.util.*;

@XmlRootElement(name = "warehouse")
@XmlAccessorType(XmlAccessType.FIELD)
public class Warehouse {
    @XmlAttribute
    private String id;

    @XmlAttribute
    private String name;

    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
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