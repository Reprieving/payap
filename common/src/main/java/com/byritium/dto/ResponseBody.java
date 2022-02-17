package com.byritium.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBody<T> {
    private Integer code = 0;
    private T data;
    private String message;

    public ResponseBody() {

    }

    public ResponseBody<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public ResponseBody<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseBody<T> message(String message) {
        this.message = message;
        return this;
    }

    public boolean success() {
        return this.getCode() == 0;
    }


}
