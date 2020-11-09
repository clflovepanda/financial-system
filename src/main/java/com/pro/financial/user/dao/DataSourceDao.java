package com.pro.financial.user.dao;

import com.pro.financial.user.dto.DataSourceDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSourceDao {

    @Select("select * from data_source where parent_id = 0")
    List<DataSourceDto> getParentDataSource();
}
