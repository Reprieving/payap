package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.entity.transaction.RechargeOrder;
import com.byritium.entity.transaction.WithdrawOrder;
import com.byritium.mapper.FreezeOrderMapper;
import com.byritium.mapper.RechargeOrderMapper;
import com.byritium.mapper.WithdrawOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawOrderService extends ServiceImpl<WithdrawOrderMapper, WithdrawOrder> implements IService<WithdrawOrder> {

}
