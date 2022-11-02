package com.byritium.dto.transaction;

import com.byritium.entity.transaction.PayOrder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionOrderMap<T> {
    private T transactionOrder;
    private PayOrder primaryPaymentOrder;
    private PayOrder couponPaymentOrder;
    private PayOrder discountPaymentOrder;
    private PayOrder virtualCurrencyPaymentOrder;
    private List<PayOrder> paymentOrderList;


    public TransactionOrderMap() {
        this.paymentOrderList = new ArrayList<>(10);
    }

    public void setPrimaryPaymentOrder(PayOrder payOrder){
        this.primaryPaymentOrder = payOrder;
        this.add(payOrder);
    }

    public void setCouponPaymentOrder(PayOrder payOrder){
        this.couponPaymentOrder = payOrder;
        this.add(payOrder);
    }

    public void setDiscountPaymentOrder(PayOrder payOrder){
        this.discountPaymentOrder = payOrder;
        this.add(payOrder);
    }

    public void setVirtualCurrencyPaymentOrder(PayOrder payOrder){
        this.virtualCurrencyPaymentOrder = payOrder;
        this.add(payOrder);
    }


    private void add(PayOrder payOrder){
        this.paymentOrderList.add(payOrder);
    }
}
