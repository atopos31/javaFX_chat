package com.example.chat.control.logincontrol;


import com.example.chat.scene.ChatPage;
import com.example.chat.server.Server;
import com.example.chat.struct.JSONdeal;
import com.example.chat.struct.Logindata;
import com.example.chat.struct.Response.LRreponse;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Control {
    public static Server Server = new Server();;
    public static void ReSetFouse(KeyEvent keyEvent,TextField textField) {
        String keycode = keyEvent.getCode().getName();
        if(keycode.equals(KeyCode.ENTER.getName())) {
            textField.requestFocus();
        }
    }

    public static void ReSetFouse(KeyEvent keyEvent,Button button) {
        String keycode = keyEvent.getCode().getName();
        if(keycode.equals(KeyCode.ENTER.getName())) {
            button.fire();
        }
    }

    public static void ReSetFouse(KeyEvent keyEvent, TextField textField, Button button) {
        String keycode = keyEvent.getCode().getName();
        if(keycode.equals(KeyCode.ENTER.getName())) {
            textField.requestFocus();
            button.fire();
        }
    }

    public static void LoginControl(ActionEvent event, Logindata logindata, AnchorPane root)  {
        Label msg = getmsg(root);
        Platform.runLater(()->{
            msg.setText("正在连接服务器！");
            msg.setStyle("-fx-text-fill: BLACK");
        });
        toload(root);
        Label finalMsg = msg;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String Message = new String();

                //发送并接受返回的信息
                try {
                    Server.getserver();
                    Message = Server.setClientMessage(JSONdeal.change(logindata));
                    System.out.println("Message=" + Message);
                } catch (IOException e) {
                    Platform.runLater(()->{
                        finalMsg.setText("服务器连接失败！");
                        finalMsg.setStyle("-fx-text-fill: #FF0000");
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    tologin(root);
                    throw new RuntimeException(e);
                }

                //处理信息
                LRreponse lRreponse = JSONdeal.toLRreponse(Message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                if(lRreponse.getCode() == 200) {
                    Platform.runLater(()->{
                        finalMsg.setText("      欢迎! " + lRreponse.getUsername());
                        finalMsg.setStyle("-fx-text-fill: GREEN");
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //加载聊天页面
                    Chatload(root,lRreponse);

                } else if(lRreponse.getCode() == 400) {
                    Platform.runLater(()->{
                        finalMsg.setText(lRreponse.getMsg());
                        finalMsg.setStyle("-fx-text-fill: #FF0000");
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    //返回
                    tologin(root);
                }
            }
        });
        thread.start();
    }

    //聊天页面加载
    static void Chatload (AnchorPane root ,LRreponse lRreponse) {
        Platform.runLater(()-> {
            Stage stage = (Stage) root.getScene().getWindow();
            ChatPage chatPage;
            try {
                chatPage = new ChatPage(lRreponse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.close();

            try {
                Server.handleChatMessages();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            chatPage.show();



        });
    }

    //找到消息显示标签
    static Label getmsg(AnchorPane root) {
        ObservableList<Node> children = root.getChildrenUnmodifiable();
        for (Node child : children) {
            if (Objects.equals(child.getId(), "msg")) {
                return (Label) child;
            }
        }
        return null;
    }

    //找到关闭按钮
    static Button closeButton(AnchorPane root) {
        ObservableList<Node> children = root.getChildrenUnmodifiable();
        for (Node child : children) {
            if (Objects.equals(child.getId(), "clo")) {
                return (Button) child;
            }
        }
        return null;
    }

    //进入加载界面
   static void toload(AnchorPane root) {
        Platform.runLater(()->{
            ObservableList<Node> children = root.getChildrenUnmodifiable();
            for (Node child : children) {
                if(Objects.equals(child.getId(), "stackPane1") || Objects.equals(child.getId(), "msg")){
                    child.setVisible(true);
                    continue;
                }
                if(Objects.equals(child.getId(), "title")) continue;
                child.setVisible(false);
            }
        });
    }

    //进入登录页面
    static void tologin(AnchorPane root) {
        ObservableList<Node> children = root.getChildrenUnmodifiable();
        for (Node child : children) {
            if(Objects.equals(child.getId(), "stackPane1")) {
                child.setVisible(false);
                continue;
            };
            if(Objects.equals(child.getId(), "msg")) {
                child.setVisible(false);
                continue;
            };
            child.setVisible(true);
        }
    }
}
