package com.byritium.service.transaction;

import com.byritium.entity.transaction.TradeOrder;
import com.byritium.mapper.TradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeOrderService {

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    public int save(TradeOrder tradeOrder) {
        return tradeOrderMapper.insert(tradeOrder);
    }


}
