package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.CompanyEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDao {

    @Select("select * from company where state = '1'")
    List<CompanyEntity> getCompany();
}