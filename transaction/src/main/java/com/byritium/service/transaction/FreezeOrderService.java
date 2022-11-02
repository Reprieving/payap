package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.mapper.FreezeOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class FreezeOrderService extends ServiceImpl<FreezeOrderMapper, FreezeOrder> implements IService<FreezeOrder> {
}
