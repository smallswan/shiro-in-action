package com.zhiluniao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月28日  下午5:08:11
 */
@Api(value = "shiro in action", tags = "errors")
@Controller
@RequestMapping("/error")
public class ErrorController {
    private Logger log = LoggerFactory.getLogger(ErrorController.class);
    
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    @ApiOperation(value = "403错误页面", notes = "403错误页面")
    public String error403(Model model) {
        log.info("跳转到403错误页面页面");
        
        return "error/403";
    }
    
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    @ApiOperation(value = "错误页面", notes = "错误页面")
    public String error404(Model model) {
        log.info("跳转到错误页面页面");
        
        return "error/404";
    }
    
    @RequestMapping(value = "/500", method = RequestMethod.GET)
    @ApiOperation(value = "系统错误页面", notes = "系统错误页面")
    public String error500(Model model) {
        log.info("跳转到系统错误页面页面");
        
        return "error/500";
    }
}
