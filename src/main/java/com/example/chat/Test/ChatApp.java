package com.example.chat.Test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ChatApp extends Application {
    private ObservableList<String> friends = FXCollections.observableArrayList();
    private ListView<String> friendsList = new ListView<>(friends);

    private Map<String,BorderPane> chatMain = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        //左右总布局
        AnchorPane top = new AnchorPane();
        BorderPane left = new BorderPane();
        //插入头像
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(ChatApp.class.getResourceAsStream("/images/head.png"))));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        double radius = 20;
        Circle clipPath = new Circle(radius);
        clipPath.setCenterX(radius);
        clipPath.setCenterY(radius);
        imageView.setClip(clipPath);
        top.getChildren().add(imageView);
        //好友列表标签
        Label fr = new Label("好友列表：");
        top.getChildren().addAll(fr);
        top.setPrefHeight(60);
        AnchorPane.setBottomAnchor(fr,0.0);



        // 设置好友列表窗口
        friends.addAll("Alice", "Bob", "Charlie","hackerxiao");

        // 设置聊天窗口
        StackPane chatPane = new StackPane();

        left.setTop(top);
        left.setCenter(friendsList);
        left.setPadding(new Insets(10));
        // 将好友列表和聊天窗口组合在一起
        SplitPane root = new SplitPane();
        root.setOrientation(Orientation.HORIZONTAL);
        root.getItems().addAll(left, chatPane);
        root.setDividerPositions(0.2);

        // 为好友列表添加事件处理器
        friendsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal != null) {
                // 打开一个新的聊天窗口
                chatPane.getChildren().clear();
                // 查找聊天窗口是否已经存在
                var chatBox = chatMain.get(newVal);

                if (chatBox == null) {
                    chatBox = CreatChat(newVal);

                }
                chatPane.getChildren().add(chatBox);

            }
        });

        // 设置场景并显示窗口
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chat App");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.show();
    }

    public BorderPane CreatChat(String newVal) {
        BorderPane chatBox = new BorderPane();
        chatBox.setPadding(new Insets(10));

        //消息显示框
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        //输入框
        TextArea inputField = new TextArea();
        //发送按钮
        Button sendButton = new Button("发送");
        //发送按钮事件
        sendButton.setOnAction(new SendMessageHandler(chatArea, inputField, newVal));
        //将发送按钮和输入框装进Anchorpane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(inputField,sendButton);
        AnchorPane.setTopAnchor(inputField, 0.0);
        AnchorPane.setBottomAnchor(inputField, 0.0);
        AnchorPane.setLeftAnchor(inputField, 0.0);
        AnchorPane.setRightAnchor(inputField, 0.0);
        AnchorPane.setRightAnchor(sendButton, 0.0);
        AnchorPane.setBottomAnchor(sendButton, 0.0);
        //在线用户的名字
        chatBox.setTop(new Label(newVal));
        //数值布局的右侧
        VBox vBox =  new VBox(10, chatArea,anchorPane);
        chatBox.setCenter(chatArea);
        chatBox.setBottom(anchorPane);
        anchorPane.setPrefHeight(100);

        chatMain.put(newVal,chatBox);

        return chatBox;
    }

    private class SendMessageHandler implements EventHandler<ActionEvent> {
        private TextArea chatArea = new TextArea();
        private TextArea input;
        private String friend;

        public SendMessageHandler(TextArea chatArea, TextArea inputField, String friend) {
            this.chatArea = chatArea;
            this.input = inputField;
            this.friend = friend;
        }

        @Override
        public void handle(ActionEvent event) {
            String message =input.getText().trim();
            if (!message.isEmpty()) {
                // 在聊天窗口中添加消息
                chatArea.appendText("我: " + message + "\n");
                input.clear();

                // 模拟好友回复消息
                String reply = "回复" + friend + ": " + message + "\n";
                chatArea.appendText(reply);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}