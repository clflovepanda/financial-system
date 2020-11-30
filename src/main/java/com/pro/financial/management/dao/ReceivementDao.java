package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ReceivementEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivementDao {

    @Insert("insert into receivement (id, company_id, receivement_type_id, receivement_money, remitter_method_id, remitter, receive_date, state, remark, create_user, ctime, update_user, utime) VALUES " +
            "(#{entity.id}, #{entity.companyId}, #{entity.receivementTypeId}, #{entity.receivementMoney}, #{entity.remitterMethodId}, #{entity.remitter}, #{entity.receiveDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime})")
    int insert(@Param("entity") ReceivementEntity entity);

    @Select("<script> " +
            "select * from receivement where id " +
            "in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach> " +
            "</script>")
    List<ReceivementEntity> getListById(@Param("ids") List<Integer> ids);

    @Select("select * from receivement where id = #{id}")
    ReceivementEntity getById(@Param("id") Integer id);
}