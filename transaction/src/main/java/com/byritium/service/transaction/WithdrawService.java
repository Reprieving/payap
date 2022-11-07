package com.byritium.service.transaction;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byritium.constance.account.ExamineFlag;
import com.byritium.dto.TransactionResult;
import com.byritium.dto.transaction.WithdrawParam;
import com.byritium.entity.transaction.FreezeOrder;
import com.byritium.entity.transaction.UnfreezeOrder;
import com.byritium.entity.transaction.WithdrawExamine;
import com.byritium.entity.transaction.WithdrawOrder;
import com.byritium.rpc.AccountRpc;
import com.byritium.rpc.PaymentRpc;
import com.byritium.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {
    @Autowired
    private WithdrawExamineService withdrawExamineService;

    @Autowired
    private WithdrawOrderService withdrawOrderService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PaymentRpc paymentRpc;


    public TransactionResult execute(Long uid, Long withdrawOrderId,Long examinerId, ExamineFlag flag,String remark) {
        TransactionResult transactionResult = new TransactionResult();
        WithdrawOrder withdrawOrder = withdrawOrderService.getById(withdrawOrderId);

        WithdrawExamine withdrawExamine = new WithdrawExamine();
        withdrawExamine.setWithdrawOrderId(withdrawOrderId);
        withdrawExamine.setExaminerId(examinerId);
        withdrawExamine.setRemark(remark);
        withdrawExamineService.save(withdrawExamine);

        if (ExamineFlag.APPROVED == flag) {
            paymentRpc.withdraw(withdrawOrder);
        } else {
            authService.unfree(uid, withdrawOrder.getFreeOrderId(), withdrawOrder.getWithdrawAmount());
        }

        return transactionResult;
    }
}
