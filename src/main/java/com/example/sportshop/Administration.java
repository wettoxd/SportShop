package com.example.sportshop;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Administration {


    @FXML
    private TextField descriptionproduct;

    @FXML
    private TextField nameproduct;

    @FXML
    private TextField photoproduct;

    @FXML
    private TextField priceproduct;

    @FXML
    private TextField productinstock;

    @FXML
    private TextField suppliersproduct;

    @FXML
    private Label updateproducttext;

    @FXML
    private ComboBox<String> changetable;

    @FXML
    private ListView<String> table;
    @FXML
    private Button addrecord;
    DataBase db = new DataBase();

    @FXML
    private Button savechanges;

    @FXML
    void initialize() {
        changetable.setItems(FXCollections.observableArrayList("products", "suppliers", "users"));
        changetable.setOnAction(event -> {
            System.out.println("Обработчик событий вызван");

            if (changetable.getValue().equals("products")) {
                try {
                    ArrayList<ArrayList<String>> records = db.getAllProducts();
                    table.getItems().clear();
                    for (ArrayList<String> record : records) {
                        String row = String.join(" | ", record);
                        table.getItems().add(row);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при получении записей: " + e.getMessage());
                }

            } else if (changetable.getValue().equals("suppliers")) {
                try {
                    ArrayList<ArrayList<String>> records = db.getAllSuppliers();
                    table.getItems().clear();
                    for (ArrayList<String> record : records) {
                        String row = String.join(" | ", record);
                        table.getItems().add(row);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при получении записей: " + e.getMessage());
                }
            } else {
                try {
                    ArrayList<ArrayList<String>> records = db.getAllUsers();
                    table.getItems().clear();
                    for (ArrayList<String> record : records) {
                        String row = String.join(" | ", record);
                        table.getItems().add(row);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при получении записей: " + e.getMessage());
                }
            }

        });
        table.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setContextMenu(null);
                } else {
                    setText(item);
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem("Обновить");
                    MenuItem deleteItem = new MenuItem("Удалить");
                    contextMenu.getItems().addAll(editItem, deleteItem);
                    setContextMenu(contextMenu);


                    editItem.setOnAction(event -> {
                        if (changetable.getValue().equals("products")) {
                            openUpdateFormProduct();
                        } else if (changetable.getValue().equals("suppliers")) {
                            openUpdateFormSupplier();
                        } else {
                            openUpdateFormUser();
                        }
                    });


                    deleteItem.setOnAction(event -> {
                        if (changetable.getValue().equals("products")) {
                            int id = getRecordId();
                            try {
                                db.deleteProduct(id);
                                table.getItems().remove(item);
                            } catch (SQLException | ClassNotFoundException e) {
                                System.out.println("Ошибка при удалении записи: " + e.getMessage());
                            }
                        } else if ((changetable.getValue().equals("suppliers"))) {
                            int id = getRecordId();
                            try {
                                db.deleteSupplier(id);
                                table.getItems().remove(item);
                            } catch (SQLException | ClassNotFoundException e) {
                                System.out.println("Ошибка при удалении записи: " + e.getMessage());
                            }
                        } else {
                            int id = getRecordId();
                            try {
                                db.deleteUser(id);
                                table.getItems().remove(item);
                            } catch (SQLException | ClassNotFoundException e) {
                                System.out.println("Ошибка при удалении записи: " + e.getMessage());
                            }
                        }

                    });
                }
            }
        });
        addrecord.setOnAction(event -> {
            if (changetable.getValue().equals("products")) {
                openAddFormProduct();
            } else if (changetable.getValue().equals("suppliers")) {
                openAddFormSupplier();
            } else {
                openAddFormUser();
            }
        });
        addrecord.setOnAction(event -> {
            if (changetable.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, выберите таблицу!");
                alert.showAndWait();
            } else if (changetable.getValue().equals("products")) {
                openAddFormProduct();
            } else if (changetable.getValue().equals("suppliers")) {
                openAddFormSupplier();
            } else {
                openAddFormUser();
            }
        });
    }


    public void openUpdateFormProduct() {
        // Создаем новую сцену
        Stage stage = new Stage();
        stage.setTitle("Обновление записи");
        stage.getIcons().add(new Image(("file:icon.jpg")));
        // Создаем панель для формы
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Создаем поля ввода
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField stockQuantityField = new TextField();
        TextField supplierIdField = new TextField();
        TextField descriptionField = new TextField();
        TextField photoField = new TextField();

        // Добавляем поля ввода в панель
        gridPane.add(new Label("Имя:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Цена:"), 0, 1);
        gridPane.add(priceField, 1, 1);
        gridPane.add(new Label("Отсаток на складе:"), 0, 2);
        gridPane.add(stockQuantityField, 1, 2);
        gridPane.add(new Label("Поставщик:"), 0, 3);
        gridPane.add(supplierIdField, 1, 3);
        gridPane.add(new Label("Описание:"), 0, 4);
        gridPane.add(descriptionField, 1, 4);
        gridPane.add(new Label("Фото:"), 0, 5);
        gridPane.add(photoField, 1, 5);

        // Создаем кнопку отправки
        Button submitButton = new Button("Обновить");
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            int price = Integer.parseInt((priceField.getText()));
            int stockQuantity = Integer.parseInt((stockQuantityField.getText()));
            int supplierId = Integer.parseInt(supplierIdField.getText());
            String description = descriptionField.getText();
            String photo = photoField.getText();
            int id = getRecordId();
            if (name.trim().equals("") && priceField.getText().trim().equals("") && supplierIdField.getText().trim().equals("") && description.isEmpty() && photo.trim().equals("") || price<0|| stockQuantity<0 ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, заполните все поля корректно!");
                alert.showAndWait();
            } else {
                try {
                    db.updateProduct(id, name, price, stockQuantity, supplierId, description, photo);
                    // Обновите список после обновления записи
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при обновлении записи: " + e.getMessage());
                }
            }
        });


        // Добавляем кнопку отправки в панель
        gridPane.add(submitButton, 1, 6);

        // Создаем сцену с панелью
        Scene scene = new Scene(gridPane, 300, 310);
        stage.setScene(scene);
        stage.show();
    }

    public void openUpdateFormUser() {
        Stage stage = new Stage();
        stage.setTitle("Обновление записи");
        stage.getIcons().add(new Image(("file:icon.jpg")));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField FIOField = new TextField();
        TextField loginField = new TextField();
        TextField passwordField = new TextField();
        TextField emailField = new TextField();

        gridPane.add(new Label("ФИО:"), 0, 0);
        gridPane.add(FIOField, 1, 0);
        gridPane.add(new Label("Логин:"), 0, 1);
        gridPane.add(loginField, 1, 1);
        gridPane.add(new Label("Пароль:"), 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);

        Button submitButton = new Button("Обновить");
        submitButton.setOnAction(event -> {
            String FIO = FIOField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            int id = getRecordId();
            if (FIO.trim().equals("") && loginField.getText().trim().equals("") && passwordField.getText().trim().equals("") && email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, заполните все поля корректно!");
                alert.showAndWait();
            } else {
                try {
                    db.updateUser(id, FIO, login, password, email);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при обновлении записи: " + e.getMessage());
                }
            }
        });
        // Добавляем кнопку отправки в панель
        gridPane.add(submitButton, 1, 6);

        // Создаем сцену с панелью
        Scene scene = new Scene(gridPane, 300, 310);
        stage.setScene(scene);
        stage.show();
    }

    public void openUpdateFormSupplier() {
        Stage stage = new Stage();
        stage.setTitle("Обновление записи");
        stage.getIcons().add(new Image(("file:icon.jpg")));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField descriptionField = new TextField();

        gridPane.add(new Label("Название:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Email:"), 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(new Label("Телефон:"), 0, 2);
        gridPane.add(phoneNumberField, 1, 2);
        gridPane.add(new Label("Описание:"), 0, 3);
        gridPane.add(descriptionField, 1, 3);

        Button submitButton = new Button("Обновить");
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String description = descriptionField.getText();
            int id = getRecordId();
            if (name.trim().equals("") && emailField.getText().trim().equals("") && phoneNumberField.getText().trim().equals("") && description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, заполните все поля корректно!");
                alert.showAndWait();
            } else {
                try {
                    db.updateSupplier(id, name, email, phoneNumber, description);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при обновлении записи: " + e.getMessage());
                }
            }
        });

        // Добавляем кнопку отправки в панель
        gridPane.add(submitButton, 1, 6);

        // Создаем сцену с панелью
        Scene scene = new Scene(gridPane, 300, 310);
        stage.setScene(scene);
        stage.show();
    }
    public void openAddFormProduct() {
        Stage stage = new Stage();
        stage.setTitle("Добавление продукта");
        stage.getIcons().add(new Image(("file:icon.jpg")));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField stockQuantityField = new TextField();
        TextField supplierIdField = new TextField();
        TextField descriptionField = new TextField();
        TextField photoField = new TextField();

        gridPane.add(new Label("Имя:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Цена:"), 0, 1);
        gridPane.add(priceField, 1, 1);
        gridPane.add(new Label("Отсаток на складе:"), 0, 2);
        gridPane.add(stockQuantityField, 1, 2);
        gridPane.add(new Label("Поставщик:"), 0, 3);
        gridPane.add(supplierIdField, 1, 3);
        gridPane.add(new Label("Описание:"), 0, 4);
        gridPane.add(descriptionField, 1, 4);
        gridPane.add(new Label("Фото:"), 0, 5);
        gridPane.add(photoField, 1, 5);

        Button submitButton = new Button("Добавить");
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String priceString = priceField.getText();
            String stockQuantityString = stockQuantityField.getText();
            String supplierIdString = supplierIdField.getText();
            String description = descriptionField.getText();
            String photo = photoField.getText();

            if (name.trim().equals("") || priceString.trim().equals("") || stockQuantityString.trim().equals("") || supplierIdString.trim().equals("") || description.trim().equals("") || photo.trim().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, заполните все поля корректно!");
                alert.showAndWait();
            } else {
                try {
                    int price = Integer.parseInt(priceString);
                    int stockQuantity = Integer.parseInt(stockQuantityString);
                    int supplierId = Integer.parseInt(supplierIdString);
                    db.addProduct(name, stockQuantity, price, supplierId, description, photo);
                    // Обновите список после добавления нового продукта
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка ввода данных");
                    alert.setContentText("Введите число для полей 'Цена', 'Отсаток на складе' и 'Поставщик'!");
                    alert.showAndWait();
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при добавлении нового продукта: " + e.getMessage());
                }
            }
        });

        gridPane.add(submitButton, 1, 6);

        Scene scene = new Scene(gridPane, 300, 310);
        stage.setScene(scene);
        stage.show();
    }
    public void callStoredProcedure() {
        try {
            // Получаем соединение с базой данных
            Connection conn = db.getDbConnection();

            // Создаем CallableStatement для вызова хранимой процедуры
            CallableStatement cs = conn.prepareCall("{CALL IncreaseStock()}");

            // Выполняем хранимую процедуру
            cs.execute();

            // Закрываем соединение
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void openAddFormUser() {
        Stage stage = new Stage();
        stage.setTitle("Добавление пользователя");
        stage.getIcons().add(new Image(("file:icon.jpg")));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField FIOField = new TextField();
        TextField loginField = new TextField();
        TextField passwordField = new TextField();
        TextField emailField = new TextField();

        gridPane.add(new Label("ФИО:"), 0, 0);
        gridPane.add(FIOField, 1, 0);
        gridPane.add(new Label("Логин:"), 0, 1);
        gridPane.add(loginField, 1, 1);
        gridPane.add(new Label("Пароль:"), 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);

        Button submitButton = new Button("Добавить");
        submitButton.setOnAction(event -> {
            String FIO = FIOField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            if (FIO.trim().equals("") && loginField.getText().trim().equals("") && passwordField.getText().trim().equals("") && email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, заполните все поля корректно!");
                alert.showAndWait();
            } else {
                try {
                    db.addUser(FIO, login, password, email);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при добавлении нового пользователя: " + e.getMessage());
                }
            }
        });

        gridPane.add(submitButton, 1, 6);

        Scene scene = new Scene(gridPane, 300, 310);
        stage.setScene(scene);
        stage.show();
    }
    public void openAddFormSupplier() {
        Stage stage = new Stage();
        stage.setTitle("Добавление поставщика");
        stage.getIcons().add(new Image(("file:icon.jpg")));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();
        TextField descriptionField = new TextField(); // Добавлено поле для описания

        gridPane.add(new Label("Имя:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Адрес:"), 0, 1);
        gridPane.add(addressField, 1, 1);
        gridPane.add(new Label("Телефон:"), 0, 2);
        gridPane.add(phoneField, 1, 2);
        gridPane.add(new Label("Описание:"), 0, 3); // Добавлено поле для описания
        gridPane.add(descriptionField, 1, 3); // Добавлено поле для описания

        Button submitButton = new Button("Добавить");
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String description = descriptionField.getText(); // Получено значение из поля описания
            if (name.trim().equals("") && addressField.getText().trim().equals("") && phoneField.getText().trim().equals("") && description.trim().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода данных");
                alert.setContentText("Пожалуйста, заполните все поля корректно!");
                alert.showAndWait();
            } else {
                try {
                    db.addSupplier(name, address, phone, description); // Передано значение описания в метод addSupplier
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Ошибка при добавлении нового поставщика: " + e.getMessage());
                }
            }
        });

        gridPane.add(submitButton, 1, 4);

        Scene scene = new Scene(gridPane, 300, 250);
        stage.setScene(scene);
        stage.show();
    }



    private int getRecordId() {
        String selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String[] record = selectedItem.split(" | ");
            return Integer.parseInt(record[0]); // предполагается, что идентификатор записи - это первое поле
        }
        return -1; // возвращаем -1, если ничего не выбрано
    }

}




