package com.byritium.service.impl;

import com.byritium.dto.wechat.CertificateVo;
import com.byritium.dto.wechat.WechatPayConfig;
import com.byritium.exception.BusinessException;
import com.byritium.utils.GsonUtils;
import com.byritium.utils.MD5Util;
import com.byritium.utils.OkHttpUtils;
import com.byritium.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class WechatCommonService {
    private static final String URL_CERTIFICATES = "https://api.mch.weixin.qq.com/v3/certificates";

    protected Map<String, String> buildHeader(String method, String url, String body, String nonceStr) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        WechatPayConfig wechatPayConfig = new WechatPayConfig();
        String michId = wechatPayConfig.getMichId();
        String privateKeyPath = wechatPayConfig.getPrivateKeyPath();
        String certificateSerialNo = wechatPayConfig.getCertificateSerialNo();
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes(StandardCharsets.UTF_8), privateKeyPath);


        String token = "mchid=\"" + michId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + certificateSerialNo + "\","
                + "signature=\"" + signature + "\"";

        log.info("wechat pay token:{}", token);

        HashMap<String, String> map = new HashMap<>();
        map.put("Authorization", "WECHATPAY2-SHA256-RSA2048 " + token);
        map.put("Content-Type", "application/json");
        map.put("Accept", "application/json");
        return map;
    }

    private String buildMessage(String method, String url, long timestamp, String nonceStr, String body) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        Assert.notNull(httpUrl, "url空异常");
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

    protected String buildWechatSign(Map<String, String> map, String apiKey) {
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
     * get private key
     *
     * @param filename
     * @return
     */
    private PrivateKey getPrivateKey(String filename) throws IOException {
        String content = Files.readString(Paths.get(filename));
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

    public X509Certificate getCertificates() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Map<String, String> map = buildHeader(HttpMethod.POST.toString(), URL_CERTIFICATES, "", RandomUtils.uuid());
        map.put("User-Agent", "https://zh.wikipedia.org/wiki/User_agent");
        String result = OkHttpUtils.httpGet(URL_CERTIFICATES, map, null);

        X509Certificate x509Certificate = null;
        CertificateVo certificateVo = GsonUtils.deserializeEntity(result,CertificateVo.class);

//        try {
//            CertificateVo certificateVo = JSONObject.parseObject(EntityUtils.toString(response.getEntity()), CertificateVo.class);
//            for (Certificates certificates : certificateVo.getData()) {
//                if (format.parse(certificates.getEffective_time()).before(new Date()) && format.parse(certificates.getExpire_time()).after(new Date())) {
//                    EncryptCertificate encrypt_certificate = certificates.getEncrypt_certificate();
//                    //解密
//                    AesUtil aesUtil = new AesUtil(CommonParameters.apiV3Key.getBytes("utf-8"));
//                    String pulicKey = aesUtil.decryptToString(encrypt_certificate.getAssociated_data().getBytes("utf-8"), encrypt_certificate.getNonce().getBytes("utf-8"), encrypt_certificate.getCiphertext());
//
//　　　　　　　　　　　　　　 //获取平台证书
//                    final CertificateFactory cf = CertificateFactory.getInstance("X509");
//
//                    ByteArrayInputStream inputStream = new ByteArrayInputStream(pulicKey.getBytes(StandardCharsets.UTF_8));
//
//                    x509Certificate = (X509Certificate) cf.generateCertificate(inputStream);
//                }
//            }
//            return x509Certificate;
//
//        } catch (GeneralSecurityException | ParseException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            response.close();
//            CommonUtils.after(httpClient);
//        }

        return x509Certificate;
    }

}
