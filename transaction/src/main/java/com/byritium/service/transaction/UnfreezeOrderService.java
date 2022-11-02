package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.UnfreezeOrder;
import com.byritium.mapper.UnfreezeOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class UnfreezeOrderService extends ServiceImpl<UnfreezeOrderMapper, UnfreezeOrder> implements IService<UnfreezeOrder> {
}
