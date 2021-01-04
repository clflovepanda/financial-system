package com.pro.financial.user.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.entity.DataSourceEntity;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.utils.Converter;

public class DataSourceEntity2Dto implements Converter<DataSourceEntity, DataSourceDto> {

    public static final DataSourceEntity2Dto instance = new DataSourceEntity2Dto();

    @Override
    public DataSourceDto convert(DataSourceEntity dataSourceEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(dataSourceEntity), DataSourceDto.class);
    }
}
