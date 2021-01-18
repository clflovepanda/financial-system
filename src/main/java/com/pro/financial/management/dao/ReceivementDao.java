package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ReceivementEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReceivementDao {

    @Insert("insert into receivement (id, company_id, receivement_type_id, receivement_money, remitter_method_id, remitter, receive_date, state, remark, create_user, ctime, update_user, utime) VALUES " +
            "(#{entity.id}, #{entity.companyId}, #{entity.receivementTypeId}, #{entity.receivementMoney}, #{entity.remitterMethodId}, #{entity.remitter}, #{entity.receiveDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime})")
    int insert(@Param("entity") ReceivementEntity entity);

//    @Update("")
    int update(@Param("entity") ReceivementEntity entity);

//    @Select("<script> " +
//            "select * from receivement " +
//            "left join company using(company_id) " +
//            "left join receivement_type using(receivement_type_id) " +
//            "left join remitter_method using(remitter_method_id) " +
//            "where id " +
//            "in <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach> " +
//            "order by id desc" +
//            "</script>")
//    List<ReceivementEntity> getList(@Param("ids") List<Integer> ids);

    @Select("select * from receivement where id = #{id}")
    ReceivementEntity getById(@Param("id") Integer id);

    @Update("update receivement set state = #{state} where id = #{id}")
    int updatestate(@Param("id") Integer id, @Param("state") Integer state);

    @Select("select * from receivement order by id asc")
    List<ReceivementEntity> getAllList();

    List<ReceivementEntity> getList(@Param("ids") List<Integer> ids, @Param("companyId") String companyId,
                                    @Param("receivementTypeId") String receivementTypeId, @Param("remitterMethodId") String remitterMethodId,
                                    @Param("remitter") String remitter, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                    @Param("limit") Integer limit, @Param("offset") Integer offset);

    List<ReceivementEntity> statistics(@Param("dataSourceId") String dataSourceId, @Param("revenueTypeId") String revenueTypeId,
                                       @Param("projectName") String projectName,  @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int getCount(@Param("companyId") String companyId,
                 @Param("receivementTypeId") String receivementTypeId, @Param("remitterMethodId") String remitterMethodId,
                 @Param("remitter") String remitter, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<ReceivementEntity> statisticsDetail(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}