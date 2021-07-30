package com.byritium.dto;

import lombok.Data;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Data
public class SSLContextDto {
    private SSLContext sslContext;
    private X509TrustManager x509TrustManager;

    public SSLContextDto() {
    }

    public SSLContextDto(SSLContext sslContext, X509TrustManager x509TrustManager) {
        this.sslContext = sslContext;
        this.x509TrustManager = x509TrustManager;
    }
}
