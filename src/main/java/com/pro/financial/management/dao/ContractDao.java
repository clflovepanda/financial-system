package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ContractEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractDao {
    

    List<ContractEntity> getContarct(@Param("projectId") String projectId, @Param("contractName") String contractName, @Param("contractNo") String contractNo, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Insert("insert into contract (`project_id`, `contract_no`, `contract_name`, `customer_name`, `resource_name`, `resource_url`) VALUES " +
            "(#{contract.projectId}, #{contract.contractNo}, #{contract.contractName}, #{contract.customerName}, #{contract.resourceName}, #{contract.resourceUrl})")
    int addContract(@Param("contract") ContractEntity contractEntity);

    @Select("select * from contract where contract_id = #{contractId}")
    ContractEntity selectById(@Param("contractId") int contractId);


    int updateContractById(@Param("contract") ContractEntity contractEntity);

    @Delete("delete from contract  where contract_id = #{contractId}")
    int deleteContract(@Param("contractId") int contractId);

    @Select("SELECT contract_no FROM contract ORDER BY contract_id DESC LIMIT 0,1")
    String selectLastNo();

    @Select("<script>" +
            "select * from contract " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<ContractEntity> getListByProjectIds(@Param("projectIds") List<Integer> projectIds);
}
