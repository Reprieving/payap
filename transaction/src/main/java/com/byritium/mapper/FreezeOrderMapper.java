package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.FreezeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FreezeOrderMapper extends BaseMapper<FreezeOrder> {
}
