package com.example.chat.struct;

public class ReceiveMessage {
    String Flag;
    String Email;

    String Message;

    public ReceiveMessage() {
        this.Flag = com.example.chat.struct.JSON.Flag.FlagReceiveMessage;
    }

    public String getMessage() {
        return this.Message;
    }

    public String getEmail() {
        return this.Email;
    }
}
