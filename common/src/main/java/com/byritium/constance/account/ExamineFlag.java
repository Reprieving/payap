package com.byritium.constance.account;

import lombok.Getter;

@Getter
public enum ExamineFlag {
    PENDING("ANDROID", "处理中"),
    APPROVED("APPROVED", "通过"),
    REJECT("REJECT", "驳回"),
    ;
    private final String flag;
    private final String message;

    ExamineFlag(String state, String message) {
        this.flag = state;
        this.message = message;
    }

    public String type() {
        return this.flag;
    }


}
