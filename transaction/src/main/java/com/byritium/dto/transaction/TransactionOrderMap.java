package com.byritium.dto.transaction;

import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionOrderMap {
    private TransactionTradeOrder transactionTradeOrder;
    private TransactionPaymentOrder primaryPaymentOrder;
    private TransactionPaymentOrder couponPaymentOrder;
    private TransactionPaymentOrder discountPaymentOrder;
    private TransactionPaymentOrder virtualCurrencyPaymentOrder;
    private List<TransactionPaymentOrder> paymentOrderList;


    public TransactionOrderMap() {
        this.paymentOrderList = new ArrayList<>(10);
    }

    public void setPrimaryPaymentOrder(TransactionPaymentOrder transactionPaymentOrder){
        this.primaryPaymentOrder = transactionPaymentOrder;
        this.add(transactionPaymentOrder);
    }

    public void setCouponPaymentOrder(TransactionPaymentOrder transactionPaymentOrder){
        this.couponPaymentOrder = transactionPaymentOrder;
        this.add(transactionPaymentOrder);
    }

    public void setDiscountPaymentOrder(TransactionPaymentOrder transactionPaymentOrder){
        this.discountPaymentOrder = transactionPaymentOrder;
        this.add(transactionPaymentOrder);
    }

    public void setVirtualCurrencyPaymentOrder(TransactionPaymentOrder transactionPaymentOrder){
        this.virtualCurrencyPaymentOrder = transactionPaymentOrder;
        this.add(transactionPaymentOrder);
    }


    private void add(TransactionPaymentOrder transactionPaymentOrder){
        this.paymentOrderList.add(transactionPaymentOrder);
    }
}
