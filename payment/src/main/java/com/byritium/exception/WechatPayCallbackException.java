package com.byritium.exception;

public class WechatPayCallbackException extends RuntimeException {


    private static final long serialVersionUID = 4192524769537673046L;

    public WechatPayCallbackException(){
        super();
    }

    public WechatPayCallbackException(String message){
        super(message);
    }
}
