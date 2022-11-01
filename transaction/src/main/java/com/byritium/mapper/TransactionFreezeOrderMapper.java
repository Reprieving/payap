package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.entity.transaction.TransactionRechargeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.transaction.Transaction;

@Mapper
public interface TransactionFreezeOrderMapper extends BaseMapper<TransactionFreezeOrder> {
}
