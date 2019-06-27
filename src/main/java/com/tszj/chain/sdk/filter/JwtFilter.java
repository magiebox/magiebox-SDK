package com.tszj.chain.sdk.filter;

import com.alibaba.fastjson.JSON;
import com.tszj.chain.sdk.security.JwtToken;
import com.tszj.chain.sdk.security.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {


    private static final String AUTHZ_HEADER = "token";
    private static final String CHARSET = "UTF-8";

    /**
     * 处理未经验证的请求
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        boolean isLogin = false;
        if (this.isLoginAttempt(request, response)) {
            isLogin = this.executeLogin(request, response);
        }

        if (!isLogin) {
            this.sendChallenge(request, response);
        }

        return isLogin;
    }

    /**
     * 请求是否已经登录（携带token）
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = WebUtils.toHttp(request).getHeader(AUTHZ_HEADER);
        return authzHeader != null;
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response)
            throws Exception {
        String token = WebUtils.toHttp(request).getHeader(AUTHZ_HEADER);
        if (null == token) {
            String msg = "execute login method token must not be null";
            throw new IllegalStateException(msg);
        }
        JwtToken jwtToken = new JwtToken(token);
        try {
            this.getSubject(request, response).login(jwtToken);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }



    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String contentType = "application/json;charset=" + CHARSET;
        httpResponse.setStatus(403);
        httpResponse.setContentType(contentType);
        try {
            String msg = "没有访问权限";
            RestResponse result = RestResponse.newBuilder()
                    .setCode(403)
                    .setMsg(msg).build();
            PrintWriter printWriter = httpResponse.getWriter();
            printWriter.append(JSON.toJSONString(result));
        } catch (IOException e) {
            log.error("sendChallenge error,can not resolve httpServletResponse");
        }

        return false;
    }

    /**
     * 请求前处理,处理跨域
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse
                .setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时,option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
