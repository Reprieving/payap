package com.byritium.service;

import com.byritium.dao.TransactionOrderRepository;
import com.byritium.dto.TransactionParam;
import com.byritium.dto.TransactionResult;
import com.byritium.entity.TransactionOrder;
import com.byritium.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TransactionService {

    @Resource
    private TransactionOrderRepository transactionOrderRepository;

    public TransactionResult call(String clientId, TransactionParam param) {
        TransactionOrder transactionOrder = new TransactionOrder(clientId, param);
        String userId = param.getUserId();




        //TODO coupon
        String couponId = param.getCouponId();


        //TODO deduction
        String deductionId = param.getDeductionId();


        transactionOrderRepository.save(transactionOrder);

        return null;
    }
}
