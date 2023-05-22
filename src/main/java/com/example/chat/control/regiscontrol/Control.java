package com.example.chat.control.regiscontrol;

import com.example.chat.server.Server;
import com.example.chat.struct.JSONdeal;
import com.example.chat.struct.Regisdata;
import com.example.chat.struct.Response.CommonReponse;
import com.example.chat.struct.SendEmail;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Control {
    public static com.example.chat.server.Server Server = new Server();;
    public static void RegisControl(Regisdata regisdata , Button regisbuttun , Label alert) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String Message = new String();
                try {
                    Server.getserver();
                    Message = Server.setClientMessage(JSONdeal.change(regisdata));

                    CommonReponse Reponse = gson.fromJson(Message,CommonReponse.class);
                    System.out.println("Message=" + Message);
                    if (Reponse.getCode() == 200) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                alert.setText(Reponse.getMsg());
                                alert.setStyle("-fx-text-fill: green");
                                regisbuttun.setDisable(false);
                                regisbuttun.setText("注册");
                            }
                        });
                    } else if (Reponse.getCode() == 400) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                alert.setText(Reponse.getMsg());
                                alert.setStyle("-fx-text-fill: red");
                                regisbuttun.setDisable(false);
                                regisbuttun.setText("注册");
                            }
                        });
                    }

                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            alert.setText("服务器连接失败！");
                            alert.setStyle("-fx-text-fill: red");
                            regisbuttun.setDisable(false);
                            regisbuttun.setText("注册");
                        }
                    });
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();
    }

    public static void SendEmailControl(SendEmail sendEmail , Button sendBt , Label alert) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String Message = new String();
                try {
                    Server.getserver();
                    Message = Server.setClientMessage(JSONdeal.change(sendEmail));

                    CommonReponse emailReponse = gson.fromJson(Message,CommonReponse.class);

                    System.out.println("code:" + emailReponse.getCode() + "Msg" + emailReponse.getMsg());
                    if(emailReponse.getCode() == 200) {
                        startCountdown(sendBt);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                alert.setVisible(true);
                                alert.setText(emailReponse.getMsg());
                                alert.setStyle("-fx-text-fill: green");
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                alert.setVisible(true);
                                alert.setText(emailReponse.getMsg());
                                alert.setStyle("-fx-text-fill: red");
                                sendBt.setText("发送验证码");
                                sendBt.setDisable(false);
                            }
                        });
                    }


                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            alert.setVisible(true);
                            alert.setText("服务器连接失败！");
                            alert.setStyle("-fx-text-fill: red");
                            sendBt.setText("发送验证码");
                            sendBt.setDisable(false);
                        }
                    });
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();
    }

    private static int countdown ;
    private static Timer timer ;

    private static void startCountdown(Button button) {
        timer = new Timer();
        countdown = 60;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> {
                    if (countdown > 0) {
                        button.setText(countdown + " 秒后重试");
                    } else {
                        timer.cancel();
                        button.setText("发送验证码");
                        button.setDisable(false);
                    }
                });
                --countdown;
            }
        }, 1000, 1000);

    }



}
