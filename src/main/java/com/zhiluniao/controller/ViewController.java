package com.zhiluniao.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 前端页面（view）路由
 *
 * @author Administrator<br>
 *         2018年1月28日  下午5:55:18
 */
@Api(value = "shiro in action", tags = "views")
@Controller
@RequestMapping("/view")
public class ViewController {
    private Logger log = LoggerFactory.getLogger(ViewController.class);

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    @ApiOperation(value = "个人信息页面", notes = "个人信息页面")
    public String profile(Model model) {
        log.info("跳转到个人信息页面");
        
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        log.info("username[{}] profile",username);
        boolean hasSuperAdminRole = subject.hasRole("SUPER_ADMIN");
        model.addAttribute("certificated", true);
        model.addAttribute("hasSuperAdminRole", hasSuperAdminRole);
        return "user/profile";
    }
}
