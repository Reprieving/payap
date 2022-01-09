package com.byritium.advice;

import com.byritium.dto.ResponseBody;
import com.byritium.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setMessage("System error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> exceptionHandler(BusinessException e) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setCode(e.getCode());
        responseBody.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
