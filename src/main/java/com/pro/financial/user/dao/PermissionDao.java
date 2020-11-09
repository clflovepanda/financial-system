package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.PermissionEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    @Select("SELECT * FROM permission\n" +
            "LEFT JOIN role_permission_relation USING (permission_id) " +
            "WHERE role_id = #{roleId}")
    List<PermissionEntity> getPermissionByRoleId(@Param("roleId") int roleId);

    @Select({"<script>" +
            "SELECT * FROM permission\n" +
            "LEFT JOIN role_permission_relation USING (permission_id) " +
            "WHERE role_id in " +
            "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>"})
    List<PermissionEntity> getPermissionByRoleIds(@Param("list") List<Integer> roleIds);
}
