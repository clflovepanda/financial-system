package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.QuotationEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationDao {
    

    List<QuotationEntity> getQuotation(@Param("quotationName") String quotationName, @Param("quotationNo") String quotationNo, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Insert("insert into quotation (`project_id`, `quotation_no`, `quotation_name`, `resource_name`, `resource_url`) VALUES " +
            "(#{quotation.projectId}, #{quotation.quotationNo}, #{quotation.quotationName}, #{quotation.resourceName}, #{quotation.resourceUrl})")
    int addQuotation(@Param("quotation") QuotationEntity quotationEntity);

    @Select("select * from quotation where quotation_id = #{quotationId}")
    QuotationEntity selectById(@Param("quotationId") int quotationId);


    int updateQuotationById(@Param("quotation") QuotationEntity quotationEntity);

    @Delete("delete from quotation  where quotation_id = #{quotationId}")
    int deleteQuotation(@Param("quotationId") int quotationId);

    @Select("SELECT quotation_no FROM quotation ORDER BY quotation_id DESC LIMIT 0,1")
    String selectLastNo();
}
