package com.example.chat.control.chatcontrol;

import com.example.chat.scene.ChatPage;
import com.example.chat.server.Server;
import com.example.chat.struct.FriendRequire;
import com.example.chat.struct.Response.FRRreponse;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Control {
    public static void LoadfriendRequire() {
                Gson gson = new Gson();
                FriendRequire friendRequire = new FriendRequire();
                Server.writer.println(gson.toJson(friendRequire));
                System.out.println("发送获取在线用户的请求啦！");
    }
    public static void Loadfriendsinit(FRRreponse frRreponse){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<String, String> entry : frRreponse.onlineUser.entrySet()) {
                    String key = entry.getKey();
                    String Email = entry.getValue();
                    if(ChatPage.chatMain.get(key) == null){

                        ChatPage.USERandEMAil.put(key,Email);
                        ChatPage.friends.add(key);
                        ChatPage.CreatChat(key);
                    }
                }
                for (Map.Entry<String, BorderPane> entry : ChatPage.chatMain.entrySet()) {
                    String key = entry.getKey();
                    if(frRreponse.onlineUser.get(key) == null) {
                        ChatPage.USERandEMAil.remove(key);
                        ChatPage.chatMain.remove(key);
                        ChatPage.friends.remove(key);
                    }
                }
            }
        });
    }

    public static void setMessage(String email,String message) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);

                String targetKey = ChatPage.USERandEMAil.entrySet().stream().filter(entry -> entry.getValue().equals(email)).findFirst().map(Map.Entry::getKey).orElse(null);
                Platform.runLater(()->{
                    System.out.println("添加消息" + email + ":" + message);
                    Text text1 = new Text("[" + targetKey + "] "  + formattedDateTime+ "\n");
                    text1.setFill(Paint.valueOf("#00BFFF"));
                    text1.setFont(new Font(10));
                    Text text2 = new Text(message + "\n");
                    text2.setFont(new Font(15));
                    ChatPage.chattext.get(email).getChildren().addAll(text1,text2);
                    ScrollPane scrollPane = ChatPage.chatscoll.get(email);
                    if(scrollPane.getVvalue()>0.8) scrollPane.setVvalue(1.0);
                });

            }
        });
        thread.start();



    }
}
