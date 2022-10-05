package com.byritium.dto.applepay;

import lombok.Data;

@Data
public class ReceiptInApp {
    private String quantity;
    private String productId;
    private String transactionId;
    private String originalTransactionId;
    private String inAppOwnershipType;
}
