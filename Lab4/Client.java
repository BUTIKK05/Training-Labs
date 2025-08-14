package org.example.lab4_ds_v6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;

public class Client extends Application {
    private StoreService store;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        store = (StoreService) LocateRegistry.getRegistry("localhost").lookup("store");

        TextField productName = new TextField();
        TextField price = new TextField();
        TextField warehouseName = new TextField();
        TextField percent = new TextField();

        Button addProductBtn = new Button("Добавить товар");
        Button removeProductBtn = new Button("Удалить товар");
        Button discountProductBtn = new Button("Изменить цену (%)");
        Button addWarehouseBtn = new Button("Добавить склад");
        Button removeWarehouseBtn = new Button("Удалить склад");
        Button showProductsBtn = new Button("Показать товары");

        addProductBtn.setOnAction(e -> {
            try {
                store.addProduct(warehouseName.getText(), productName.getText(), Double.parseDouble(price.getText()));
                showAlert(Alert.AlertType.INFORMATION, "Товар добавлен");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });

        removeProductBtn.setOnAction(e -> {
            try {
                store.removeProduct(warehouseName.getText(), productName.getText());
                showAlert(Alert.AlertType.INFORMATION, "Товар удалён");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });

        discountProductBtn.setOnAction(e -> {
            try {
                store.discountProduct(productName.getText(), Double.parseDouble(percent.getText()));
                showAlert(Alert.AlertType.INFORMATION, "Цена изменена");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });

        addWarehouseBtn.setOnAction(e -> {
            try {
                store.addWarehouse(warehouseName.getText());
                showAlert(Alert.AlertType.INFORMATION, "Склад добавлен");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });

        removeWarehouseBtn.setOnAction(e -> {
            try {
                store.removeWarehouse(warehouseName.getText());
                showAlert(Alert.AlertType.INFORMATION, "Склад удалён");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });

        showProductsBtn.setOnAction(e -> {
            try {
                String warehouse = warehouseName.getText();
                var products = store.getProductsByWarehouseName(warehouse);
                if (products.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Нет товаров на складе: " + warehouse);
                } else {
                    StringBuilder sb = new StringBuilder("Товары на складе " + warehouse + ":\n");
                    for (var p : products) {
                        sb.append("- ").append(p.getName()).append(", Цена: ").append(p.getPrice()).append("\n");
                    }
                    showAlert(Alert.AlertType.INFORMATION, sb.toString());
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, ex.getMessage());
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                new Label("Имя товара:"), productName,
                new Label("Цена:"), price,
                new Label("Имя склада:"), warehouseName,
                new Label("Процент скидки:"), percent,
                addProductBtn, removeProductBtn, discountProductBtn,
                addWarehouseBtn, removeWarehouseBtn, showProductsBtn
        );

        primaryStage.setTitle("Управление складом");
        primaryStage.setScene(new Scene(root, 320, 450));
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.show();
    }
}
