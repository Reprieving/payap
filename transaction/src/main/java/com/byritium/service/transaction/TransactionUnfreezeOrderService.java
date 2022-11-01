package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.entity.transaction.TransactionUnfreezeOrder;
import com.byritium.mapper.TransactionFreezeOrderMapper;
import com.byritium.mapper.TransactionUnfreezeOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class TransactionUnfreezeOrderService extends ServiceImpl<TransactionUnfreezeOrderMapper, TransactionUnfreezeOrder> implements IService<TransactionUnfreezeOrder> {
}
