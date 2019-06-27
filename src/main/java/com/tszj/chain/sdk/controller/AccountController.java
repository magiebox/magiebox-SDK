package com.tszj.chain.sdk.controller;

import com.tszj.chain.sdk.entities.vo.View;
import com.tszj.chain.sdk.service.AccountService;
import com.tszj.chain.sdk.service.LockCoinsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController extends BaseController {

    @Autowired
    AccountService accountService;
    @Autowired
    LockCoinsService lockCoinsService;

    @RequestMapping(value = "/forward", method = RequestMethod.POST)
    public View forward(String to, BigDecimal amount, String transactionId) {
        String owner = getOwner();
        View view = new View();
        try {
            String result = accountService.forward(owner, to, amount, transactionId);
            if (result.equals("success"))
                view.setCode(200);
            else
                view.setData(406);
            view.setStatus(result);
        } catch (Exception e) {
            view.setMessage(e.getMessage());
            view.setStatus("fail");
            view.setCode(500);
            e.printStackTrace();
            log.error("forward transaction error,error message:{}", e.getMessage());
        }
        return view;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public View withdraw(BigDecimal amount, String transactionId) {
        String owner = getOwner();
        View view = new View();
        try {
            String result = accountService.withdraw(owner, amount, transactionId);
            if (result.equals("success"))
                view.setCode(200);
            else
                view.setCode(406);
            view.setStatus(result);
        } catch (Exception e) {
            view.setMessage(e.getMessage());
            view.setStatus("fail");
            view.setCode(500);
            e.printStackTrace();
            log.error("withdraw transaction error,error message:{}", e.getMessage());
        }
        return view;
    }

}
