package com.byritium.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliPayConfig {
    private String appId;
    private String privateKey;
    private String appCertPath;
    private String alipayCertPath;
    private String alipayRootCertPath;

}
