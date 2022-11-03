package com.byritium.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byritium.entity.transaction.WithdrawExamine;
import com.byritium.entity.transaction.WithdrawOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WithdrawExamineMapper extends BaseMapper<WithdrawExamine> {
}
