package com.byritium.constance;

import lombok.Getter;

@Getter
public enum TransactionProduct {
    GUARANTEE("GUARANTEE", "担保交易"),
    INSTANT("INSTANT", "即时交易"),
    GOON("GOON", "继续支付"),
    SETTLE("SETTLE", "结算"),
    QUERY("QUERY", "查询"),
    REFUND("REFUND", "退款"),
    FREEZE("FREEZE", "冻结"),
    UNFREEZE("UNFREEZE", "解冻"),
    WITHDRAW("WITHDRAW", "提现"),
    ;
    private String type;
    private String message;

    TransactionProduct(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
