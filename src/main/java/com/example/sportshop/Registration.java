package com.example.sportshop;

import javafx.event.ActionEvent;
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
import java.sql.SQLException;

public class Registration {
    DataBase db = new DataBase();


    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private TextField FIO;

    @FXML
    private TextField email;

    @FXML
    void registrations() throws SQLException, ClassNotFoundException, IOException {
        if (!FIO.getText().trim().equals("") & !login.getText().trim().equals("") & !password.getText().trim().equals("") & !email.getText().trim().equals("")) {
            db.insertUser(FIO.getText(), login.getText(), password.getText(), email.getText());
            FIO.setText("");
            login.setText("");
            password.setText("");
            email.setText("");

        } else {
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image(("file:icon.jpg")));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Button okButton = new Button("Ok");
            VBox vbox = new VBox(new Text("Вы забыли внести какие-то данные!"), okButton);
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
    void goingBack() throws IOException {
        Stage stage = (Stage) FIO.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("authorization.fxml"));
        Stage Newstage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 620, 400);
        Newstage.setTitle("Авторизация");
        Newstage.setScene(scene);
        Newstage.show();
    }
}
