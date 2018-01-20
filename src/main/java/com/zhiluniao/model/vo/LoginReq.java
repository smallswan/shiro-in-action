package com.zhiluniao.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 登录请求
 *
 * @author huangshunle<br>
 *         2017年5月24日 上午10:07:34
 */

public class LoginReq implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 422836160366228463L;

    // TODO 增加格式验证
	@ApiModelProperty(required = true, example = "18077812345", value = "用户名（手机号码/邮箱）")
	@NotBlank(message = "用户名[username]不能为空")
	@Length(max = 30, message = "用户名[userName]长度不能超过30位。")
	private String username;

	@ApiModelProperty(required = true, example = "abcd", value = "密码")
	@NotBlank(message = "密码[password]不能为空")
	@Length(max = 16, message = "密码[password]长度不能超过16位。")
	private String password;
	
	@ApiModelProperty(required = false, example = "abcd", value = "图形验证码")
	private String captcha;
	
	@ApiModelProperty(required = false, example = "true", value = "记住我")
	@NotNull(message = "记住我[rememberMe]不能为空")
	private Boolean rememberMe;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
    public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

	@Override
	public String toString() {
		return "LoginReq [username=" + username + ", password=" + password + ", captcha=" + captcha + ", rememberMe=" + rememberMe + "]";
	}
}
