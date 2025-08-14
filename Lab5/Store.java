package org.example.lab5_ds_v5;

import jakarta.xml.bind.annotation.*;
import java.util.*;

@XmlRootElement(name = "store")
@XmlAccessorType(XmlAccessType.FIELD)
public class Store {
    @XmlElementWrapper(name = "warehouses")
    @XmlElement(name = "warehouse")
    private List<Warehouse> warehouses = new ArrayList<>();

    public List<Warehouse> getWarehouses() { return warehouses; }
    public void setWarehouses(List<Warehouse> warehouses) { this.warehouses = warehouses; }
}