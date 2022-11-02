package com.byritium.service.transaction;

import com.byritium.entity.transaction.RechargeOrder;
import com.byritium.mapper.RechargeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeOrderService {

    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;

    public int save(RechargeOrder rechargeOrder) {
        return rechargeOrderMapper.insert(rechargeOrder);
    }


}
