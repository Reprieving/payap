package com.byritium.utils;


import io.micrometer.core.instrument.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class WechatUtils {
    static final int KEY_LENGTH_BYTE = 32;
    static final int TAG_LENGTH_BIT = 128;

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

    /**
     * AES解密（GCM）
     *
     * @param aesKey
     * @param associatedData
     * @param nonce
     * @param ciphertext
     * @return
     * @throws GeneralSecurityException
     */
    public static String aseGSMDecrypt(String aesKey, String associatedData, String nonce, String ciphertext)
            throws GeneralSecurityException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)),
                    StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * AES加密(PKCS7)
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String asePKCS7Encrypt(String data, String key) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(MD5Util.MD5Encode(key).getBytes(), "AES");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密(PKCS7)
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String asePKCS7Decrypt(String base64Data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(MD5Util.MD5Encode(key).getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(base64Data)));
    }

    /**
     * map排序
     *
     * @param paraMap
     * @param urlEncode
     * @param keyToLower
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<>(paraMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            infoIds.sort(Map.Entry.comparingByKey());
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase()).append("=").append(val);
                    } else {
                        buf.append(key).append("=").append(val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (!buff.isEmpty()) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

}
