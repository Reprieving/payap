package com.byritium.dto.wechat;

import lombok.Data;

@Data
public class WechatPaySceneInfo {
    private String payer_client_ip;
    private WechatH5Info h5_info;

}
