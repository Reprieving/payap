package com.byritium.constance;

import lombok.Getter;

@Getter
public enum TransactionType {
    GUARANTEE("GUARANTEE", "担保交易"),
    INSTANT("INSTANT", "即时交易"),
    SETTLE("SETTLE", "结算"),
    REFUND("REFUND", "退款"),
    WITHDRAW("WITHDRAW", "提现"),
    TRANSFER("TRANSFER", "转账"),
    FREEZE("FREEZE", "冻结"),
    UNFREEZE("UNFREEZE", "解冻"),
    ;
    private final String type;
    private final String message;

    TransactionType(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
