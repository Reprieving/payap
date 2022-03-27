package com.byritium.service.common;

import com.byritium.dto.ResponseBody;
import com.byritium.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class RpcRspService<T> {

    public T get(ResponseBody<T> responseBody) {
        if (!responseBody.success()) {
            throw new BusinessException(responseBody.getMessage());
        }
        return responseBody.getData();
    }
}
