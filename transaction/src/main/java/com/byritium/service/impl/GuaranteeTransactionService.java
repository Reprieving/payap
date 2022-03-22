package com.byritium.service.impl;

import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionType;
import com.byritium.dto.LiquidationParam;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.rpc.LiquidationRpc;
import com.byritium.service.ITransactionService;
import com.byritium.service.common.TransactionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
@Slf4j
public class GuaranteeTransactionService implements ITransactionService {
    @Override
    public TransactionType type() {
        return TransactionType.GUARANTEE;
    }

    @Resource
    private TransactionOrderService transactionOrderService;

    @Resource
    private LiquidationRpc liquidationRpc;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionOrderService.trade(clientId, param);

        if (PaymentState.PAYMENT_SUCCESS == transactionResult.getPaymentState()) {
            LiquidationParam liquidationParam = new LiquidationParam();
            liquidationRpc.payment(liquidationParam);
        }

        return transactionResult;
    }

}
