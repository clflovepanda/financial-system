package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureDao {

    @Insert("insert into expenditure (id, numbering, expenditure_type, company_id, project_id, expenditure_method_id, expenditure_type_id, expenditure_purpose_id, expenditure_purpose_content, expenditure_money, state, remark, beneficiary_unit, beneficiary_number, province, city, beneficiary_bank, create_user, ctime, update_user, utime, is_effective) VALUES " +
            "(#{entity.id}, #{entity.numbering}, #{entity.expenditureType}, #{entity.companyId}, #{entity.projectId}, #{entity.expenditureMethodId}, #{entity.expenditureTypeId}, #{entity.expenditurePurposeId}, #{entity.expenditurePurposeContent}, #{entityExpenditureMoney}, #{entity.state}, #{entity.remark}, #{entity.beneficiaryUnit}, #{entity.beneficiaryNumber}, #{entityProvince}, #{entity.city}, #{entity.beneficiaryBank}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime}, #{entity.isEffective})")
    int insert(@Param("entity") ExpenditureEntity entity);
}