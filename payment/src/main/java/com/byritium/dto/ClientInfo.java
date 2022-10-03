package com.byritium.dto;

import com.byritium.constance.ClientType;
import lombok.Data;

@Data
public class ClientInfo {
    private String ip;
    private ClientType clientType;
}
