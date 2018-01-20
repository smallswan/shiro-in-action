package com.zhiluniao.model.vo;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月20日  下午4:34:20
 */
public class SimpleFieldError {
    
    private String field;
    private String message;
    
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public SimpleFieldError(){
        
    }
    
    public SimpleFieldError(String field,String message){
        this.field = field;
        this.message = message;
    }
}
