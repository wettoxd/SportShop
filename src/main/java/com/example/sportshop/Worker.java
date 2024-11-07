package com.example.sportshop;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Worker {
    private ObservableList<ProductData> allProducts;
    @FXML
    private CheckBox available_in_stock;
    @FXML
    private ComboBox<String> price_regulation;
    @FXML
    private ComboBox<String> category_product;
    @FXML
    private TextField foundproductname;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private ComboBox<String> addclient;

    @FXML
    private ComboBox<String> addpickuppoint;

    @FXML
    private ListView<ProductData> allproductslistview;

    @FXML
    private ListView<ProductData> basket;

    DataBase db = new DataBase();
    public static ArrayList<Integer> offers = new ArrayList<>();


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        List<ProductData> list = db.getProducts();
        allproductslistview.getItems().addAll(list);
        allproductslistview.setCellFactory(stringListView -> {
            ListCell<ProductData> cell = new ProductCell();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Добавить товар в заказ");
            editItem.setOnAction(event -> {
                ProductData item = cell.getItem();
                basket.getItems().addAll(item);
                offers.add(item.getID());
                System.out.println("Товар добавлен в корзину");
                System.out.println(offers);
            });
            contextMenu.getItems().addAll(editItem);
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
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

        basket.getItems().addListener((ListChangeListener.Change<? extends ProductData> c) -> {
            int totalPrice = 0;
            for (ProductData item : basket.getItems()) {
                totalPrice += item.getCost() * ProductCell.spinnerValues.getOrDefault(item.getID(), 1);
            }
            totalPriceLabel.setText(("Стоимость: " + totalPrice + " ₽"));
        });

        List<String> pp = db.getPickupPoint();
        addpickuppoint.setItems(FXCollections.observableArrayList(pp));
        List<String> clientlist = db.getClient();
        addclient.setItems(FXCollections.observableArrayList(clientlist));
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
        allproductslistview.setItems(filteredProducts);
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
    void addClientOrder() throws SQLException, ClassNotFoundException {
        db.insertOrder(db.getIdPickupPoint(addpickuppoint.getValue()), db.getIdClient(addclient.getValue()));
        java.util.Date date = new Date();
    }

    @FXML
    void insertOrderProduct() throws SQLException, ClassNotFoundException {

        for (Integer offer : offers) {
            db.insertOrderProduct(db.getmaxOrder(), offer, ProductCell.spinnerValues.getOrDefault(offer, 1));
        }
    }

    public void admin() throws IOException {
        Stage stage = (Stage) totalPriceLabel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("administration.fxml"));
        Stage Newstage = new Stage();
        Newstage.getIcons().add(new Image(("file:icon.jpg")));
        Scene scene = new Scene(fxmlLoader.load(), 570, 337);
        Newstage.setTitle("Администрирование");
        Newstage.setScene(scene);
        Newstage.setResizable(false);
        Newstage.setMaximized(false);
        Newstage.show();
    }

}
