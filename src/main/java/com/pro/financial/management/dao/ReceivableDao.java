package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ReceivableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 应收单表 Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-11-21
 */
@Repository
public interface ReceivableDao extends BaseMapper<ReceivableEntity> {

    List<ReceivableEntity> getReceivable(@Param("projectId") String projectId, @Param("org") String org, @Param("receivableNo") String receivableNo, @Param("taxableServiceName") String taxableServiceName, @Param("userName") String userName, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("SELECT receivable_no FROM receivable ORDER BY receivable_id DESC LIMIT 0,1")
    String selectLastNo();

    @Select("<script>" +
            "select * from receivable " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<ReceivableEntity> getListByProjectIds(@Param("projectIds") List<Integer> projectIds);
}
