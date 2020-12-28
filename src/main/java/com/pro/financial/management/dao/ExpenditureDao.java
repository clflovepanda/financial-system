package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenditureDao {

    @Options(useGeneratedKeys = true, keyProperty = "expenditureId", keyColumn = "expenditure_id")
    @Insert("insert into expenditure ( numbering, expenditure_type, company_id, project_id, expenditure_method_id, expenditure_type_id, expenditure_purpose_id, expenditure_purpose_content, expenditure_money, state, remark, beneficiary_unit, beneficiary_number, province, city, beneficiary_bank, create_user, ctime, update_user, utime, is_effective) VALUES " +
            "( #{entity.numbering}, #{entity.expenditureType}, #{entity.companyId}, #{entity.projectId}, #{entity.expenditureMethodId}, #{entity.expenditureTypeId}, #{entity.expenditurePurposeId}, #{entity.expenditurePurposeContent}, #{entityExpenditureMoney}, #{entity.state}, #{entity.remark}, #{entity.beneficiaryUnit}, #{entity.beneficiaryNumber}, #{entityProvince}, #{entity.city}, #{entity.beneficiaryBank}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime}, #{entity.isEffective})")
    int insert(@Param("entity") ExpenditureEntity entity);

    @Select("<script>" +
            "select * from expenditure " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<ExpenditureEntity> getExpenditureList(@Param("projectIds") List<Integer> projectIds);

    List<ExpenditureEntity> statistics(@Param("attribute") String attribute, @Param("company") String company, @Param("projectNo") String projectNo,
                                       @Param("applyUser") String applyUser, @Param("purpose") String purpose, @Param("state") String state,
                                       @Param("beneficiaryUnit") String beneficiaryUnit, @Param("startDt") Date startDate, @Param("endDt") Date endDate);

    @Update("update set company_id = #{entity.companyId}, expenditure_type = #{expenditureType},")
    int update(@Param("entity") ExpenditureEntity entity);

    List<ExpenditureEntity> searchList(@Param("projectId") String projectId, @Param("companyId") String companyId,
                                       @Param("numbering") String numbering, @Param("expenditureMethodId") String expenditureMethodId,
                                    @Param("expenditureTypeId") String expenditureTypeId, @Param("beneficiaryUnit") String beneficiaryUnit, @Param("createUser") String createUser,
                                    @Param("state") String state, @Param("expenditureAuditLog") String expenditureAuditLog,
                                    @Param("expenditurePurposeId") String expenditurePurposeId, @Param("startDt") Date startDate, @Param("endDt") Date endDate,
                                       @Param("keyWord") String keyWord);
}