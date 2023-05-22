package com.example.chat.struct;

public class SendMessage {
    String Flag;
    String Toemail;

    String Message;

    public SendMessage() {
        this.Flag = com.example.chat.struct.JSON.Flag.FlagSendMessage;
    }

    public void setToemail(String toemail) {
        this.Toemail = toemail;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

}
