package com.example.chat.struct;

import com.example.chat.struct.JSON.JSONbase;
import com.example.chat.struct.unilt.timeuntil;

public class Logindata extends JSONbase {

    private String Email;

    private String Password;

    public Logindata(String email,String password) {
        this.Email = email;
        this.Password = password;
        this.Flag = com.example.chat.struct.JSON.Flag.FlagLogin;
        this.time = timeuntil.getime();
    }

}
