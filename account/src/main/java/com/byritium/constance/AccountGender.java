package com.byritium.constance;

import lombok.Getter;

@Getter
public enum AccountGender {
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女"),

    ;
    private final String gender;
    private final String message;

    AccountGender(String gender, String message) {
        this.gender = gender;
        this.message = message;
    }
}
