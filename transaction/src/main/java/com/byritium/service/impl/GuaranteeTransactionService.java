package com.byritium.service.impl;

import com.byritium.constance.PaymentChannel;
import com.byritium.constance.PaymentState;
import com.byritium.constance.TransactionConst;
import com.byritium.constance.TransactionType;
import com.byritium.dao.TransactionPayOrderRepository;
import com.byritium.dao.TransactionReceiptOrderRepository;
import com.byritium.dto.Deduction;
import com.byritium.dto.LiquidationParam;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactiontOrder;
import com.byritium.entity.TransactionPaymentOrder;
import com.byritium.rpc.LiquidationRpc;
import com.byritium.service.ITransactionService;
import com.byritium.service.TransactionWrapperService;
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
    private TransactionWrapperService transactionWrapperService;

    @Resource
    private LiquidationRpc liquidationRpc;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionResult transactionResult = transactionWrapperService.trade(clientId, param);

        if (PaymentState.PAYMENT_SUCCESS == transactionResult.getPaymentState()) {
            LiquidationParam liquidationParam = new LiquidationParam();
            liquidationRpc.payment(liquidationParam);
        }

        return transactionResult;
    }

}
