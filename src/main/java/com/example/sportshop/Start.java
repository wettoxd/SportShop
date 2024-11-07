package com.example.sportshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("authorization.fxml"));
        stage.getIcons().add(new Image(("file:image/icon.jpg")));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setMaximized(false);
    }
    public static void main(String[] args) {
        launch();
    }
}