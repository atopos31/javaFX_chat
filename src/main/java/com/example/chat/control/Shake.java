package com.example.chat.control;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    public static void ShakeNode(Node node) {
        // 换色
        node.setStyle("-fx-border-color: red");
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(500));
        pauseTransition.setOnFinished(event -> node.setStyle(""));

        // 创建抖动动画
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0);
        translateTransition.setToX(10);
        translateTransition.setCycleCount(4);
        translateTransition.setAutoReverse(true);

        // 加载动画效果
        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition);

        pauseTransition.play();
        sequentialTransition.play();
    }
}
