package com.example.shixun.iotclient.message;

/**
 * Created by 15741 on 2019/7/5.
 */

public class EvenMsg {

    private String message;

    public  EvenMsg (String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
