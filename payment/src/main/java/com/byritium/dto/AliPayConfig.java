package com.byritium.dto;

import lombok.Data;

@Data
public class AliPayConfig {
    private String appId;
    private String privateKey;
    private String appCertPath;
    private String alipayCertPath;
    private String alipayRootCertPath;

}
