package com.zhiluniao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhiluniao.model.po.User;
import com.zhiluniao.model.vo.LoginReq;
import com.zhiluniao.model.vo.RspBody;
import com.zhiluniao.model.vo.SimpleFieldError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月20日 下午12:33:47
 */
@Api(value = "/user")
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody RspBody<String> login(HttpServletRequest request, @Validated @RequestBody LoginReq loginInfo,
            BindingResult bindingResult) {
        RspBody<String> rsp = new RspBody<String>();
        try {
            if (bindingResult.hasErrors()) {
                assemblyErrors(rsp,bindingResult);
                
                return rsp;
            }

            request.getSession().getId();
            User user = new User();
            user.setUsername(loginInfo.getUsername());
            user.setPassword(loginInfo.getPassword());

            Subject subject = SecurityUtils.getSubject();

            if (subject.isAuthenticated()) {
                rsp.setStatus("E0006");
                rsp.setBody(null);
                rsp.addError(new SimpleFieldError("username", "账号已经登录无需重复登录"));
                return rsp;
            }

            UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword());

            if (loginInfo.getRememberMe()) {
                token.setRememberMe(true);
            }
            // 4、登录，即身份验证
            subject.login(token);
            
            rsp.setStatus("0000");
            rsp.setStatusText("登录成功");
            
        } catch (LockedAccountException e) {
            log.error("账号被锁定", e);
            rsp.setStatus("E0002");
            rsp.addError(new SimpleFieldError("username", "账号被锁定"));
        } catch (ExcessiveAttemptsException e) {
            log.error("密码输入错误超过5次，账号被锁定", e);
            rsp.setStatus("E0003");
            rsp.addError(new SimpleFieldError("password", "密码输入错误超过5次，账号被锁定"));
        }catch(IncorrectCredentialsException e){
            log.error("用户名或密码为错误", e);
            rsp.setStatus("E00040");
            rsp.addError(new SimpleFieldError("username", e.getMessage()));
        }catch (AuthenticationException e) {
            log.error("用户名或密码为错误", e);
            rsp.setStatus("E0004");
            rsp.addError(new SimpleFieldError("username", "用户名或密码为错误"));
        } catch (Exception e) {
            log.error("系统错误", e);
            rsp.setStatus("E0000");
        }

        return rsp;

    }
    
    
    @ApiOperation(value = "用户注销", httpMethod = "POST", notes = "")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public @ResponseBody RspBody<String> logout() {
        RspBody<String> rsp = new RspBody<String>();
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            log.info("sessionId : {}", session.getId());
            log.info("host : {}", session.getHost());
            log.info("isAuthenticated:{}", subject.isAuthenticated());
            log.info("ticket : {}", session.getAttribute("ticket"));

            String username = null;
            if(subject.isAuthenticated()){
                username = (String)subject.getPrincipal();
                log.info("用户[username={}]注销登录",username);
                subject.logout();
            }else{
                log.error("用户尚未登录无需注销");
            }

            rsp.setStatus("0000");
            rsp.setStatusText("注销成功");
            rsp.setBody("OK");

        } catch (Exception e) {
            log.error("", e);
            rsp.setStatus("");
            rsp.setStatusText("");
        }
        return rsp;

    }
    
    //TODO 用户profile
    @RequiresRoles("SUPER_ADMIN")
    @ApiOperation(value = "用户资料", httpMethod = "POST", notes = "")
    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public @ResponseBody RspBody<String> profile() {
        RspBody<String> rsp = new RspBody<String>();
        try{
            rsp.setStatus("0000");
            rsp.setStatusText("成功");
            rsp.setBody("你拥有超级管理员(SUPER_ADMIN)角色");
            
        }catch(Exception e){
            log.error("",e);
            rsp.setStatus("E0000");
            rsp.setStatusText("系统错误");
        }
        

        return rsp;
    }
    
    @RequiresPermissions({"/view/permissions"})
    @ApiOperation(value = "用户注销", httpMethod = "POST", notes = "")
    @RequestMapping(value = "permissions", method = RequestMethod.POST)
    public @ResponseBody RspBody<String> permissions() {
        RspBody<String> rsp = new RspBody<String>();
        rsp.setStatus("0000");
        rsp.setStatusText("成功");
        
        
        rsp.setBody("你拥有查看权限(/view/permissions)的权限");
        
        return rsp;
    }
    
    /**
     * 装配错误信息
     * 
     * @param rsp
     * @param bindingResult
     */
    private void assemblyErrors(RspBody rsp,BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for(FieldError error : fieldErrors){
            SimpleFieldError err = new SimpleFieldError();
            err.setField(error.getField());
            err.setMessage(error.getDefaultMessage());
            rsp.addError(err);
        }
        
        rsp.setStatus("0001");
        rsp.setStatusText("参数校验失败，请检查提交的参数");
    }
}
