package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ContractEntity;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.utils.Converter;

public class ContractEntity2Dto implements Converter<ContractEntity, ContractDto> {

    public static final ContractEntity2Dto instance = new ContractEntity2Dto();
    @Override
    public ContractDto convert(ContractEntity ContractEntity) {
        ContractDto ContractDto = new ContractDto();
        ContractDto = JSONObject.parseObject(JSONObject.toJSONString(ContractEntity), ContractDto.class);
        return ContractDto;
    }
}
