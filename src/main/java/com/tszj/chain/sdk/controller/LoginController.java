package com.tszj.chain.sdk.controller;


import com.tszj.chain.sdk.api.ParamApi;
import com.tszj.chain.sdk.security.RestResponse;
import com.tszj.chain.sdk.utils.ConvertUtil;
import com.tszj.chain.sdk.utils.JwtUtils;
import com.tszj.chain.gmc4j.ActionSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController extends BaseController {


    @Autowired
    ActionSender actionSender;

    @Value("${contract.eappcode}")
    String code;
    @Value("${gmc4j.url.nodeos}")
    String url;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse<String> login(String owner, String random, long time, String transactionId) {

        RestResponse.Builder builder = new RestResponse.Builder();
        if (StringUtils.isEmpty(owner)) {
            builder.setMsg("account is empty.");
            builder.setCode(403);
            return builder.build();
        }
        StringBuilder builder1 = new StringBuilder();
        String baseStr = builder1.append(owner + ",").append(random + ",").append(time).toString();
        String sha256 = ConvertUtil.getSHA256(baseStr);

        String message = ParamApi.check(transactionId, code, code, "notetaker", url, owner);
        if (StringUtils.isEmpty(message)) {
            builder.setMsg("message is empty.");
            builder.setCode(403);
            return builder.build();
        }
        String[] messageArr = message.trim().split(",");
        if (messageArr.length != 2) {
            builder.setMsg("message1 checked invalid.");
            builder.setCode(403);
            return builder.build();
        }
        if (!"login".equals(messageArr[0])) {
            builder.setMsg("message1 checked invalid.");
            builder.setCode(403);
            return builder.build();
        }
        if (!sha256.equals(messageArr[1])) {
            builder.setMsg("message2 checked invalid.");
            builder.setCode(403);
            return builder.build();
        }

        //todo 数据层校验

        String msg = "login success,token valid for 12 hours !";
        return RestResponse.OK(msg, JwtUtils.createToken(owner));
    }

}
