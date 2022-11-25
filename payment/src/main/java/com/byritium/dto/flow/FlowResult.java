package com.byritium.dto.flow;

import lombok.Data;

@Data
public class FlowResult<T> {
    private boolean flag;
    private T data;
}
