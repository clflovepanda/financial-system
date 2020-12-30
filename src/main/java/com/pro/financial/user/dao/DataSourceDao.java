package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.DataSourceEntity;
import com.pro.financial.user.dto.DataSourceDto;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSourceDao {

    @Select("select * from data_source where parent_id = 0")
    List<DataSourceDto> getParentDataSource();

    @Select({"<script>" +
            "SELECT * FROM data_source " +
            "LEFT JOIN role_datasource_relation USING (data_source_id) " +
            "WHERE role_id in " +
            "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>"})
    List<DataSourceEntity> getDatasourceByRoleIds(@Param("list") List<Integer> roleIds);

    @Select("select * from data_source where parent_id = #{parentId}")
    List<DataSourceDto> getDataSourceByParentId(@Param("parentId") Integer parentId);

    @Select("select parent_id from data_source where data_source_id = #{dsId}")
    Integer getParentDSId(@Param("dsId") int dsId);
}
