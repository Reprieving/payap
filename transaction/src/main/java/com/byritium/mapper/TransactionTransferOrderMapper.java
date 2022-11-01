package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.TransactionFreezeOrder;
import com.byritium.entity.transaction.TransactionTransferOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionTransferOrderMapper extends BaseMapper<TransactionTransferOrder> {
}
