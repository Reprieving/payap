package com.byritium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.PayOrder;
import com.byritium.mapper.PayOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrderService extends ServiceImpl<PayOrderMapper, PayOrder> implements IService<PayOrder> {

}
