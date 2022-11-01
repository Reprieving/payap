package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.TransactionTransferOrder;
import com.byritium.entity.transaction.TransactionUnfreezeOrder;
import com.byritium.mapper.TransactionTransferOrderMapper;
import com.byritium.mapper.TransactionUnfreezeOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class TransactionTransferOrderService extends ServiceImpl<TransactionTransferOrderMapper, TransactionTransferOrder> implements IService<TransactionTransferOrder> {
}
