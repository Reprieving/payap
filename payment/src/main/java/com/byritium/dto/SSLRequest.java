package com.byritium.dto;

import com.byritium.constance.InterfaceProvider;
import lombok.Data;

@Data
public class SSLRequest {
    private InterfaceProvider interfaceProvider;
    private String certPath;
    private String certPassword;

    public SSLRequest(InterfaceProvider interfaceProvider, String certPath, String certPassword) {
        this.interfaceProvider = interfaceProvider;
        this.certPath = certPath;
        this.certPassword = certPassword;
    }
}
