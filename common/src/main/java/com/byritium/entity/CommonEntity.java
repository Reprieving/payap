package com.byritium.entity;

import com.byritium.constance.ObjectState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommonEntity {
    protected ObjectState os;
    protected LocalDateTime crateTime;
    protected LocalDateTime updateTime;
}
