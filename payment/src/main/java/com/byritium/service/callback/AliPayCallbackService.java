package com.byritium.service.callback;

import com.alipay.api.internal.util.AlipaySignature;
import com.byritium.constance.alipay.AliPayStatus;
import com.byritium.dto.alipay.AliPayConfig;
import com.byritium.exception.AliPayCallbackException;
import com.byritium.rpc.SecretFeign;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class AliPayCallbackService {
    @Autowired
    private SecretFeign secretFeign;

    public void aliPayCallBack(Map<String, String[]> requestParams) {
        Gson gson = new Gson();

        log.info("支付宝回调参数：{}", gson.toJson(requestParams));

        AliPayConfig aliPayConfig = secretFeign.getAliPayConfig();

        Map<String, String> params = new HashMap<>();

        try {
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            log.info("params:{}", gson.toJson(params));

            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCertCheckV1(params, aliPayConfig.getAlipayCertPath(), "UTF-8", "RSA2");
            if (!flag) {
                throw new AliPayCallbackException("验签失败");
            }
            String paymentOrderId = params.get("out_trade_no");
            String msg = params.get("msg");
            String payStatus = params.get("trade_status");

            String successStatus = AliPayStatus.TRADE_SUCCESS.getStatus();

            if (payStatus.equals(successStatus)) {

            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AliPayCallbackException();
        }
    }
}
