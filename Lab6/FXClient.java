package org.example.lab6_ds_v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * <h2>Класс FXClient</h2>
 * @author LESHA
 */
public class FXClient extends Application {
    private RMIService service;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client_gui/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("RMI Client");
        primaryStage.show();

        // Инициализация RMI
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        service = (RMIService) registry.lookup("RMIService");

        // Настройка событий
        TextField idField = (TextField) scene.lookup("#idField");
        TextField percentField = (TextField) scene.lookup("#percentField");
        Button updateButton = (Button) scene.lookup("#updateButton");

        updateButton.setOnAction(event -> {
            try {
                int productId = Integer.parseInt(idField.getText());
                double percentage = Double.parseDouble(percentField.getText());
                service.updateProductPrice(productId, percentage);
                System.out.println("Цена успешно обновлена.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
