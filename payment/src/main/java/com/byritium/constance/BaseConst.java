package com.byritium.constance;

public class BaseConst {
    public static final String GATEWAY_URL = "";
    public static final String WECHATPAY_NOTICE_URL = GATEWAY_URL.concat("/payment/notice/wechat");
    public static final String ALIPAY_NOTICE_URL = GATEWAY_URL.concat("/payment/notice/alipay");
}
