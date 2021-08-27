package com.byritium.exception;

public class AliPayCallbackException extends RuntimeException {

    private static final long serialVersionUID = 8178755655900749716L;

    public AliPayCallbackException(){
        super();
    }

    public AliPayCallbackException(String message){
        super(message);
    }
}
