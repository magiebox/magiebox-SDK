package com.tszj.chain.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    public static String ADMIN;

    @Value("${contract.gameowner}")
    private void setAdmin(String gameOwner) {
        ADMIN= gameOwner;
    }
}
