package com.zhiluniao.config;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.zhiluniao.model.constants.Constants;
import com.zhiluniao.shiro.credential.RetryLimitHashedCredentialsMatcher;
import com.zhiluniao.shiro.realm.UserRealm;

//import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;


/**
 * Shiro配置
 * 参考：http://lib.csdn.net/article/java/43527
 * 
 * @author Administrator<br>
 *         2017年11月24日  下午4:31:16
 */
@Configuration
public class ShiroConfiguration {
	private Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);
	
	@Bean
    public org.apache.shiro.mgt.SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //TODO 自定义缓存实现 使用redis  <!-- 必须放在首位,否则出错 -->
        securityManager.setCacheManager(cacheManager());
        //设置realm.
        securityManager.setRealm(myRealm());
        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
        //
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
	
	@Bean
	public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
		RetryLimitHashedCredentialsMatcher matcher = null;
		try {
			matcher = new RetryLimitHashedCredentialsMatcher(cacheManager());
			matcher.setHashAlgorithmName(Constants.SHIRO_HASH_ALGORITHM_NAME);
			matcher.setHashIterations(Constants.SHIRO_HASH_ITERATIONS);
			matcher.setStoredCredentialsHexEncoded(true);
		} catch (FileNotFoundException e) {
			log.error("",e);
		}

		return matcher;
		
	}
	
	
	@Bean
	public UserRealm myRealm(){
	    UserRealm myRealm = new UserRealm();
		myRealm.setCachingEnabled(true);
		myRealm.setAuthenticationCachingEnabled(true);
		myRealm.setAuthenticationCacheName("authenticationCacheName");
		myRealm.setAuthorizationCachingEnabled(true);
		myRealm.setAuthorizationCacheName("authorizationCacheName");
		myRealm.setCredentialsMatcher(credentialsMatcher());
		myRealm.setCacheManager(cacheManager());
		
		return myRealm;
		
	}
	
	@Bean
	public CacheManager cacheManager(){
	    log.info("创建CacheManager...");
	    EhCacheManager ehCacheManager = new org.apache.shiro.cache.ehcache.EhCacheManager();
	    ehCacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache.xml");
		return ehCacheManager;
	}
	
	@Bean
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/view/signin");
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/usersPage");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/unauthorized");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //TODO DIY（最好改写为从文件中读取配置）
		//TODO API访问权限设置


        //TODO 页面访问权限设置

		
		//TODO 无需任何授权
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");
        
        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/user/register","anon");
        
        
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->

//        filterChainDefinitionMap.put("/**", "authc");
      
        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/view/profile", "user");
        filterChainDefinitionMap.put("/user/profile", "user");
//        filterChainDefinitionMap.put("/", "user");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
	
    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
	
	/**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    
/*    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }*/
    
    @Bean
    public SimpleCookie rememberMeCookie(){

           System.out.println("ShiroConfiguration.rememberMeCookie()");
           //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
           SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
           //<!-- 记住我cookie生效时间30天 ,单位秒;-->
//           simpleCookie.setMaxAge(259200);
           simpleCookie.setMaxAge(300);
           return simpleCookie;
    }
    /**
      * cookie管理对象;
      * @return
      */
    @Bean
    public CookieRememberMeManager rememberMeManager(){

           System.out.println("ShiroConfiguration.rememberMeManager()");
           CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
           cookieRememberMeManager.setCookie(rememberMeCookie());
           return cookieRememberMeManager;
    }
    
    @Bean
    public SimpleMappingExceptionResolver error(){
        SimpleMappingExceptionResolver  Resolver = new SimpleMappingExceptionResolver();
        
        Properties mappings = new Properties();
        mappings.put("org.apache.shiro.authz.UnauthenticatedException", "/error/403");
        mappings.put("java.lang.Throwable", "/error/500");
//        mappings.put("", "");
//        mappings.put("", "");
        Resolver.setExceptionMappings(mappings);
        return Resolver;
    }

}
