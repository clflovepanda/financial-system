package com.pro.financial.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.user.dao.entity.DataSourceEntity;
import com.pro.financial.user.dao.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDao extends BaseMapper<UserEntity> {

    @Insert("insert into user (username, dep_id, email, mobile, password, state, register_time, create_datetime) " +
            "values (#{username}, #{depId}, #{email}, #{mobile}, #{password}, #{state}, #{registerTime}, #{createDatetime}) ")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int add(UserEntity user);

    @Select("select count(1) from user where mobile = #{mobile}")
    int isRegist(String mobile);

    List<UserEntity> userList(@Param("username") String username, @Param("mobile") String mobile, @Param("role") String role,
                              @Param("state") String state, @Param("depId") String depId,
                              @Param("startDt") Date startDt, @Param("endDt") Date endDt,
                              @Param("limit") Integer limit, @Param("offset") Integer offset);


    int update(@Param("user") UserEntity userEntity);

    UserEntity getUserById(@Param("userId") int userId);

    List<UserEntity> userRealInfo(@Param("username") String userName);

    @Update("update `user` set state = #{state} where user_id = #{userId}")
    int changeUserState(@Param("userId") int userId, @Param("state") String state);

    /**
     *
     * @param userId
     * @return
     */
    @Delete("delete from user_role_relation where user_id = #{userId}")
    int deleteRole(@Param("userId") Integer userId);

    @Select("SELECT * FROM data_source WHERE data_source_id IN (SELECT data_source_id FROM role_datasource_relation WHERE role_id in (select role_id from user_role_relation WHERE user_id = #{userId})) AND parent_id != 0")
    List<DataSourceEntity> getDataSource(@Param("userId") Integer userId);
}
