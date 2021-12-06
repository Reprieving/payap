package com.byritium.constance;

import lombok.Getter;

@Getter
public enum PaymentProduct {
    QUICK_PAY("QUICK_PAY", "快捷支付"),
    NET_BANK_PAY("NET_BANK_PAY", "网银支付"),
    PLATFORM_ONLINE_PAY("PLATFORM_ONLINE_PAY", "平台线上支付"),
    PLATFORM_OUTLINE_PAY("PLATFORM_OUTLINE_PAY", "平台线下支付"),
    PHONE_BILL_PAY("PHONE_BILL_PAY", "话费支付"),
    AGREEMENT_PAY("AGREEMENT_PAY", "协议支付"),
    IAP_PAY("IAP_PAY", "手机支付"),
    ACCOUNT_PAY("ACCOUNT_PAY", "账户支付"),
    VIRTUAL_CURRENCY_PAY("VIRTUAL_CURRENCY_PAY", "虚拟币支付"),
    CREDIT_PAY("CREDIT_PAY", "信用支付"),
    TRANSFER_PAY("TRANSFER_PAY", "代付支付"),
    ;
    private String product;
    private String message;

    PaymentProduct(String product, String message) {
        this.product = product;
        this.message = message;
    }
}
