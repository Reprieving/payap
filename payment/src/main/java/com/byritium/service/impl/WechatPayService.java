package com.byritium.service.impl;


import com.byritium.dto.WechatPayConfig;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class WechatPayService {


    public Map<String, String> buildHeader(String method, String path, String body, String nonceStr, String michId, String certificateSerialNo, String privateKeyPath) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, path, timestamp, nonceStr, body);
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


    private String buildMessage(String method, String path, long timestamp, String nonceStr, String body) {
        return method + "\n"
                + path + "\n"
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

    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public PrivateKey getPrivateKey(String filename) throws IOException {
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
}
