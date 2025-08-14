package org.example.lab4_ds_v6;

import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception {
        var service = new StoreServiceImpl();
        var registry = LocateRegistry.createRegistry(1099);
        registry.rebind("store", service);
        System.out.println("RMI сервер запущен");
    }
}
