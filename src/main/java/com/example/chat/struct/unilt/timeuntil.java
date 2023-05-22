package com.example.chat.struct.unilt;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface timeuntil {
    public static String getime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        return formatter.format(date);
    }
}
