package com.example.chat.struct.Response;

import java.util.HashMap;
import java.util.Map;

public class FRRreponse {
    String Flag;
    public Map<String,String> onlineUser;

    public FRRreponse(Map<String,String> onlineUser) {
        this.Flag = com.example.chat.struct.JSON.Flag.FlagFriendRequire;
        this.onlineUser = new HashMap<>(onlineUser);
    }
}
