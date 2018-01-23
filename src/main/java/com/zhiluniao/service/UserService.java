package com.zhiluniao.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiluniao.model.po.Permission;
import com.zhiluniao.model.po.Role;
import com.zhiluniao.model.po.User;
import com.zhiluniao.model.po.UserDetail;
import com.zhiluniao.model.po.dao.PermissionMapper;
import com.zhiluniao.model.po.dao.UserMapper;
import com.zhiluniao.model.po.dao.UserRoleMapper;

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

    public User getUserByEmail(String username) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
