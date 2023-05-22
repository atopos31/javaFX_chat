package com.example.chat.struct;


public class SendEmail  {
    String Flag;
    String Email;

    public SendEmail () {
        this.Flag = com.example.chat.struct.JSON.Flag.FlagSendEmail;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}
