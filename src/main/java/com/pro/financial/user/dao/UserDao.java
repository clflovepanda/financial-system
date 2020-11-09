package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDao {

    @Insert("insert into user (username, dep_id, email, mobile, password, state, register_time, create_datetime) " +
            "values (#{username}, #{depId}, #{email}, #{mobile}, #{password}, #{state}, #{registerTime}, #{createDatetime}) ")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int add(UserEntity user);

    @Select("select count(1) from user where mobile = #{mobile}")
    int isRegist(String mobile);

    List<UserEntity> userList(@Param("username") String username, @Param("mobile") String mobile, @Param("role") String role,
                              @Param("state") String state, @Param("depId") String depId,
                              @Param("startDt") Date startDt, @Param("endDt") Date endDt);


    int update(@Param("user") UserEntity userEntity);

    UserEntity getUserById(@Param("userId") int userId);

    List<UserEntity> getUserInfo(@Param("username") String userName);

}
