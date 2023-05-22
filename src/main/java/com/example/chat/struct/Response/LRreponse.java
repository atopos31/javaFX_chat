package com.example.chat.struct.Response;

public class LRreponse {
    private int Code;
    private String Msg;
    private String Username;

    private String Email;

    public LRreponse() {}

    public void setCode(int code) {
        this.Code = code;
    }

    public void setMsg(String msg) {
        this.Msg = msg;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getUsername() {
        return this.Username;
    }
    public int getCode() {
        return this.Code;
    }

    public String getMsg() {
        return this.Msg;
    }

    public String getEmail() {
        return this.Email;
    }
}
