package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.mapper.TransactionFreezeOrderMapper;
import com.byritium.mapper.TransactionPaymentOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class TransactionFreezeOrderService extends ServiceImpl<TransactionFreezeOrderMapper, TransactionFreezeOrder> implements IService<TransactionFreezeOrder> {
}
