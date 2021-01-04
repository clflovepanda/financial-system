package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dto.PermissionDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    /**
     *
     * @param roleId 角色id
     * @return
     */
    @Select("SELECT * FROM permission " +
            "LEFT JOIN role_permission_relation USING (permission_id) " +
            "WHERE role_id = #{roleId} " +
            "AND state = 1")
    List<PermissionEntity> getPermissionByRoleId(@Param("roleId") int roleId);

    @Select({"<script>" +
            "SELECT * FROM permission " +
            "LEFT JOIN role_permission_relation USING (permission_id) " +
            "WHERE role_id in " +
            "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>"})
    List<PermissionEntity> getPermissionByRoleIds(@Param("list") List<Integer> roleIds);

    @Select({"<script>" +
            "SELECT uri FROM permission " +
            "LEFT JOIN role_permission_relation USING (permission_id) " +
            "WHERE role_id in " +
            "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>"})
    List<String> getPermissionUriByRoleIds(@Param("list") List<Integer> roleIds);

    @Select("SELECT * FROM permission where name like '%${keyWords}%' or uri like '%${keyWords}%' ")
    List<PermissionEntity> selectPermission(@Param("keyWords") String keyWords);

    @Select("SELECT * FROM permission where parent_id = 0 and state = 1")
    List<PermissionEntity> selectFristPermission();

    @Select("SELECT * FROM permission where parent_id <> 0 and state = 1")
    List<PermissionEntity> selectOtherPermission();

    @Select("SELECT * FROM permission WHERE permission_id IN (SELECT " +
            "permission_id  " +
            "FROM " +
            "role_permission_relation  " +
            "WHERE " +
            "role_id IN ( " +
            "SELECT " +
            "role_id  " +
            "FROM " +
            "user_role_relation  " +
            "WHERE " +
            "user_id = #{userId}))")
    List<PermissionDto> getPermissionByUserId(@Param("userId") Integer userId);
}
