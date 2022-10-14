package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.TransactionPaymentOrder;
import com.byritium.entity.transaction.TransactionTradeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionPaymentOrderMapper extends BaseMapper<TransactionPaymentOrder> {
}
