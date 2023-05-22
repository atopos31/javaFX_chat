package com.example.chat.server;

import com.example.chat.control.chatcontrol.Control;
import com.example.chat.struct.JSON.Flag;
import com.example.chat.struct.ReceiveMessage;
import com.example.chat.struct.Response.FRRreponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public  class Server {
    public Socket socket;
    public static PrintWriter writer;
    private BufferedReader reader;
    public void getserver() throws IOException {
        socket = new Socket(ServerConfig.URL,ServerConfig.PORT);
        System.out.println("连接服务器成功！");
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String setClientMessage (String Text){
        try {
            writer.println(Text);
            System.out.println("到这里啦");
            Thread.sleep(3000);
            String resp = reader.readLine();
            System.out.println("resp = " + resp);
            return resp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() throws IOException, InterruptedException {
        writer.println("exit");
        Thread.sleep(1000);
        socket.close();
    }

    public Socket GetSocket() {
        return socket;
    }

    public void handleChatMessages() throws IOException {
        System.out.println("开始监听啦！");
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                Gson gson = new Gson();
                while (true) {
                    try {
                        if ((message = reader.readLine()) == null) break;
                        System.out.println("退出啦");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("收到服务端消息:" + message);


                    JsonElement element = gson.fromJson(message, JsonElement.class);
                    String flag = JsonPath.read(element.toString(), "$.Flag");

                    if(flag.equals(Flag.FlagFriendRequire)) {
                        FRRreponse rreponse =  gson.fromJson(message,FRRreponse.class);
                        Control.Loadfriendsinit(rreponse);
                    } else if(flag.equals("TEST")) {
                        System.out.println("收到测试数据");
                    } else if(flag.equals(Flag.FlagReceiveMessage)) {
                        ReceiveMessage receiveMessage = gson.fromJson(message,ReceiveMessage.class);
                        Control.setMessage(receiveMessage.getEmail(),receiveMessage.getMessage());
                    }


                }
            }
        });
        thread.start();

    }


}
