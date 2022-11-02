package com.byritium.entity;

import com.byritium.constance.ObjectState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class CommonEntity {
    protected ObjectState os;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;

    protected void init() {
        this.os = ObjectState.USABLE;
        this.createTime = LocalDateTime.now();
    }
}
