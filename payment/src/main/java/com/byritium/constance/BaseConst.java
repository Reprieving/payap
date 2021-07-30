package com.byritium.constance;

public class BaseConst {
    public static final String GATEWAY_URL = "https://app.liangyuekeji.cn";
    public static final String WECHATPAY_NOTICE_URL = GATEWAY_URL.concat("/transaction/notice/wechat");
    public static final String ALIPAY_NOTICE_URL = BaseConst.GATEWAY_URL.concat("/transaction/notice/alipay");
}
