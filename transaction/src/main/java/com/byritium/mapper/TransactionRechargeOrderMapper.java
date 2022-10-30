package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.TransactionRechargeOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionRechargeOrderMapper extends BaseMapper<TransactionRechargeOrder> {
}