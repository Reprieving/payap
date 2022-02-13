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

        //TODO coupon


        //TODO deduction



        transactionOrderRepository.save(transactionOrder);





//        switch (param.getTransactionType()) {
//            case GUARANTEE:
//
//
//
//                break;
//
//            case INSTANT:
//                break;
//
//            default:
//                throw new BusinessException("error tx type");
//        }
        return null;
    }
}
