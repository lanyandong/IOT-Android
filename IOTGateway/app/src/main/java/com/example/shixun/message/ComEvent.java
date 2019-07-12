package com.example.shixun.message;

/**
 * Created by 15741 on 2019/7/3.
 */

public class ComEvent {
    private String message;

    public  ComEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
