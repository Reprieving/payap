package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.TransferOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransferOrderMapper extends BaseMapper<TransferOrder> {
}
