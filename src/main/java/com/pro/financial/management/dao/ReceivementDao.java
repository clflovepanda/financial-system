package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ReceivementEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivementDao {

    @Insert("insert into receivement (id, company_id, receivement_type_id, receivement_money, remitter_method_id, remitter, receive_date, state, remark, create_user, ctime, update_user, utime) VALUES " +
            "(#{entity.id}, #{entity.companyId}, #{entity.receivementTypeId}, #{entity.receivementMoney}, #{entity.remitterMethodId}, #{entity.remitter}, #{entity.receiveDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime})")
    int insert(@Param("entity") ReceivementEntity entity);

    @Update("update receivement set company_id = #{entity.companyId}, receivement_type_id = #{entity.receivementTypeId}, receivement_money = #{entity.receivementMoney}, " +
            "remitter_method_id = #{entity.remitterMethodId}, remitter = #{entity.remitter}, receive_date = #{entity.receiveDate}, state = #{entity.state}, " +
            "remark = #{entity.remark}, create_user = #{entity.createUser}, ctime = #{entity.ctime}, update_user = #{entity.updateUser}, utime = #{entity.utime} where id = #{entity.id}")
    int update(@Param("entity") ReceivementEntity entity);

    @Select("<script> " +
            "select * from receivement where id " +
            "in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach> " +
            "</script>")
    List<ReceivementEntity> getListById(@Param("ids") List<Integer> ids);

    @Select("select * from receivement where id = #{id}")
    ReceivementEntity getById(@Param("id") Integer id);

    @Update("update receivement set state = #{state} where id = #{state}")
    int update(@Param("id") Integer id, @Param("state") Integer state);

    @Select("select * from receivement order by id asc")
    List<ReceivementEntity> getAllList();
}