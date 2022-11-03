package com.byritium.service.transaction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byritium.entity.transaction.WithdrawExamine;
import com.byritium.entity.transaction.WithdrawOrder;
import com.byritium.mapper.WithdrawExamineMapper;
import com.byritium.mapper.WithdrawOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class WithdrawExamineService extends ServiceImpl<WithdrawExamineMapper, WithdrawExamine> implements IService<WithdrawExamine> {

}
