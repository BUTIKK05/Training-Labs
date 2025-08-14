package org.example.lab4_ds_v6;

import javax.xml.stream.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class StoreServiceImpl extends UnicastRemoteObject implements StoreService {
    private Store store;
    private final File xmlFile = new File("store.xml");

    public StoreServiceImpl() throws Exception {
        loadXML();
    }

    private void loadXML() throws Exception {
        store = new Store();
        store.setWarehouses(new ArrayList<>());

        if (!xmlFile.exists()) return;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlFile));

        Warehouse currentWarehouse = null;
        Product currentProduct = null;
        String currentElement = "";

        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT -> {
                    currentElement = reader.getLocalName();
                    switch (currentElement) {
                        case "warehouse" -> {
                            currentWarehouse = new Warehouse();
                            currentWarehouse.setId(reader.getAttributeValue(null, "id"));
                            currentWarehouse.setName(reader.getAttributeValue(null, "name"));
                            currentWarehouse.setProducts(new ArrayList<>());
                        }
                        case "product" -> currentProduct = new Product();
                    }
                }
                case XMLStreamConstants.CHARACTERS -> {
                    String text = reader.getText().trim();
                    if (text.isEmpty()) break;
                    if (currentProduct != null) {
                        switch (currentElement) {
                            case "name" -> currentProduct.setName(text);
                            case "price" -> currentProduct.setPrice(Double.parseDouble(text));
                            case "warehouseName" -> currentProduct.setWarehouseName(text);
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    switch (reader.getLocalName()) {
                        case "product" -> currentWarehouse.getProducts().add(currentProduct);
                        case "warehouse" -> store.getWarehouses().add(currentWarehouse);
                    }
                }
            }
        }
        reader.close();
    }

    private void saveXML() throws Exception {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(xmlFile), "UTF-8");

        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("store");
        writer.writeStartElement("warehouses");

        for (Warehouse warehouse : store.getWarehouses()) {
            writer.writeStartElement("warehouse");
            writer.writeAttribute("id", warehouse.getId());
            writer.writeAttribute("name", warehouse.getName());

            writer.writeStartElement("products");
            for (Product product : warehouse.getProducts()) {
                writer.writeStartElement("product");

                writer.writeStartElement("name");
                writer.writeCharacters(product.getName());
                writer.writeEndElement();

                writer.writeStartElement("price");
                writer.writeCharacters(String.valueOf(product.getPrice()));
                writer.writeEndElement();

                writer.writeStartElement("warehouseName");
                writer.writeCharacters(product.getWarehouseName());
                writer.writeEndElement();

                writer.writeEndElement(); // product
            }
            writer.writeEndElement(); // products
            writer.writeEndElement(); // warehouse
        }

        writer.writeEndElement(); // warehouses
        writer.writeEndElement(); // store
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    private void saveSafe() {
        try {
            saveXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String warehouseName, String name, double price) {
        if (price < 0 || name == null || name.isBlank()) throw new IllegalArgumentException("Invalid data");

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

    public List<Product> getProductsByWarehouseName(String warehouseName) throws RemoteException {
        for (Warehouse w : store.getWarehouses()) {
            if (w.getName().equalsIgnoreCase(warehouseName)) {
                return w.getProducts();
            }
        }
        throw new RuntimeException("Склад не найден");
    }
}