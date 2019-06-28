package com.tszj.chain.sdk.service.impl;


import com.tszj.chain.sdk.api.ParamApi;
import com.tszj.chain.sdk.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${contract.eappcode}")
    String code;
    @Value("${gmc4j.url.nodeos}")
    String url;

    private String resultMessage = "信息校验失败,请检查填写信息";


    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String forward(String from, String to, BigDecimal amount,String transactionId) {

        if (StringUtils.isEmpty(transactionId)) {
            return resultMessage;
        }
        String message = ParamApi.check(transactionId, code, code, "notetaker", url, from);
        if (StringUtils.isEmpty(message))
            return resultMessage;

        String[] messageArr = message.trim().split(",");
        if (messageArr.length != 4)
            return resultMessage;
        if (!to.equals(messageArr[1]))
            return resultMessage;
        BigDecimal mesAmount = BigDecimal.valueOf(Double.parseDouble(messageArr[2].split(" ")[0]));
        if (amount.compareTo( mesAmount) != 0)
            return resultMessage;

        //TODO 数据层校验

        return "success";
    }

    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String withdraw(String accountName, BigDecimal amount, String transactionId) {

        if (StringUtils.isEmpty(transactionId))
            return resultMessage;
        String message = ParamApi.check(transactionId, code, code, "notetaker", url, accountName);
        if (StringUtils.isEmpty(message))
            return resultMessage;
        String[] messageArr = message.trim().split(",");
        if (messageArr.length != 3)
            return resultMessage;
        BigDecimal mesAmount = BigDecimal.valueOf(Double.parseDouble(messageArr[1].split(" ")[0]));
        if (amount.compareTo( mesAmount) != 0)
            return resultMessage;

        //TODO 数据层校验

      return "success";
    }

}
