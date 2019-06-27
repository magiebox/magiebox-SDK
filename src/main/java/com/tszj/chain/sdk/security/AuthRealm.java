package com.tszj.chain.sdk.security;

import com.tszj.chain.sdk.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public class AuthRealm extends AuthorizingRealm {





    @Override
    public boolean supports(AuthenticationToken token) {
        return null != token && token instanceof JwtToken;
    }

    /**
     * 登陆认证
     *
     * @param authenticationToken jwtFilter传入的token
     * @return 登陆信息
     * @throws AuthenticationException 未登陆抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        String token = (String) authenticationToken.getCredentials();
        String username = JwtUtils.getUserName(token);
        if (username == null) {
            throw new AccountException("user not found.");
        }
        if (!JwtUtils.verify(username, token)) {
            throw new UnknownAccountException("token invalid.");
        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 授权认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String token = principalCollection.toString();
        String userName = JwtUtils.getUserName(token);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        /**
         * 查询并赋予用户角色和权限
         *
        UserInfo userInfo = userInfoService.findByUsername(userName);
        if (userInfo == null)
            return authorizationInfo;

        userInfo.getRoleList().forEach(role -> {
            authorizationInfo.addRole(role.getRole());
            role.getPermissions().forEach(permission -> authorizationInfo.addStringPermission(permission.getPermission()));
        });
         */
        return authorizationInfo;
    }
}
