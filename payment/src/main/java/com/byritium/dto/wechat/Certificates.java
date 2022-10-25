package com.byritium.dto.wechat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Certificates {
    private String serial_no;
    private LocalDateTime effective_time;
    private LocalDateTime expire_time;
    private WechatPayCallBackResource encrypt_certificate;
}
