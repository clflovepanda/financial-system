package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.InvoiceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 发票应收表 Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-12-27
 */
@Repository
public interface InvoiceDao extends BaseMapper<InvoiceEntity> {

    List<InvoiceEntity> getList(@Param("projectId") Integer projectId, @Param("keyWord") String keyWord,@Param("username")  String username,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate,
                                @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("SELECT invoice_no FROM invoice ORDER BY invoice_id DESC LIMIT 0,1")
    String selectLastNo();

    int getCount(String keyWord, String username, Date startDate, Date endDate);

    @Select("SELECT sum(cny_money) FROM invoice WHERE project_id = #{projectId}")
    BigDecimal getreByProjectId(@Param("projectId") Integer projectId);
}
