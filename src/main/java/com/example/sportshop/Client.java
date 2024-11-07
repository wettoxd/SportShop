package com.example.sportshop;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Client {
    private boolean isOrderAdded = false;

    @FXML
    private CheckBox available_in_stock;
    @FXML
    private ComboBox<String> price_regulation;

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button back;
    @FXML
    private TextField foundproductname;
    @FXML
    private ComboBox<String> category_product;
    @FXML
    public ComboBox<String> pickuppoint;
    @FXML
    private ListView<ProductData> products;
    @FXML
    private Button arrange;
    DataBase db = new DataBase();
    @FXML
    private ListView<ProductData> basket;
    public static String login = Authorization.log;
    private ObservableList<ProductData> allProducts;
    public static ArrayList<Integer> offers = new ArrayList<>();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        try {
            List<ProductData> list = db.getProducts();
            products.getItems().addAll(list);
            products.setCellFactory(stringListView -> {
                ListCell<ProductData> cell = new ProductCell();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Добавить товар в заказ");
                editItem.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    int stockQuantity = item.getHave();
                    int quantity = ProductCell.spinnerValues.getOrDefault(item.getID(), 1);
                    if (quantity > stockQuantity) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Отсутствие товара");
                        alert.setHeaderText("Товара недостаточно на складе");
                        LocalDate currentDate = LocalDate.now();
                        LocalDate deliveryDate = currentDate.plusDays(7);
                        alert.setContentText("Доставка будет перенесена на 7 дней(" + deliveryDate + ").");
                        alert.showAndWait();
                        // Выводим новую дату доставки
                        basket.getItems().addAll(item);
                        offers.add(item.getID());
                        System.out.println("Товар добавлен в корзину");
                        System.out.println(offers);
                    } else {
                        basket.getItems().addAll(item);
                        offers.add(item.getID());
                        System.out.println("Товар добавлен в корзину");
                        System.out.println(offers);
                    }
                });
                MenuItem editItem1 = new MenuItem("Информация о производителе");
                editItem1.setOnAction(event -> {
                    ProductData item = cell.getItem();
                    ArrayList<String> manufacturer = null;
                    try {
                        manufacturer = db.getSupplier(item.getSupplier_id());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Stage dialogStage = new Stage();
                    dialogStage.getIcons().add(new Image(("file:icon.jpg")));
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    Button okButton = new Button("Ok");


                    Label nameLabel = new Label("Информация о производителе: " + manufacturer.get(0));
                    nameLabel.setAlignment(Pos.CENTER_LEFT);

                    Label emailLabel = new Label("Почта: " + manufacturer.get(1));
                    emailLabel.setAlignment(Pos.CENTER_LEFT);

                    Label phoneLabel = new Label("Телефон: " + manufacturer.get(2));
                    phoneLabel.setAlignment(Pos.CENTER_LEFT);

                    Label descriptionLabel = new Label("Описание: " + manufacturer.get(3));
                    descriptionLabel.setWrapText(true);

                    VBox vbox = new VBox(
                            nameLabel,
                            emailLabel,
                            phoneLabel,
                            descriptionLabel,
                            okButton
                    );

                    okButton.setOnAction(e -> {
                        dialogStage.close();
                    });
                    dialogStage.setWidth(400);
                    dialogStage.setHeight(200);
                    vbox.setPadding(new Insets(15));
                    dialogStage.setScene(new Scene(vbox));
                    okButton.setAlignment(Pos.CENTER_RIGHT);
                    dialogStage.show();
                    dialogStage.setX(cell.getScene().getWindow().getX() + (cell.getScene().getWindow().getWidth() - dialogStage.getWidth()) / 2); // установка положения по оси X
                    dialogStage.setY(cell.getScene().getWindow().getY() + (cell.getScene().getWindow().getHeight() - dialogStage.getHeight()) / 2);
                });
                contextMenu.getItems().addAll(editItem);
                contextMenu.getItems().addAll(editItem1);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        basket.setCellFactory(stringListView -> {
            ListCell<ProductData> cell = new ProductCell();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem2 = new MenuItem("Удалить");
            editItem2.setOnAction(event -> {
                ProductData item = cell.getItem();
                basket.getItems().remove(item);
                offers.remove(offers.indexOf(item.getID()));
                System.out.println(offers);

            });

            contextMenu.getItems().addAll(editItem2);
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });


        List<String> pp = db.getPickupPoint();
        pickuppoint.setItems(FXCollections.observableArrayList(pp));

        basket.getItems().addListener((ListChangeListener.Change<? extends ProductData> c) -> {
            int totalPrice = 0;
            for (ProductData item : basket.getItems()) {
                totalPrice += item.getCost() * ProductCell.spinnerValues.getOrDefault(item.getID(),1);
            }
            totalPriceLabel.setText(("Стоимость: " + totalPrice + " ₽"));
        });

        allProducts = FXCollections.observableArrayList(db.getProducts());
        ArrayList<String> categories = db.getCategories();
        category_product.setItems(FXCollections.observableArrayList(categories));
        price_regulation.setItems(FXCollections.observableArrayList("По возрастанию", "По убыванию"));


        available_in_stock.setOnAction(event -> {
            updateProducts();
        });
        foundproductname.textProperty().addListener((observable, oldValue, newValue) -> {
            updateProducts();
        });
        category_product.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateProducts();
        });
        price_regulation.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateProducts();
        });


    }

    public void updateProducts() {
        ObservableList<ProductData> filteredProducts = filterByAvailability();
        filteredProducts = filterByCategory(filteredProducts);
        filteredProducts = filterByName(filteredProducts);
        String priceRegulationValue = price_regulation.getValue();
        boolean ascending = priceRegulationValue != null && priceRegulationValue.equals("По возрастанию");
        filteredProducts = filterByPrice(filteredProducts, ascending);
        products.setItems(filteredProducts);
    }
    public ObservableList<ProductData> filterByCategory(ObservableList<ProductData> products) {
        ObservableList<ProductData> filteredProducts = FXCollections.observableArrayList(products);
        String selectedCategory = category_product.getValue();
        if (selectedCategory != null) {
            filteredProducts.removeIf(product -> {
                try {
                    return !product.getCategoryName().equals(selectedCategory);
                } catch (SQLException | ClassNotFoundException e) {
                    // Обработка исключений
                    System.out.println("Ошибка при получении имени категории: " + e.getMessage());
                    return false;
                }
            });
        }
        return filteredProducts;
    }

    public ObservableList<ProductData> filterByName(ObservableList<ProductData> products) {
        ObservableList<ProductData> filteredProducts = FXCollections.observableArrayList(products);
        filteredProducts.removeIf(product -> !product.getName().toLowerCase().contains(foundproductname.getText().toLowerCase()));
        return filteredProducts;
    }
    public ObservableList<ProductData> filterByAvailability() {
        ObservableList<ProductData> filteredProducts = FXCollections.observableArrayList();
        for (ProductData product : allProducts) {
            if (available_in_stock.isSelected() && product.getHave() > 0) {
                filteredProducts.add(product);
            } else if (!available_in_stock.isSelected()) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public ObservableList<ProductData> filterByPrice(ObservableList<ProductData> products, boolean ascending) {
        ObservableList<ProductData> sortedProducts = FXCollections.observableArrayList(products);
        sortedProducts.sort(ascending ? Comparator.comparing(ProductData::getCost) : Comparator.comparing(ProductData::getCost).reversed());
        return sortedProducts;
    }



    @FXML
    void addOrder() throws SQLException, ClassNotFoundException {
        db.insertOrder(db.getIdPickupPoint(pickuppoint.getValue()), db.getClient(login));
        isOrderAdded = true;
        java.util.Date date = new Date();
    }

    @FXML
    void insertOrderProduct() throws SQLException, ClassNotFoundException {
        if (!isOrderAdded) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка выполнения операции");
            alert.setContentText("Сначала нужно выбрать пункт выдачи!");
            alert.showAndWait();
            return;
        }
        String selectedPickupPoint = pickuppoint.getValue();
        if (selectedPickupPoint == null) {
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(("file:icon.jpg")));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Button okButton = new Button("Ok");
            VBox vbox = new VBox(new Text("Пункт выдачи не выбран!"), okButton);
            okButton.setOnAction(e -> {
                dialogStage.close();
            });
            dialogStage.setWidth(250);
            dialogStage.setHeight(100);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(15));
            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            dialogStage.setX(pickuppoint.getScene().getWindow().getX() + (pickuppoint.getScene().getWindow().getWidth() - dialogStage.getWidth()) / 2); // установка положения по оси X
            dialogStage.setY(pickuppoint.getScene().getWindow().getY() + (pickuppoint.getScene().getWindow().getHeight() - dialogStage.getHeight()) / 2);
        } else if (basket.getItems().isEmpty()) {
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(("file:icon.jpg")));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Button okButton = new Button("Ok");
            VBox vbox = new VBox(new Text("Добавьте в корзину хотя бы 1 товар!"), okButton);
            okButton.setOnAction(e -> {
                dialogStage.close();
            });
            dialogStage.setWidth(250);
            dialogStage.setHeight(100);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(15));
            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            dialogStage.setX(pickuppoint.getScene().getWindow().getX() + (pickuppoint.getScene().getWindow().getWidth() - dialogStage.getWidth()) / 2); // установка положения по оси X
            dialogStage.setY(pickuppoint.getScene().getWindow().getY() + (pickuppoint.getScene().getWindow().getHeight() - dialogStage.getHeight()) / 2);
        } else {
            for (Integer offer : offers) {
                db.insertOrderProduct(db.getmaxOrder(), offer, ProductCell.spinnerValues.getOrDefault(offer, 1));
            }
        }

    }
}
