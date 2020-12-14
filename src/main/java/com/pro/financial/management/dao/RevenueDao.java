package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.RevenueEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueDao {

    @Insert("insert into revenue (`id`, `receivement_id`, `revenue_type_id`, `data_source_id`, `project_id`, `cny_money`, `delete`, `remark`, `create_user`, `ctime`, `update_user`, `utime`) VALUES " +
            "(#{entity.id}, #{entity.receivementId}, #{entity.revenueTypeId}, #{entity.dataSourceId}, #{entity.projectId}, #{entity.cnyMoney}, #{entity.delete}, #{entity.remark}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime}, #{entity.}, #{entity.}, #{entity.}, #{entity.}, #{entity.}, )")
    int insert(@Param("entity") RevenueEntity entity);

    @Select("<script>" +
            "select * from revenue " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<RevenueEntity> getRevenueList(@Param("projectIds") List<Integer> projectIds);
}