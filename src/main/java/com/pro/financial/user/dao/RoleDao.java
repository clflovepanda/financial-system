package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.user.dto.RoleDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public interface RoleDao {

    @Select("select * from role where state = '1' order by taxis")
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
                    "(#{roleId} ,#{item.permissionId})" +
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
            "WHERE " +
            "role_id = #{roleId}")
    RoleEntity getRoleByRoleId(@Param("roleId") Integer roleId);

    int update(@Param("role") RoleEntity role);

    /**
     * 删除现有权限
     * @param roleId
     * @return
     */
    @Delete("delete from role_permission_relation where role_id = #{roleId}")
    int deleRolePermission(@Param("roleId") Integer roleId);

    @Update("update `role` set state = #{state} where role_id = #{roleId}")
    int changeRoleState(@Param("roleId") Integer roleId, @Param("state") String state);

    @Insert({
            "<script> insert into role_datasource_relation (role_id, data_source_id)" +
                    "values " +
                    "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >" +
                    "(#{roleId}, #{item.dataSourceId},)" +
                    "</foreach>" +
                    "</script>"
    })
    int addRoleDataSource(@Param("roleId") Integer roleId, @Param("list") List<DataSourceDto> dataSourceDtos);

    @Delete("delete from role_datasource_relation where role_id = #{roleId}")
    int deleRoleDataSource(@Param("roleId") Integer roleId);
}
