package com.byritium.dto;

import com.byritium.constance.ClientType;
import com.byritium.dto.applepay.ApplePayParam;
import lombok.Data;

@Data
public class ClientInfo {
    private String ip;
    private ClientType clientType;
    private ApplePayParam applePayParam;
}
