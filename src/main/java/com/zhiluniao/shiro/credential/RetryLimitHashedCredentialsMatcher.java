package com.zhiluniao.shiro.credential;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 限制登录密码错误尝试次数Matcher
 *
 * @author huangshunle<br>
 *         2017年5月25日  下午3:25:33
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private  Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(){
        
    }
    
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) throws FileNotFoundException {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount  = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);  
            passwordRetryCache.put(username,retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException("密码输入错误超过5次，账号被锁定");
        }

        boolean isMatches = super.doCredentialsMatch(token, info);
        if (!isMatches) {
            throw new IncorrectCredentialsException("用户或密码错误，剩余重试次数:" + (5 - retryCount.get()));
        }else{
            //clear retry count
            passwordRetryCache.remove(username);
        }
        
        return isMatches;
    }
}
