package com.pro.financial.management.biz;

import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.ContractDto2Entity;
import com.pro.financial.management.converter.ContractEntity2Dto;
import com.pro.financial.management.dao.ContractDao;
import com.pro.financial.management.dao.entity.ContractEntity;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.utils.CommonUtil;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractBiz {

    @Autowired
    private ContractDao contractDao;

    public List<ContractDto> getContarct(String contractName, String contractNo, Integer limit, Integer offset) {
        List<ContractEntity> contractEntities = contractDao.getContarct(contractName, contractNo, limit, offset);
        return ConvertUtil.convert(ContractEntity2Dto.instance, contractEntities);
    }

    public int addContract(ContractDto contractDto) {
        ContractEntity contractEntity = ContractDto2Entity.instance.convert(contractDto);
        //生成编号
        String contractNo;
        //获取最后一条数据的编号
        String lastNo = contractDao.selectLastNo();
        if (StringUtils.isEmpty(lastNo)) {
            contractNo = "001";
        } else  {
            contractNo = CommonUtil.generatorNO(CommonConst.initials_contract, contractDto.getDataSource(), lastNo);
        }
        contractEntity.setContractNo(contractNo);
        return contractDao.addContract(contractEntity);
    }

    public ContractDto getByContractId(int contractId) {
        ContractEntity contractEntity = contractDao.selectById(contractId);
        return ContractEntity2Dto.instance.convert(contractEntity);
    }

    public int updateContract(ContractDto contractDto) {
        ContractEntity contractEntity = ContractDto2Entity.instance.convert(contractDto);
        return contractDao.updateContractById(contractEntity);
    }

    public int deleteContractTrue(int contractId) {
        return contractDao.deleteContract(contractId);
    }
    public int deleteContract(int contractId) {
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setContractId(contractId);
        contractEntity.setState("0");
        return contractDao.updateContractById(contractEntity);
    }
}
