package com.example.sportshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductCell extends ListCell<ProductData> {


    @FXML
    private AnchorPane container;

    @FXML
    private Label cost;
    @FXML
    private Label title;

    @FXML
    private Label description;

    @FXML
    private Label have;

    @FXML
    private ImageView image;
    @FXML
    private Spinner<Integer> quantitySpinner;

    private FXMLLoader mLLoader;
    public static Map<Integer, Integer> spinnerValues = new HashMap<>();


    @Override
    protected void updateItem(ProductData product, boolean empty) {


        super.updateItem(product, empty);


        if (empty || product == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("product_cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                spinnerValues.put(product.getID(), newVal);

            });
            Integer savedValue = spinnerValues.get(product.getID());
            if (savedValue != null) {
                quantitySpinner.getValueFactory().setValue(savedValue);
            }


            Integer count = product.getHave();
            if (count == 0) {
                container.setStyle("-fx-background-color: gray;");
            } else {
                container.setStyle("");
            }

            title.setText(product.getName());
            cost.setText("Цена товара: " + (product.getCost()) + "₽");
            description.setWrapText(true);
            description.setText("Описание товара: " + (product.getDesc()) + " ");
            have.setText("Остаток на складе: " + (product.getHave()));
            if (product.getPhoto() == null) {
                image.setImage(new Image("net_foto.jpg"));
            } else {
                File file = new File(product.getPhoto());
                try {
                    String urlImage = file.toURI().toURL().toString();
                    Image images = new Image(urlImage);
                    image.setImage(images);
                } catch (MalformedURLException ignored) {


                }
            }


            setText(null);
            setGraphic(container);

        }


    }
}


