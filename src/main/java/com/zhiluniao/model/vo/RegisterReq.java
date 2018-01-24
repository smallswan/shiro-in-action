package com.zhiluniao.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 *
 * @author huangshunle<br>
 *         2017年5月27日 下午3:01:59
 */

public class RegisterReq implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -3438651792909843618L;

	@ApiModelProperty(required = true, example = "18077812345", value = "用户名（手机号码/邮箱）")
	@NotBlank(message = "用户名[username]不能为空")
	@Length(max = 30, message = "用户名[username]长度不能超过30位。")
	private String username;

	@ApiModelProperty(required = true, example = "abcd", value = "密码")
	@NotBlank(message = "密码[password]不能为空")
	@Length(max = 16, message = "密码[password]长度不能超过16位。")
	private String password;

	@ApiModelProperty(required = true, example = "0123", value = "短信验证码")
	@NotBlank(message = "短信验证码[smsCaptcha]不能为空")
	private String smsCaptcha;

	@ApiModelProperty(required = true, example = "abcd", value = "图形验证码")
	@NotBlank(message = "图形验证码[captcha]不能为空")
	private String captcha;

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

	public String getSmsCaptcha() {
		return smsCaptcha;
	}

	public void setSmsCaptcha(String smsCaptcha) {
		this.smsCaptcha = smsCaptcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Override
	public String toString() {
		return "RegisterReq [username=" + username + ", password=" + password
				+ ", smsCaptcha=" + smsCaptcha + ", captcha=" + captcha + "]";
	}

}
