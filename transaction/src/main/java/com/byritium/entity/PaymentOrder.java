package com.byritium.entity;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentProduct;
import com.byritium.constance.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class PaymentOrder {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "client_id", unique = true, nullable = false, length = 32)
    private String clientId;

    @Column(name = "business_order_id", unique = true, nullable = false, length = 32)
    private String businessOrderId;

    @Column(name = "transaction_order_id", unique = true, nullable = false, length = 32)
    private String transactionOrderId;

    @Column(name = "payer_id", unique = true, nullable = false, length = 32)
    private String payerId;

    @Column(name = "payee_id", unique = true, nullable = false, length = 32)
    private String payeeId;

    @Column(name = "subject", nullable = false, length = 64)
    private String subject;

    @Column(name = "order_amount", nullable = false, length = 64)
    private BigDecimal orderAmount;

    @Column(name = "pay_amount", nullable = false, length = 64)
    private BigDecimal payAmount;

    @Column(name = "payment_channel", nullable = false, length = 64)
    private PaymentChannel paymentChannel;

    @Column(name = "transaction_product", nullable = false, length = 64)
    private TransactionProduct transactionProduct;

    @Column(name = "payment_product", nullable = false)
    private PaymentProduct paymentProduct;

    @Column(name = "payment_state", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

}
