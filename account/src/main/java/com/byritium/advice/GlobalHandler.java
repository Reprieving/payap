package com.byritium.advice;

import com.byritium.dto.ResponseBody;
import com.byritium.exception.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GlobalHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setData(o);
        return responseBody;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setMessage("System error");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseBody);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> exceptionHandler(BusinessException e) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setCode(e.getCode());
        responseBody.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }


}
