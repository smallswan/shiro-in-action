package com.zhiluniao.model.po.dao;

import com.zhiluniao.model.po.RolePermission;

public interface RolePermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_role_permission
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_role_permission
     *
     * @mbg.generated
     */
    int insert(RolePermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_role_permission
     *
     * @mbg.generated
     */
    int insertSelective(RolePermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_role_permission
     *
     * @mbg.generated
     */
    RolePermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_role_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RolePermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table zln_role_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RolePermission record);
}