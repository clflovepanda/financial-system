package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.SettlementEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SettlementDao {
    

    List<SettlementEntity> getSettlement(@Param("projectId") String projectId, @Param("settlementName") String settlementName, @Param("settlementNo") String settlementNo, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Insert("insert into settlement (`project_id`, `settlement_no`, `settlement_name`, `resource_name`, `resource_url`, `settlement_income`, `is_last_settlement`) VALUES " +
            "(#{settlement.projectId}, #{settlement.settlementNo}, #{settlement.settlementName}, #{settlement.resourceName}, #{settlement.resourceUrl}, #{settlement.settlementIncome}, #{settlement.isLastSettlement})")
    int addSettlement(@Param("settlement") SettlementEntity settlementEntity);

    @Select("select * from settlement where settlement_id = #{settlementId}")
    SettlementEntity selectById(@Param("settlementId") int settlementId);


    int updateSettlementById(@Param("settlement") SettlementEntity settlementEntity);

    @Delete("delete from settlement  where settlement_id = #{settlementId}")
    int deleteSettlement(@Param("settlementId") int settlementId);

    @Select("SELECT settlement_no FROM settlement ORDER BY settlement_id DESC LIMIT 0,1")
    String selectLastNo();

    @Select("<script>" +
            "select * from settlement " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<SettlementEntity> getListByProjectIds(@Param("projectIds") List<Integer> projectIds);

    @Select("SELECT sum(settlement_income) FROM settlement WHERE project_id = #{projectId} AND state = 1")
    BigDecimal getreByProjectId(@Param("projectId") Integer projectId);
}
