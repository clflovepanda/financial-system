package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.user.dto.RoleDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {

    @Select("select * from role order by taxis")
    List<RoleEntity> getRole();

    @Insert({
            "<script> insert into user_role_relation (role_id, user_id)" +
                    "values " +
                    "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >" +
                    "(#{item}, #{userId})" +
                    "</foreach>" +
                    "</script>"
    })
    int addUserRoleRelation(@Param("userId") Integer userId, @Param("list") List<Integer> roleIds);

    @Insert("insert into role (role_name, is_sys_role, remark, create_datetime) values (#{roleName}, #{isSysRole}, #{remark}, #{createDatetime})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "role_id")
    int addRole(RoleDto roleDto);

    @Insert({
            "<script> insert into role_permission_relation (role_id, permission_id)" +
                    "values " +
                    "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >" +
                    "(#{item.permissionId}, #{roleId})" +
                    "</foreach>" +
                    "</script>"
    })
    int addRolePermission(@Param("roleId") int roleId, @Param("list") List<PermissionDto> permissionDtoList);

    @Results(id = "roleWithPermission", value = {
            @Result(property = "roleId", column ="role_id" , id = true),
            @Result(property = "roleName", column ="role_name" ),
            @Result(property = "permissionId", column ="permission_id" ),
            @Result(property = "uri", column ="uri" ),
            @Result(property = "permissions", column = "role_id", many = @Many(select = "com.pro.financial.user.dao.PermissionDao.getPermissionByRoleId"))
    })
    @Select("SELECT " +
            "r.role_id, r.role_name " +
            "FROM " +
            "role r " +
//            "LEFT JOIN role_permission_relation rp USING ( role_id ) " +
//            "LEFT JOIN permission p USING ( permission_id )  " +
            "WHERE " +
            "role_id = #{roleId}")
    RoleEntity getRoleByRoleId(@Param("roleId") Integer roleId);
}
