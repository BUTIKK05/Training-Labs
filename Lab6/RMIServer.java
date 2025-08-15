package org.example.lab6_ds_v1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * <h2>Класс RMIServer</h2>
 * @author LESHA
 */
public class RMIServer {
    public static void main(String[] args) {
        try {
            RMIService service = new RMIServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RMIService", service);
            System.out.println("Сервер запущен.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
