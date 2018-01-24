package com.zhiluniao.shiro.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zhiluniao.model.constants.Constants;
import com.zhiluniao.model.constants.UserStatus;
import com.zhiluniao.model.po.User;
import com.zhiluniao.service.UserService;


/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-1-27
 * <p>
 * Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {
    private Logger log = LoggerFactory.getLogger(UserRealm.class);
    
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //  返回用户角色、权限信息
        // 可以参照：/shiro-example-chapter6/src/main/java/com/github/zhangkaitao/shiro/chapter6/realm/UserRealm.java
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal(); // 得到用户名

        // 判断用户名是手机号码还是电子邮箱
        User user = null;
        if (username.matches(Constants.MOBILE_PATTERN)) {
            user = userService.getUserByMobile(username);
        } else if (username.matches(Constants.EMAIL_PATTERN)) {
            user = userService.getUserByEmail(username);
        }else{
            user = userService.getUserByUsername(username);
        }

        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }
        // 判断用户状态是否为锁定 Boolean.TRUE.equals(user.getLocked()
        if (UserStatus.USER_LOCKED.equals(user.getUserStatus())) {
            throw new LockedAccountException("帐号被锁定");
        }
        // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        sai.setCredentialsSalt(new SimpleByteSource(user.getPwdSalt()));
        return sai;
    }
    
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        log.info("清除用户[username={}]的授权信息",principals.getPrimaryPrincipal());
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        log.info("清除用户[username={}]的认证信息",principals.getPrimaryPrincipal());
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        log.info("清除用户[username={}]的认证信息、授权信息",principals.getPrimaryPrincipal());
        super.clearCache(principals);
    }
}
