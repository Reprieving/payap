package com.byritium.constance;

import com.byritium.entity.payment.PaymentSetting;
import lombok.Getter;

@Getter
public enum PaymentPattern {//支付模式
    ONLINE_QUICK_ALIPAY_APP(PaymentChannel.ALI_PAY, PaymentScene.ONLINE, PaymentApplication.APP, PaymentProduct.QUICK_PAY, "支付宝线上APP快捷支付"),
    ONLINE_QUICK_ALIPAY_WAP(PaymentChannel.ALI_PAY, PaymentScene.ONLINE, PaymentApplication.WAP, PaymentProduct.QUICK_PAY, "支付宝线上WAP快捷支付"),
    ONLINE_QUICK_ALIPAY_PC(PaymentChannel.ALI_PAY, PaymentScene.ONLINE, PaymentApplication.PC, PaymentProduct.QUICK_PAY, "支付宝线上PC快捷支付"),
    OFFLINE_QUICK_ALIPAY_CREATE_CODE(PaymentChannel.ALI_PAY, PaymentScene.OFFLINE, PaymentApplication.CREATE_CODE, PaymentProduct.QUICK_PAY, "支付宝线下付款码快捷支付"),
    OFFLINE_QUICK_ALIPAY_SCAN_CODE(PaymentChannel.ALI_PAY, PaymentScene.OFFLINE, PaymentApplication.SCAN_CODE, PaymentProduct.QUICK_PAY, "支付宝线下扫码快捷支付"),
    ONLINE_AUTH_ALIPAY_APP(PaymentChannel.ALI_PAY, PaymentScene.ONLINE, PaymentApplication.APP, PaymentProduct.AUTH_PAY, "支付宝线上APP授权支付"),
    OFFLINE_AUTH_ALIPAY_CREATE_CODE(PaymentChannel.ALI_PAY, PaymentScene.OFFLINE, PaymentApplication.CREATE_CODE, PaymentProduct.AUTH_PAY, "支付宝线下付款码授权支付"),
    OFFLINE_AUTH_ALIPAY_SCAN_CODE(PaymentChannel.ALI_PAY, PaymentScene.OFFLINE, PaymentApplication.SCAN_CODE, PaymentProduct.AUTH_PAY, "支付宝线下扫码授权支付"),

    ONLINE_QUICK_WECHATPAY_APP(PaymentChannel.WECHAT_PAY, PaymentScene.ONLINE, PaymentApplication.APP, PaymentProduct.QUICK_PAY, "微信线上APP快捷支付"),
    ONLINE_QUICK_WECHATPAY_WAP(PaymentChannel.WECHAT_PAY, PaymentScene.ONLINE, PaymentApplication.WAP, PaymentProduct.QUICK_PAY, "微信线上WAP快捷支付"),
    ONLINE_QUICK_WECHATPAY_PC(PaymentChannel.WECHAT_PAY, PaymentScene.ONLINE, PaymentApplication.PC, PaymentProduct.QUICK_PAY, "微信线上PC快捷支付"),
    OFFLINE_QUICK_WECHATPAY_CREATE_CODE(PaymentChannel.WECHAT_PAY, PaymentScene.ONLINE, PaymentApplication.CREATE_CODE, PaymentProduct.QUICK_PAY, "微信线下付款码快捷支付"),
    OFFLINE_QUICK_WECHATPAY_SCAN_CODE(PaymentChannel.WECHAT_PAY, PaymentScene.ONLINE, PaymentApplication.SCAN_CODE, PaymentProduct.QUICK_PAY, "微信线下扫码快捷支付"),

    ONLINE_QUICK_UNION_APP(PaymentChannel.PAYPAL, PaymentScene.ONLINE, PaymentApplication.APP, PaymentProduct.QUICK_PAY, "云闪付线上快捷支付"),

    ONLINE_QUICK_PAYPAL_APP(PaymentChannel.PAYPAL, PaymentScene.ONLINE, PaymentApplication.APP, PaymentProduct.QUICK_PAY, "PAYPAL线上快捷支付"),

    ONLINE_QUICK_IOS_APP(PaymentChannel.APPLE_PAY, PaymentScene.ONLINE, PaymentApplication.APP, PaymentProduct.QUICK_PAY, "IOS线上快捷支付");



    private PaymentChannel channel;
    private PaymentScene scene;
    private PaymentApplication application;
    private PaymentProduct product;
    private final String message;

    PaymentPattern(String pattern, String message) {
        this.message = message;
    }

    PaymentPattern(PaymentChannel channel, PaymentScene scene, PaymentApplication application, PaymentProduct product, String message) {
        this.channel = channel;
        this.scene = scene;
        this.application = application;
        this.product = product;
        this.message = message;
    }

    public static PaymentPattern pattern(PaymentSetting setting) {
        return PaymentPattern.valueOf(String.valueOf(setting.getScene()) + setting.getChannel() + setting.getProduct() + setting.getApplication());
    }


}
