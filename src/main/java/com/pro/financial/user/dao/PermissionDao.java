package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.PermissionEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    @Select("SELECT * FROM permission\n" +
            "\tLEFT JOIN role_permission_relation USING (permission_id)\n" +
            "\tWHERE role_id = #{roleId}")
    List<PermissionEntity> getPermissionByRoleId(@Param("roleId") int roleId);
}
