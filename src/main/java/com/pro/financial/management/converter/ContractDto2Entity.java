package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ContractEntity;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.utils.Converter;

public class ContractDto2Entity implements Converter<ContractDto, ContractEntity> {

    public static final ContractDto2Entity instance = new ContractDto2Entity();
    @Override
    public ContractEntity convert(ContractDto ContractDto) {
        ContractEntity contractEntity = new ContractEntity();
        contractEntity = JSONObject.parseObject(JSONObject.toJSONString(ContractDto), ContractEntity.class);
        return contractEntity;
    }
}
