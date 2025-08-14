package org.example.lab5_ds_v5;

import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception {
        var service = new StoreServiceImpl();
        var registry = LocateRegistry.createRegistry(1099);
        registry.rebind("store", service);
        System.out.println("RMI сервер запущен");
    }
}
