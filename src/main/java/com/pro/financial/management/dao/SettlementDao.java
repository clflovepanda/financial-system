package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.SettlementEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementDao {
    

    List<SettlementEntity> getSettlement(@Param("settlementName") String settlementName, @Param("settlementNo") String settlementNo, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Insert("insert into settlement (`project_id`, `settlement_no`, `settlement_name`, `resource_name`, `resource_url`) VALUES " +
            "(#{settlement.projectId}, #{settlement.settlementNo}, #{settlement.settlementName}, #{settlement.resourceName}, #{settlement.resourceUrl})")
    int addSettlement(@Param("settlement") SettlementEntity settlementEntity);

    @Select("select * from settlement where settlement_id = #{settlementId}")
    SettlementEntity selectById(@Param("settlementId") int settlementId);


    int updateSettlementById(@Param("settlement") SettlementEntity settlementEntity);

    @Delete("delete from settlement  where settlement_id = #{settlementId}")
    int deleteSettlement(@Param("settlementId") int settlementId);
}
