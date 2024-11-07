package com.example.sportshop;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class Authorization {


    @FXML
    public static Button enter;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    public static String log;

    DataBase db = new DataBase();


    @FXML
    void authorization() throws IOException, SQLException, ClassNotFoundException {


        if (!login.getText().trim().equals("") && !password.getText().trim().equals("")) {
            int a = db.getUser(login.getText(), password.getText());
            log = login.getText();
            if (a == 1) {
                Stage stage = (Stage) login.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("client.fxml"));
                Stage Newstage = new Stage();
                Newstage.getIcons().add(new Image(("file:icon.jpg")));
                Scene scene = new Scene(fxmlLoader.load(), 775, 640);
                Newstage.setTitle("Клиент");
                Newstage.setResizable(false);
                Newstage.setMaximized(false);
                Newstage.setScene(scene);
                Newstage.show();
            }
            if (a == 2) {
                Stage stage = (Stage) login.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("worker.fxml"));
                Stage Newstage = new Stage();
                Newstage.getIcons().add(new Image(("file:icon.jpg")));
                Scene scene = new Scene(fxmlLoader.load(), 800, 647);
                Newstage.setTitle("Сотрудник");
                Newstage.setResizable(false);
                Newstage.setMaximized(false);
                Newstage.setScene(scene);
                Newstage.show();
            }
            if (a == 0) {
                {
                    Stage dialogStage = new Stage();
                    dialogStage.getIcons().add(new Image(("file:icon.jpg")));
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    Button okButton = new Button("Ok");
                    VBox vbox = new VBox(new Text("Неверный логин или пароль!"), okButton);
                    okButton.setOnAction(e -> {
                        dialogStage.close();
                    });
                    dialogStage.setWidth(250);
                    dialogStage.setHeight(100);
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setPadding(new Insets(15));
                    dialogStage.setScene(new Scene(vbox));
                    dialogStage.show();
                    dialogStage.setX(password.getScene().getWindow().getX() + (password.getScene().getWindow().getWidth() - dialogStage.getWidth()) / 2); // установка положения по оси X
                    dialogStage.setY(password.getScene().getWindow().getY() + (password.getScene().getWindow().getHeight() - dialogStage.getHeight()) / 2); // установка положения по оси Y
                }
            }


        } else {
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(("file:icon.jpg")));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Button okButton = new Button("Ok");
            VBox vbox = new VBox(new Text("Вы не ввели логин или пароль!"), okButton);
            okButton.setOnAction(e -> {
                dialogStage.close();
            });
            dialogStage.setWidth(250);
            dialogStage.setHeight(100);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(15));
            dialogStage.setScene(new Scene(vbox));
            dialogStage.show();
            dialogStage.setX(password.getScene().getWindow().getX() + (password.getScene().getWindow().getWidth() - dialogStage.getWidth()) / 2); // установка положения по оси X
            dialogStage.setY(password.getScene().getWindow().getY() + (password.getScene().getWindow().getHeight() - dialogStage.getHeight()) / 2); // установка положения по оси Y
        }

    }




    @FXML
    void registration() throws IOException {
        Stage stage = (Stage) login.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("registration.fxml"));
        Stage Newstage = new Stage();
        Newstage.getIcons().add(new Image(("file:icon.jpg")));
        Scene scene = new Scene(fxmlLoader.load(), 600, 341);
        Newstage.setTitle("Регистрация");
        Newstage.setResizable(false);
        Newstage.setMaximized(false);
        Newstage.setScene(scene);
        Newstage.show();
    }

}