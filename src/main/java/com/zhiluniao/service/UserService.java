package com.zhiluniao.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiluniao.model.po.User;
import com.zhiluniao.model.po.dao.UserMapper;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月20日  下午4:44:33
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    public User getUserById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }
    
    public User getUserByMobile(String mobile){
        return userMapper.selectByMobile(mobile);
    }

    public Set<String> findRoles(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public Set<String> findPermissions(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public User getUserByEmail(String username) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
