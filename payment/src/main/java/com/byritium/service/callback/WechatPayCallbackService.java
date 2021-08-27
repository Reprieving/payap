package com.byritium.service.callback;

import com.alipay.api.internal.util.AlipaySignature;
import com.byritium.constance.alipay.AliPayStatus;
import com.byritium.constance.wechatpay.WechatPayStatus;
import com.byritium.dto.AliPayConfig;
import com.byritium.dto.WechatPayConfig;
import com.byritium.dto.wechat.WechatPayCallBackNotifyParam;
import com.byritium.dto.wechat.WechatPayCallBackResource;
import com.byritium.dto.wechat.WechatPayCallBackResult;
import com.byritium.exception.AliPayCallbackException;
import com.byritium.exception.WechatPayCallbackException;
import com.byritium.rpc.SecretFeign;
import com.byritium.utils.WechatUtils;
import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class WechatPayCallbackService {

    @Autowired
    private SecretFeign secretFeign;

    public void wechatPayCallBack(WechatPayCallBackResult result) {
        try {
            log.info("微信支付回调参数：{}", new Gson().toJson(result));

            WechatPayConfig config = secretFeign.getWechatPayConfig();
            WechatPayCallBackResource resource = result.getResource();
            String wxApiV3key = config.getApiV3Key();
            String nonce = resource.getNonce();
            String associatedData = resource.getAssociated_data();
            String cipherText = resource.getCiphertext();

            String param = WechatUtils.aseGSMDecrypt(wxApiV3key, associatedData, nonce, cipherText);
            Gson gson = new Gson();
            WechatPayCallBackNotifyParam notifyParam = gson.fromJson(param, WechatPayCallBackNotifyParam.class);

            String paymentOrderId = notifyParam.getOut_trade_no();
            String msg = notifyParam.getTrade_state_desc();
            String payStatus = notifyParam.getTrade_state();

            if (payStatus.equals(WechatPayStatus.SUCCESS.getStatus())) {

            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new WechatPayCallbackException();
        }
    }



}
