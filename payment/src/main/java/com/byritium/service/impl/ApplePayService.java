package com.byritium.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.byritium.constance.PaymentChannel;
import com.byritium.dto.ClientInfo;
import com.byritium.dto.IdContainer;
import com.byritium.dto.PaymentResult;
import com.byritium.dto.applepay.ApplePayParam;
import com.byritium.dto.applepay.ApplePayRes;
import com.byritium.dto.applepay.Receipt;
import com.byritium.dto.applepay.ReceiptInApp;
import com.byritium.entity.payment.PaymentSetting;
import com.byritium.exception.BusinessException;
import com.byritium.service.PayService;
import com.byritium.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ApplePayService implements PayService {
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return false;
        }
    }

    @Override
    public PaymentChannel channel() {
        return PaymentChannel.APPLE_PAY;
    }

    @Override
    public PaymentResult pay(ClientInfo clientInfo, PaymentSetting setting, IdContainer idContainer, String subject, BigDecimal orderAmount) {
        ApplePayParam applePayParam = clientInfo.getApplePayParam();
        Integer environmentType = applePayParam.getEnvironmentType();
        String clientTransactionId = applePayParam.getTransactionId();

        String url = "";
        if (environmentType == 0) {
            url = "https://sandbox.itunes.apple.com/verifyReceipt"; //沙盒测试
        } else {
            url = "https://buy.itunes.apple.com/verifyReceipt"; //线上测试
        }
        String receiptData = "";
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "text/json");
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedOutputStream hurlBufOus = new BufferedOutputStream(conn.getOutputStream());

            Map<String, Object> map = new HashMap<>();
            map.put("receipt-data", receiptData);
            //自动续费产品加上password
            // map.put("password", "**************");
            hurlBufOus.write(GsonUtils.serialize(map).getBytes());
            hurlBufOus.flush();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String verifyResult = sb.toString();
            if (!StringUtils.hasText(verifyResult)) {
                throw new BusinessException("无订单信息!");
            }
            ApplePayRes res = GsonUtils.deserializeEntity(verifyResult, ApplePayRes.class);
            Integer status = res.getStatus();
            if (0 == status) {
                Receipt receipt = res.getReceipt();
                List<ReceiptInApp> inAppList = receipt.getInApp();
                if (inAppList == null || inAppList.size() == 0) {
                    throw new BusinessException("数据异常");
                }
                ReceiptInApp inApp = receipt.getInApp().get(inAppList.size() - 1);
                String transactionId = inApp.getTransactionId();
                String productId = inApp.getProductId();
                String inAppOwnershipType = inApp.getInAppOwnershipType();

                if (transactionId.equals(clientTransactionId) && inAppOwnershipType.equals("PURCHASED")) {

                }
            }


            return null;
        } catch (Exception ex) {
            System.out.println("苹果服务器异常");
            ex.printStackTrace();
        }
        return null;
    }

}
