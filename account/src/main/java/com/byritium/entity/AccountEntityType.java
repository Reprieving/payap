package com.byritium.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class AccountEntityType {
    @Id
    private String id;
    private String typeName;
    private Boolean initFlag;
}
