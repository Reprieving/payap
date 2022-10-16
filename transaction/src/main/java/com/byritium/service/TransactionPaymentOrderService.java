package com.byritium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.mapper.TransactionPaymentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionPaymentOrderService extends ServiceImpl<TransactionPaymentOrderMapper, TransactionPaymentOrder> implements IService<TransactionPaymentOrder> {

    @Autowired
    private TransactionPaymentOrderMapper transactionPaymentOrderMapper;

}
