package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.TransferOrder;
import com.byritium.mapper.TransferOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class TransferOrderService extends ServiceImpl<TransferOrderMapper, TransferOrder> implements IService<TransferOrder> {
}
