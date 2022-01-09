package com.byritium.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBody {
    private Integer code = 0;
    private Object data;
    private String message;

    public ResponseBody() {

    }

    public static ResponseBody build() {
        return new ResponseBody();
    }

    public ResponseBody code(Integer code) {
        this.code = code;
        return this;
    }

    public ResponseBody data(Object data) {
        this.data = data;
        return this;
    }

    public ResponseBody message(String message) {
        this.message = message;
        return this;
    }


}
