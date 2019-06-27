package com.tszj.chain.sdk.service;

import java.math.BigDecimal;

public interface AccountService {
    String forward(String from, String to, BigDecimal amount, String transactionId);

    String withdraw(String account, BigDecimal amount, String transactionId);

}
