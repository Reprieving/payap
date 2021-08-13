package com.byritium.service.impl;


import com.byritium.constance.BaseConst;
import com.byritium.constance.InterfaceProvider;
import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.dto.PayParam;
import com.byritium.dto.PaymentExtra;
import com.byritium.dto.SSLRequest;
import com.byritium.dto.WechatPayConfig;
import com.byritium.dto.wechat.WechatPayResult;
import com.byritium.dto.wechat.WechatRefundAmount;
import com.byritium.dto.wechat.WechatRefundRequest;
import com.byritium.dto.wechat.WechatWithdrawRequest;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.service.QueryService;
import com.byritium.service.RefundService;
import com.byritium.service.WithdrawService;
import com.byritium.utils.MD5Util;
import com.byritium.utils.OkHttpUtils;
import com.byritium.utils.RandomUtils;
import com.byritium.utils.XmlUtils;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class WechatPayService implements PayService, RefundService, WithdrawService, QueryService {
    protected Map<String, String> buildHeader(String method, String url, String body, String nonceStr, String michId, String certificateSerialNo, String privateKeyPath) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes(StandardCharsets.UTF_8), privateKeyPath);


        String token = "mchid=\"" + michId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + certificateSerialNo + "\","
                + "signature=\"" + signature + "\"";

        log.info("wechat pay token:{}", token);

        return ImmutableMap.of(
                "Authorization", "WECHATPAY2-SHA256-RSA2048 " + token,
                "Content-Type", "application/json",
                "Accept", "application/json"
        );
    }

    private String buildMessage(String method, String url, long timestamp, String nonceStr, String body) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        String canonicalUrl = httpUrl.encodedPath();
        if (httpUrl.encodedQuery() != null) {
            canonicalUrl += "?" + httpUrl.encodedQuery();
        }
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    private String sign(byte[] message, String privateKeyPath) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(getPrivateKey(privateKeyPath));
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public String buildWechatSign(Map<String, String> map, String apiKey) {
        try {
            String result;
            {
                List<Map.Entry<String, String>> infoIds = new ArrayList<>(map.entrySet());
                // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
                infoIds.sort(Map.Entry.comparingByKey());
                // 构造签名键值对的格式
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> item : infoIds) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!val.equals("")) {
                        sb.append(key).append("=").append(val).append("&");
                    }
                }
                sb.append("key").append("=").append(apiKey);
                result = sb.toString();
                result = MD5Util.MD5Encode(result).toUpperCase();
                return result;
            }
        } catch (Exception e) {
            throw new BusinessException("签名异常");
        }

    }

    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    private PrivateKey getPrivateKey(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    @Override
    public PaymentProduct product() {
        return PaymentProduct.PLATFORM_ONLINE_PAY;
    }

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.WECHAT_PAY;
    }

    @Override
    public void refund(String businessOrderId, String refundOrderId, BigDecimal orderAmount, BigDecimal refundAmount, PaymentExtra paymentExtra) {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        String url = wechatPayConfig.getRefundUrl();

        String michId = wechatPayConfig.getMichId();
        String nonceStr = RandomUtils.uuid();
        String certificateSerialNo = wechatPayConfig.getCertificateSerialNo();
        String privateKeyPath = wechatPayConfig.getPrivateKeyPath();

        WechatRefundRequest wechatRefundRequest = new WechatRefundRequest();
        wechatRefundRequest.setOut_trade_no(businessOrderId);
        wechatRefundRequest.setOut_refund_no(refundOrderId);
        wechatRefundRequest.setNotify_url(BaseConst.WECHATPAY_NOTICE_URL);
        wechatRefundRequest.setAmount(new WechatRefundAmount(orderAmount, refundAmount));

        Gson gson = new Gson();
        String json = gson.toJson(wechatRefundRequest);
        String str;

        try {
            Map<String, String> map = buildHeader("POST", url, json, nonceStr, michId, certificateSerialNo, privateKeyPath);
            str = OkHttpUtils.httpPostJson(url, map, json);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException e) {
            log.error("微信支付构建请求头失败，json:{}", json);
            throw new BusinessException("微信支付渠道退款失败");
        }

        WechatPayResult wechatPayResult = gson.fromJson(str, WechatPayResult.class);
        if (!StringUtils.hasText(wechatPayResult.getCode())) {
            log.error("微信支付退款失败，参数：{}", str);
            throw new BusinessException("支付失败");
        }
    }

    @Override
    public void withdraw(String businessOrderId, String sdkId, BigDecimal amount, PaymentExtra paymentExtra) {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();

        String url = wechatPayConfig.getWithdrawUrl();
        String appId = wechatPayConfig.getAppId();
        String apiKey = wechatPayConfig.getApiKey();
        String michId = wechatPayConfig.getMichId();
        String nonceStr = RandomUtils.uuid();
        String certificateSerialNo = wechatPayConfig.getCertificateSerialNo();
        String privateKeyPath = wechatPayConfig.getPrivateKeyPath();

        String p12Path = wechatPayConfig.getP12Path();

        WechatWithdrawRequest wechatWithdrawRequest = new WechatWithdrawRequest();
        wechatWithdrawRequest.setMch_appid(appId);
        wechatWithdrawRequest.setMchid(michId);
        wechatWithdrawRequest.setNonce_str(nonceStr);
        wechatWithdrawRequest.setPartner_trade_no(businessOrderId);
        wechatWithdrawRequest.setOpenid(sdkId);
        wechatWithdrawRequest.setCheck_name("NO_CHECK");
        wechatWithdrawRequest.setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue());
        wechatWithdrawRequest.setDesc("提现");

        Gson gson = new Gson();

        String json = gson.toJson(wechatWithdrawRequest);

        String str;

        Map<String, String> map;
        Map<String, String> param;

        try {
            map = buildHeader("POST", url, json, nonceStr, michId, certificateSerialNo, privateKeyPath);

            param = gson.fromJson(json, TypeToken.getParameterized(Map.class, String.class, String.class).getType());
            String sign = buildWechatSign(param, apiKey);
            param.put("sign", sign);
            String xml = XmlUtils.mapToXml(param, false);

            SSLRequest sslRequest = new SSLRequest(InterfaceProvider.WECHAT_PAY, p12Path, michId);
            str = OkHttpUtils.httpPostXml(url, map, xml, sslRequest);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | IOException e) {
            log.error("微信支付构建请求头失败，json:{}", json);
            throw new BusinessException("微信支付渠道提现失败");
        }

        Map<String, String> resultMap = XmlUtils.xmlToMap(str);
        log.info("微信支付提现结果：{}", gson.toJson(resultMap));
        String resultCode = resultMap.get("result_code");
        if (!"SUCCESS".equals(resultCode)) {
            log.error("微信支付提现失败，参数：{}", gson.toJson(resultMap));
            throw new BusinessException(resultMap.get("return_msg"));
        }

    }

}
