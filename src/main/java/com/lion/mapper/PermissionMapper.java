package com.lion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lion.domain.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    // 查询角色拥有的所有权限的id
    List<Integer> findPermissionIdByRole(Integer rid);
}
