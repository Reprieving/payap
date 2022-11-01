package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.entity.transaction.TransactionUnfreezeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionUnfreezeOrderMapper extends BaseMapper<TransactionUnfreezeOrder> {
}
