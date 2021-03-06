package com.zhiluniao.model.po.dao;

import java.util.List;

import com.zhiluniao.model.po.UserDetail;
import com.zhiluniao.model.po.UserRole;

public interface UserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_user_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_user_role
     *
     * @mbg.generated
     */
    int insert(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_user_role
     *
     * @mbg.generated
     */
    int insertSelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_user_role
     *
     * @mbg.generated
     */
    UserRole selectByPrimaryKey(Long id);
    
    UserDetail selectByUsername(String username);
    
    List<UserRole> selectByUserId(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserRole record);
}