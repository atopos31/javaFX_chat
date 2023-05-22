package com.example.chat.scene;

import com.example.chat.control.chatcontrol.Control;
import com.example.chat.control.logincontrol.DragWindowHandler;
import com.example.chat.server.Server;
import com.example.chat.struct.Response.LRreponse;
import com.example.chat.struct.SendMessage;
import com.google.gson.Gson;
import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatPage {
    LRreponse lRreponse;
    Stage primaryStage = new Stage();
    Scene chatScene;

    public static ObservableList<String> friends = FXCollections.observableArrayList();
    public static ListView<String> friendsList = new ListView<>(friends);
    public static Map<String,TextFlow> chattext = new HashMap<>();

    public static Map<String,BorderPane> chatMain = new HashMap<>();
    public static Map<String,ScrollPane> chatscoll = new HashMap<>();
    public static Map<String,String> USERandEMAil = new HashMap<>();

    public void show()  {

        DragWindowHandler handler = new DragWindowHandler(primaryStage);
        chatScene.setOnMousePressed(handler);
        chatScene.setOnMouseDragged(handler);

        primaryStage.setScene(chatScene);
        primaryStage.setTitle("Chat App");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.show();

        //加载在线用户
        Control.LoadfriendRequire();
    }


    public ChatPage(LRreponse lRreponse) throws IOException {
        this.lRreponse = lRreponse;

        //左右总布局
        AnchorPane top = new AnchorPane();
        BorderPane left = new BorderPane();
        //插入头像
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(ChatPage.class.getResourceAsStream("/images/head.png"))));
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        double radius = 20;
        Circle clipPath = new Circle(radius);
        clipPath.setCenterX(radius);
        clipPath.setCenterY(radius);
        imageView.setClip(clipPath);
        top.getChildren().add(imageView);
        //用户名标签
        Label username = new Label("用户名:"+lRreponse.getUsername());
//        Label email = new Label("邮箱:" + lRreponse.getEmail());
        top.getChildren().addAll(username);
        AnchorPane.setLeftAnchor(username,50.0);
//        AnchorPane.setLeftAnchor(email,50.0);
//        AnchorPane.setBottomAnchor(email,20.0);
        //好友列表标签

        Label fr = new Label("在线列表：");
        top.getChildren().addAll(fr);
        top.setPrefHeight(60);
        AnchorPane.setBottomAnchor(fr,0.0);

        // 设置聊天窗口
        StackPane chatPane = new StackPane();

        left.setTop(top);
        left.setCenter(friendsList);
        left.setMaxWidth(180);

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

                chatPane.getChildren().add(chatBox);

            }
        });

        primaryStage.setOnCloseRequest(event -> {
            primaryStage.close();
            try {
                com.example.chat.control.logincontrol.Control.Server.closeSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.exit();
        });

        // 设置场景并显示窗口
        chatScene= new Scene(root, 600, 400);
        chatScene.getStylesheets().add(LoginPage.class.getResource("/css/chat.css").toExternalForm());


    }
    public static BorderPane CreatChat(String newVal) {
        BorderPane chatBox = new BorderPane();
        chatBox.setPadding(new Insets(10));

        //消息显示框
        TextFlow chatArea = new TextFlow();

        chatArea.setPadding(new Insets(10));
        chatArea.setId("chatarea");
        chatArea.setBackground(Background.EMPTY);

        //输入框
        TextArea inputField = new TextArea();
        inputField.setPromptText("使用ctrl+回车发送！");
        KeyCodeCombination saveCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);


        //发送按钮
        Button sendButton = new Button("发送");

        sendButton.setId("send");
         //将发送按钮和输入框装进Anchorpane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(inputField,sendButton);
        AnchorPane.setTopAnchor(inputField, 0.0);
        AnchorPane.setBottomAnchor(inputField, 0.0);
        AnchorPane.setLeftAnchor(inputField, 0.0);
        AnchorPane.setRightAnchor(inputField, 0.0);
        AnchorPane.setRightAnchor(sendButton, 5.0);
        AnchorPane.setBottomAnchor(sendButton, 5.0);
        //在线用户的名字
        Label touser = new Label(newVal);
        touser.setFont(new Font(16));
        chatBox.setTop(touser);
        //数值布局的右侧
        VBox vBox =  new VBox(10, chatArea,anchorPane);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId("chatback");
        scrollPane.setContent(chatArea);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        inputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(saveCombination.match(event)) {
                    sendButton.fire();
                }
            }
        });

        //发送按钮事件
        sendButton.setOnAction(new ChatPage.SendMessageHandler(chatArea, inputField, newVal ,scrollPane));
        // 绑定宽度
        chatArea.prefWidthProperty().bind(scrollPane.widthProperty());

        // 绑定高度
        chatArea.prefHeightProperty().bind(scrollPane.heightProperty());

        chatBox.setCenter(scrollPane);
        anchorPane.setPadding(new Insets(10,0,0,0));
        chatBox.setBottom(anchorPane);
        anchorPane.setPrefHeight(100);

        chatMain.put(newVal,chatBox);
        System.out.println("chattext添加了" + USERandEMAil.get(newVal));
        chatscoll.put(USERandEMAil.get(newVal),scrollPane);
        chattext.put(USERandEMAil.get(newVal),chatArea);

        return chatBox;
    }

    public static class SendMessageHandler implements EventHandler<ActionEvent> {
        private TextFlow chatArea = new TextFlow();
        private TextArea input;
        private String friend;

        private ScrollPane scrollPane;

        public SendMessageHandler(TextFlow chatArea, TextArea inputField, String friend ,ScrollPane scrollPane) {
            this.scrollPane = scrollPane;
            this.chatArea = chatArea;
            this.input = inputField;
            this.friend = friend;
        }

        @Override
        public void handle(ActionEvent event) {
            String message =input.getText().trim();
            if (!message.isEmpty()) {

                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);

                Text text1 = new Text("[你] " + formattedDateTime + "\n");
                text1.setFont(new Font(10));
                text1.setFill(Paint.valueOf("#00FF7F"));
                Text text2 = new Text(message + "\n");
                text2.setFont(new Font(15));

                chatArea.getChildren().addAll(text1,text2);
                scrollPane.setVvalue(1.0);

                input.clear();

                SendMessage sendMessage = new SendMessage();
                sendMessage.setToemail(USERandEMAil.get(friend));
                sendMessage.setMessage(message);
                Gson gson = new Gson();
                Server.writer.println(gson.toJson(sendMessage));
            }
        }
    }
}
