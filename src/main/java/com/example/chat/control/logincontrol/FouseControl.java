package com.example.chat.control.logincontrol;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class FouseControl implements ChangeListener<Boolean> {
    Label label;
    public FouseControl(Label label) {
        this.label = label;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            label.setStyle("-fx-text-fill: #4d90fe;");
        } else {
            label.setStyle("");
        }
    }
}
