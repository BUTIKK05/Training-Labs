package org.example.lab5_ds_v5;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface StoreService extends Remote {
    void addProduct(String warehouseName, String name, double price) throws RemoteException;
    void removeProduct(String warehouseName, String name) throws RemoteException;
    void addWarehouse(String warehouseName) throws RemoteException;
    void removeWarehouse(String warehouseName) throws RemoteException;
    void discountProduct(String productName, double percent) throws RemoteException;
    List<Product> getProductsByWarehouseName(String warehouseName) throws RemoteException;
}