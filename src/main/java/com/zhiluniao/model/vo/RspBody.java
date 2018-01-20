package com.zhiluniao.model.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 接口返回对象
 * 
 * @author huangshunle Date: 2017年4月22日 下午9:34:46 <br/>
 * @param <T>
 */
public class RspBody<T> {
    public final static String STATUS_SUCCESS = "0000";
    public final static String STATUS_SUCCESS_TEXT = "成功";
    public final static String STATUS_FAIL_TEXT = "失败";
	@ApiModelProperty(required = true, value = "状态码", example = "0000")
	private String status;

	@ApiModelProperty(required = true, value = "状态信息", example = "成功")
	private String statusText;

	@ApiModelProperty(value = "业务返回值")
	private T body;
	   
	@ApiModelProperty(value = "业务错误信息")
	private List<SimpleFieldError> errors;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

    public List<SimpleFieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<SimpleFieldError> errors) {
        this.errors = errors;
    }
    
    public void addError(SimpleFieldError error){
        if(this.errors == null){
            List<SimpleFieldError> errors = new ArrayList<>();
            this.errors = errors;
        }
        errors.add(error);
    }

}
