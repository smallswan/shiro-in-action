package com.zhiluniao.model.po;

import java.util.List;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月21日  下午11:35:56
 */
public class UserDetail {
    private String username;
    
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
    
}
