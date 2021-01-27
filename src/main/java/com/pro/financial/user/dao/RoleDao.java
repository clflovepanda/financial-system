package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.DataSourceEntity;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.user.dto.RoleDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleDao {

    @Select("select * from role order by create_datetime desc")
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
            @Result(property = "permissions", column = "role_id", many = @Many(select = "com.pro.financial.user.dao.PermissionDao.getPermissionByRoleId")),
            @Result(property = "dataSources", column = "role_id", many = @Many(select = "com.pro.financial.user.dao.RoleDao.getPermissionByRoleId"))
    })
    @Select("SELECT " +
            "r.role_id, r.role_name, r.state " +
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
                    "(#{roleId}, #{item.dataSourceId})" +
                    "</foreach>" +
                    "</script>"
    })
    int addRoleDataSource(@Param("roleId") Integer roleId, @Param("list") List<DataSourceDto> dataSourceDtos);

    @Delete("delete from role_datasource_relation where role_id = #{roleId}")
    int deleRoleDataSource(@Param("roleId") Integer roleId);

    @Select({"<script>" +
            "SELECT * FROM data_source " +
            "LEFT JOIN role_datasource_relation USING (data_source_id) " +
            "WHERE role_id in " +
            "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>"})
    List<DataSourceEntity> getDatasourceByRoleIds(@Param("list") List<Integer> roleIds);

    @Select("SELECT * FROM data_source " +
            "LEFT JOIN role_datasource_relation USING (data_source_id) " +
            "WHERE role_id = #{roleId} ")
    List<DataSourceEntity> getPermissionByRoleId(@Param("roleId") int roleId);

    @Select("SELECT " +
            " *  " +
            "FROM " +
            " data_source  " +
            "WHERE " +
            " data_source_id IN ( " +
            " SELECT " +
            "  data_source_id  " +
            " FROM " +
            "  role_datasource_relation  " +
            "WHERE " +
            " role_id IN ( SELECT role_id FROM user_role_relation WHERE user_id = #{userId} ))")
    List<DataSourceEntity> getDatasourceByUserIds(@Param("userId") Integer userId);

    @Select("SELECT distinct intro FROM permission WHERE permission_id IN (SELECT permission_id FROM role_permission_relation WHERE role_id IN (SELECT DISTINCT role_id FROM user_role_relation LEFT JOIN role USING(role_id) where user_id =#{userId} and role.state = 1) and state = 1)")
    Set<String> getMenu(@Param("userId") Integer userId);

    @Insert({
            "<script> insert into role_permission_relation (role_id, permission_id)" +
                    "values " +
                    "<foreach collection=\"collection\" item=\"item\" index=\"index\" separator=\",\" >" +
                    "(#{roleId}, #{item})" +
                    "</foreach>" +
                    "</script>"
    })
    int addRolePermissionNew(@Param("roleId") Integer roleId, @Param("collection") Set<Integer> allIds);

}
