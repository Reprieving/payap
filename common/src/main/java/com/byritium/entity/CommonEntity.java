package com.byritium.entity;

import com.byritium.constance.ObjectState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommonEntity {
    protected ObjectState os;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;

    protected void init() {
        this.os = ObjectState.USABLE;
    }
}
