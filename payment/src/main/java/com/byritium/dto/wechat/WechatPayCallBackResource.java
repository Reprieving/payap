package com.byritium.dto.wechat;

import lombok.Data;

@Data
public class WechatPayCallBackResource {
    private String algorithm;
    private String ciphertext;
    private String associated_data;
    private String original_type;
    private String nonce;

}
