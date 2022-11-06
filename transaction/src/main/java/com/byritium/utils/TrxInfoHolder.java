package com.byritium.utils;

import com.byritium.dto.TransactionInfo;

public class TrxInfoHolder {
    private static final ThreadLocal<TransactionInfo> threadTrxInfo = new ThreadLocal<>();

    /**
     * 添加当前登录用户方法
     */
    public static void add(TransactionInfo transactionInfo){
        threadTrxInfo.set(transactionInfo);
    }

    public static TransactionInfo get(){
        return threadTrxInfo.get();
    }

    public static void remove(){
        threadTrxInfo.remove();
    }
}
