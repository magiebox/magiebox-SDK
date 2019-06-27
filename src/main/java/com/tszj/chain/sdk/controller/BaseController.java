package com.tszj.chain.sdk.controller;

import com.tszj.chain.sdk.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

@Slf4j
public class BaseController {

    public String getOwner() {

        try {
            Object principal = SecurityUtils.getSubject().getPrincipal();
            return JwtUtils.getUserName(principal.toString());
        }catch (Exception e) {
            log.error("get owner error, error message:{}", e.getMessage());
            return null;
        }

    }
}
