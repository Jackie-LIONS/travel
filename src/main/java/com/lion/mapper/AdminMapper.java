package com.lion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.domain.Admin;
import com.lion.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    Admin findDesc(Integer aid);
    // 删除用户的所有角色
    void deleteAdminAllRoles(Integer aid);


    // 给用户添加角色
    void addAdminRole(@Param("aid") Integer aid, @Param("rid") Integer rid);
    // 根据管理员名查询权限
    List<Permission> findAllPermission(String username);


}
