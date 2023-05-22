package com.example.chat.struct;

import com.example.chat.struct.Response.LRreponse;
import com.google.gson.Gson;

public class JSONdeal {
    public static <T> String change(T data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static LRreponse toLRreponse(String message) {

        Gson gson = new Gson();
        return gson.fromJson(message,LRreponse.class);
    }
}
