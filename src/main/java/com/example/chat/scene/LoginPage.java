package com.example.chat.scene;

import com.example.chat.LoginApp;
import com.example.chat.control.Shake;
import com.example.chat.control.logincontrol.Control;
import com.example.chat.control.logincontrol.DragWindowHandler;
import com.example.chat.control.logincontrol.FouseControl;
import com.example.chat.struct.Logindata;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginPage {

    public Button closeButton = new Button("╳");

    Stage primaryStage;
    Scene loginScene;
    AnchorPane root = new AnchorPane();
    GridPane loginPane = new GridPane();
    Label titleLabel = new Label("欢迎登陆");
    Label userNameLabel = new Label("邮箱:");
    Label passwordLabel = new Label("密码:");
    Button loginButton = new Button("登录");
    public Button registerButton = new Button("立即注册");
    public Button repassButton = new Button("找回密码");

    public void show() {
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public LoginPage(Stage Stage) throws IOException {
        this.primaryStage = Stage;
        root.setPrefWidth(300);
        root.setPrefHeight(500);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));

        titleLabel.setStyle("-fx-font-size: 24px;");
        loginPane.add(titleLabel, 0, 0, 2, 1);

        TextField userNameField = new TextField();
        loginPane.add(userNameField, 1, 1);

        userNameLabel.setLabelFor(userNameField);
        loginPane.add(userNameLabel, 0, 1);
        userNameField.focusedProperty().addListener(new FouseControl(userNameLabel));


        PasswordField passwordField = new PasswordField();
        loginPane.add(passwordField, 1, 2);

        passwordLabel.setLabelFor(passwordField);
        loginPane.add(passwordLabel, 0, 2);
        passwordField.focusedProperty().addListener(new FouseControl(passwordLabel));
        passwordField.setOnKeyPressed(event -> {Control.ReSetFouse(event,loginButton);});
        userNameField.setOnKeyPressed(event -> {Control.ReSetFouse(event,passwordField);});

        loginButton.setId("loginButton");
        loginButton.setPrefWidth(200);

        loginPane.add(loginButton, 0,3,2,3);
        loginPane.setId("loginPane");

        registerButton.setId("registerButton");
        Label registerLabel = new Label("还没有账号？");

        HBox regibox = new HBox();
        regibox.getChildren().addAll(registerLabel,registerButton);


        Button minButton = new Button("一");
        minButton.setId("min");
        minButton.setOnAction(event -> {
            primaryStage.setIconified(true);
        });

        closeButton.setId("clo");

        //点击关闭按钮后的操作
        closeButton.setOnAction(event -> {
            primaryStage.close();
            try {
                Control.Server.closeSocket();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.exit();
        });

        HBox title = new HBox();
        title.setId("title");

        title.getChildren().addAll(minButton,closeButton);

        loginPane.setPrefHeight(300);
        loginPane.setPrefWidth(500);

        root.getChildren().addAll(loginPane,title);

        repassButton.setId("repassButton");

        AnchorPane.setTopAnchor(title,0.0);
        AnchorPane.setRightAnchor(title,0.0);
        AnchorPane.setBottomAnchor(regibox,5.0);
        AnchorPane.setRightAnchor(regibox,5.0);
        AnchorPane.setLeftAnchor(repassButton,5.0);
        AnchorPane.setBottomAnchor(repassButton,5.0);
        root.getChildren().addAll(regibox,repassButton);

        Scene loginScene = new Scene(root, 500, 300);
        loginScene.getStylesheets().add(Objects.requireNonNull(LoginApp.class.getResource("/css/login.css")).toExternalForm());

        DragWindowHandler handler = new DragWindowHandler(primaryStage);
        loginScene.setOnMousePressed(handler);
        loginScene.setOnMouseDragged(handler);
        loginScene.setOnMouseClicked(event -> {
            loginPane.requestFocus();
        });
        loginPane.requestFocus();
        this.loginScene = loginScene;


        //点击登陆后加载组件
        Image loading = new Image(Objects.requireNonNull(LoginApp.class.getResourceAsStream("/images/loading3.gif")));
        ImageView imageView = new ImageView(loading);
        StackPane stackPane = new StackPane();
        StackPane stackPane1 = new StackPane();
        stackPane1.getChildren().add(imageView);
        imageView.setFitHeight(175);
        imageView.setFitWidth(175);

        Label msg = new Label();
        msg.setPrefWidth(140);

        root.getChildren().addAll(stackPane1,msg);
        stackPane1.setVisible(false);
        msg.setVisible(false);

        stackPane1.setId("stackPane1");
        msg.setId("msg");
        AnchorPane.setRightAnchor(stackPane1,250-87.5);
        AnchorPane.setBottomAnchor(stackPane1,170-87.5);
        AnchorPane.setRightAnchor(msg,250-75.0);
        AnchorPane.setBottomAnchor(msg,60.0);

        //点击登陆后的流程
        loginButton.setOnAction(e -> {

            String email = userNameField.getText();
            String password = passwordField.getText();
            //chatgpt优化后
            if (email.isEmpty() && password.isEmpty()) {
                Shake.ShakeNode(userNameField);
                Shake.ShakeNode(passwordField);
                return;
            }
            if (email.isEmpty()) {
                Shake.ShakeNode(userNameField);
                return;
            }
            if (password.isEmpty()) {
                Shake.ShakeNode(passwordField);
                return;
            }

            System.out.println(userNameField.getText() + passwordField.getText());
            Logindata logindata = new Logindata(email,password);
            Control.LoginControl(e,logindata,root);
        });
    }
}

