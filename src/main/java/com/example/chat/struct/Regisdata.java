package com.example.chat.struct;

import com.example.chat.struct.JSON.JSONbase;
import com.example.chat.struct.unilt.timeuntil;

public class Regisdata extends JSONbase {
    private String Username;
    private String Password;
    private String Email;
    private String VerifyCode;

    public Regisdata(String username,String password,String email,String verifyCode) {
        this.Username = username;
        this.Password = password;
        this.Email = email;
        this.VerifyCode = verifyCode;
        this.Flag = com.example.chat.struct.JSON.Flag.FlagRegis;
        this.time = timeuntil.getime();
    }
}
