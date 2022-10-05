package com.byritium.dto.applepay;

import lombok.Data;

import java.util.List;

@Data
public class Receipt {
    private String receiptType;
    private Integer adamId;
    private Integer appItemId;
    private String bundleId;
    private String applicationVersion;
    private Integer downloadId;
    private Integer versionExternalIdentifier;
    private List<ReceiptInApp> inApp;
}
