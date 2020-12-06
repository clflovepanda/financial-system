package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.AccountingLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingLogDao {

    @Insert("insert into accounting_log (`id`, `receivement_id`, `voucher_no`, `state`, `remark`, `create_user`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.receivementId}, #{entity.voucherNo}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime})")
    int insert(@Param("entity")AccountingLogEntity entity);
}