package com.byritium.exception;

import com.byritium.constance.ResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -2851132677704481659L;

    private Integer code;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }


    public BusinessException(String message) {
        this.code = ResponseCode.ERROR.getCode();
        this.message = message;
    }
}
