package com.example.chat.scene;

import com.example.chat.LoginApp;
import com.example.chat.control.Shake;
import com.example.chat.control.logincontrol.Control;
import com.example.chat.control.logincontrol.DragWindowHandler;
import com.example.chat.control.logincontrol.FouseControl;
import com.example.chat.struct.Regisdata;
import com.example.chat.struct.SendEmail;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegisterPage {
    Stage primaryStage ;
    Scene loginScene;
    AnchorPane root = new AnchorPane();
    GridPane loginPane = new GridPane();
    Label titleLabel = new Label("欢迎注册");
    Label userNameLabel = new Label("用户名:");
    Label passwordLabel = new Label("密码:");
    Label enterPasswordLabel = new Label("确认密码:");
    Label emailLabel = new Label("邮箱:");
    Label verficodeLabel = new Label("验证码:");
    public Button loginButton = new Button("返回");
    public void show() {
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public RegisterPage(Stage Stage){
        this.primaryStage = Stage;
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(25, 25, 25, 25));

        titleLabel.setStyle("-fx-font-size: 24px;");
        loginPane.add(titleLabel, 0, 0, 2, 1);

        //用户名
        TextField userNameField = new TextField();
        loginPane.add(userNameField, 1, 1);

        userNameLabel.setLabelFor(userNameField);
        loginPane.add(userNameLabel, 0, 1);
        userNameField.focusedProperty().addListener(new FouseControl(userNameLabel));

        //密码
        PasswordField passwordField = new PasswordField();
        loginPane.add(passwordField, 1, 2);
        //空格跳转
        userNameField.setOnKeyPressed(event -> {Control.ReSetFouse(event,passwordField);});

        passwordLabel.setLabelFor(passwordField);
        loginPane.add(passwordLabel, 0, 2);
        passwordField.focusedProperty().addListener(new FouseControl(passwordLabel));

        //确认密码
        PasswordField enterwordField = new PasswordField();
        loginPane.add(enterwordField,1,3);
        //空格跳转
        passwordField.setOnKeyPressed(event -> {Control.ReSetFouse(event,enterwordField);});


        enterPasswordLabel.setLabelFor(enterwordField);
        loginPane.add(enterPasswordLabel,0,3);
        enterwordField.focusedProperty().addListener(new FouseControl(enterPasswordLabel));



        TextField email = new TextField();
        //邮箱提示语
        Label alert = new Label("");
        email.focusedProperty().addListener(new FouseControl(emailLabel));
        alert.setVisible(false);
        loginPane.add(alert,1,6);
        loginPane.add(email,1,4);
        loginPane.add(emailLabel,0,4);
        enterwordField.setOnKeyPressed(event -> {Control.ReSetFouse(event,email);});

        TextField verfiyCode = new TextField();
        verfiyCode.focusedProperty().addListener(new FouseControl(verficodeLabel));
        Button bt = new Button("发送验证码");
        bt.setId("sendverfiy");
        bt.setOnAction(event -> {
            System.out.println("fire!");
        });
        AnchorPane vanchorPane = new AnchorPane();
        vanchorPane.setPadding(new Insets(0));
        vanchorPane.setId("vanchorPane");
        vanchorPane.getChildren().addAll(verfiyCode,bt);
        AnchorPane.setRightAnchor(bt,0.0);
        loginPane.add(vanchorPane,1,5);
        loginPane.add(verficodeLabel,0,5);
        email.setOnKeyPressed(event -> {Control.ReSetFouse(event,verfiyCode,bt);});

        loginButton.setId("loginButton");

        Button registerButton = new Button("注册");
        registerButton.setId("registerButton");
        registerButton.setPrefWidth(200);
        verfiyCode.setOnKeyPressed(event -> {Control.ReSetFouse(event,registerButton);});

        AnchorPane anchorPane = new AnchorPane(registerButton);
        AnchorPane.setRightAnchor(registerButton,0.0);

        loginPane.add(registerButton, 0,6,2,6);
        loginPane.setId("loginPane");

        Button minButton = new Button("一");
        minButton.setId("min");
        minButton.setOnAction(event -> {
            primaryStage.setIconified(true);
        });
        Button closeButton = new Button("╳");
        closeButton.setId("clo");
        closeButton.setOnAction(event -> {
            primaryStage.close();
        });
        HBox title = new HBox();

        title.getChildren().addAll(minButton,closeButton);

        loginPane.setPrefHeight(300);
        loginPane.setPrefWidth(500);

        root.getChildren().add(loginPane);
        root.getChildren().add(loginButton);
        root.getChildren().add(title);

        AnchorPane.setTopAnchor(title,0.0);
        AnchorPane.setRightAnchor(title,0.0);
        AnchorPane.setBottomAnchor(loginButton,5.0);
        AnchorPane.setRightAnchor(loginButton,5.0);

        Scene loginScene = new Scene(root, 500, 300);
        loginScene.getStylesheets().add(LoginApp.class.getResource("/css/register.css").toExternalForm());

        DragWindowHandler handler = new DragWindowHandler(primaryStage);
        loginScene.setOnMousePressed(handler);
        loginScene.setOnMouseDragged(handler);
        loginScene.setOnMouseClicked(event -> {
            loginPane.requestFocus();
        });
        loginPane.requestFocus();
        this.loginScene = loginScene;

        bt.setOnAction(event -> {
            String emailstring = email.getText();
            if (emailstring.isEmpty()) {
                Shake.ShakeNode(email);
                return;
            }
            bt.setText("请稍后...");
            bt.setDisable(true);

            SendEmail sendEmail =new SendEmail();
            sendEmail.setEmail(emailstring);
            com.example.chat.control.regiscontrol.Control.SendEmailControl(sendEmail , bt ,alert);
        });

        registerButton.setOnAction(event -> {
            System.out.println("点击注册啦");
            String password = passwordField.getText();
            String enterpassword = enterwordField.getText();

            String username = userNameField.getText();
            if (username.isEmpty()) {
                Shake.ShakeNode(userNameField);

                return;
            }

            if(password.isEmpty()) {
                Shake.ShakeNode(passwordField);

                return;
            }

            if(enterpassword.isEmpty()) {
                Shake.ShakeNode(enterwordField);

                return;
            }

            String emailstring = email.getText();
            if (emailstring.isEmpty()) {
                Shake.ShakeNode(email);

                return;
            }

            String code = verfiyCode.getText();
            if (code.isEmpty()) {
                Shake.ShakeNode(verfiyCode);

                return;
            }

            if(!password.equals(enterpassword)) {
                Shake.ShakeNode(passwordField);
                Shake.ShakeNode(enterwordField);

                return;
            }

            registerButton.setText("请稍后...");
            registerButton.setDisable(true);

            Regisdata regisdata = new Regisdata(username,enterpassword,emailstring,code);
            com.example.chat.control.regiscontrol.Control.RegisControl(regisdata , registerButton ,alert);
        });
    }
}

