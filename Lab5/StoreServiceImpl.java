package org.example.lab5_ds_v5;

import jakarta.xml.bind.*;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.List;

public class StoreServiceImpl extends UnicastRemoteObject implements StoreService {
    private Store store;
    private final File xmlFile = new File("store.xml");

    public StoreServiceImpl() throws Exception {
        loadXML();
    }

    private void loadXML() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Store.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        store = (Store) unmarshaller.unmarshal(xmlFile);
    }

    private void saveXML() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Store.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(store, xmlFile);
    }

    public void addProduct(String warehouseName, String name, double price) {
        if (price < 0 || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid data");
        }

        for (Warehouse w : store.getWarehouses()) {
            if (w.getName().equalsIgnoreCase(warehouseName)) {
                w.getProducts().add(new Product(name, price, w.getName()));
                saveSafe();
                return;
            }
        }

        throw new RuntimeException("Warehouse not found by name");
    }


    public void removeProduct(String warehouseName, String name) {
        for (Warehouse w : store.getWarehouses()) {
            if (w.getName().equalsIgnoreCase(warehouseName)) {
                w.getProducts().removeIf(p -> p.getName().equalsIgnoreCase(name));
                saveSafe();
                return;
            }
        }
        throw new RuntimeException("Warehouse not found by name");
    }


    public void addWarehouse(String warehouseName) {
        if (warehouseName == null || warehouseName.isBlank()) throw new IllegalArgumentException("Invalid warehouse name");

        String id = UUID.randomUUID().toString();
        store.getWarehouses().add(new Warehouse(id, warehouseName));
        saveSafe();
    }

    public void removeWarehouse(String name) {
        boolean removed = store.getWarehouses().removeIf(w -> w.getName().equalsIgnoreCase(name));
        if (!removed) throw new RuntimeException("Warehouse not found by name");
        saveSafe();
    }


    public void discountProduct(String productName, double percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("Invalid percent");
        for (Warehouse w : store.getWarehouses()) {
            for (Product p : w.getProducts()) {
                if (p.getName().equalsIgnoreCase(productName)) {
                    p.setPrice(p.getPrice() * (1 - percent / 100));
                }
            }
        }
        saveSafe();
    }
    @Override
    public List<Product> getProductsByWarehouseName(String warehouseName) throws RemoteException {
        for (Warehouse w : store.getWarehouses()) {
            if (w.getName().equalsIgnoreCase(warehouseName)) {
                return w.getProducts();
            }
        }
        throw new RuntimeException("Склад не найден");
    }

    private void saveSafe() {
        try { saveXML(); } catch (Exception e) { e.printStackTrace(); }
    }
}