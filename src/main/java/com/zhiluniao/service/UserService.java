package com.zhiluniao.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiluniao.model.constants.Constants;
import com.zhiluniao.model.constants.UserStatus;
import com.zhiluniao.model.po.Permission;
import com.zhiluniao.model.po.Role;
import com.zhiluniao.model.po.User;
import com.zhiluniao.model.po.UserDetail;
import com.zhiluniao.model.po.dao.PermissionMapper;
import com.zhiluniao.model.po.dao.UserMapper;
import com.zhiluniao.model.po.dao.UserRoleMapper;
import com.zhiluniao.model.vo.RegisterReq;
import com.zhiluniao.utils.IdWorker;

/**
 * 
 *
 * @author Administrator<br>
 *         2018年1月20日  下午4:44:33
 */
@Service
public class UserService {
    
    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private PermissionMapper  permissionMapper;
    
    public User getUserById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }
    
    public User getUserByMobile(String mobile){
        return userMapper.selectByMobile(mobile);
    }
    
    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }
    
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public Set<String> findRoles(String username) {
        UserDetail detail = userRoleMapper.selectByUsername(username);
        if(detail != null){
            List<Role> roles = detail.getRoles();
           if(roles != null && !roles.isEmpty()){
               Set<String> roleSet = new HashSet<>();
               for(Role role : roles){
                   roleSet.add(role.getRole());
               }
               
               return roleSet;
           }
        }
        return null;
    }

    public Set<String> findPermissions(String username) {
        Set<String>  roleSet = findRoles(username);
        if(roleSet != null && !roleSet.isEmpty()){
            String[] roles = new String[]{};
            roles = roleSet.toArray(roles);
            
            List<Permission>  permissions = permissionMapper.selectByRoles(roles);
            if(permissions != null && !permissions.isEmpty()){
                Set<String>  permissionSet = new HashSet<>();
                for(Permission permission : permissions){
                    permissionSet.add(permission.getPermission());
                }
                
                return permissionSet;
            }
        }
        
        return null;
    }


    public User register(RegisterReq req) {
        User user = null;
        String username = req.getUsername();
        String mobile = null;
        String email = null;
        if(username.matches(Constants.MOBILE_PATTERN)){
            user = userMapper.selectByMobile(username);
            mobile = username;
        } 
        
        if(username.matches(Constants.EMAIL_PATTERN)){
            user = userMapper.selectByEmail(username);
            email = username;
        }else{
            user = userMapper.selectByUsername(username);
        }
        
        if (user != null) {
            log.error("帐号[{}]已经注册，请使用其它帐号进行注册", req.getUsername());
            return null;
        }

        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        //盐
        String salt = generator.nextBytes().toHex();
        String hex = new Sha512Hash(req.getPassword(),salt).toHex();

        User user2 = new User();
        // 系统自动生成username
        IdWorker worker = new IdWorker(2);
        Long id = worker.nextId();
        user2.setId(id);
        user2.setUsername(username);
        user2.setMobile(mobile);
        user2.setEmail(email);
        user2.setPassword(hex);
        user2.setRegisterTime(new Date());
        user2.setPwdSalt(salt);
        user2.setUserStatus(UserStatus.USER_UNLOCKED);

        int affected = userMapper.insertSelective(user2);
        
        log.info("affected : {}",affected);
        return userMapper.selectByUsername(user2.getUsername());
    }
    
    
}
