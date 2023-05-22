package com.example.chat;

import com.example.chat.scene.LoginPage;
import com.example.chat.scene.RegisterPage;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        LoginPage loginpage = new LoginPage(primaryStage);
        RegisterPage registerPage = new RegisterPage(primaryStage);
        loginpage.show();

        loginpage.registerButton.setOnAction(event -> {
            registerPage.show();
        });

        registerPage.loginButton.setOnAction(event -> {
            loginpage.show();
        });
    }



    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

