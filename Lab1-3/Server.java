package org.example.lab3_ds_v5;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception {
        StoreService service = new StoreServiceImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi://26.135.102.136:1099/store", service);
        System.out.println("Сервер RMI запущен.");
    }
}
