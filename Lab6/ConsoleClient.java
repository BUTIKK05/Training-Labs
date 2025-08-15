package org.example.lab6_ds_v1;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * <h2>Класс ConsoleClient</h2>
 * @author LESHA
 */

public class ConsoleClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RMIService service = (RMIService) registry.lookup("RMIService");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите ID товара: ");
            int productId = scanner.nextInt();
            System.out.print("Введите процент изменения цены: ");
            double percentage = scanner.nextDouble();

            service.updateProductPrice(productId, percentage);
            System.out.println("Цена товара успешно обновлена.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
