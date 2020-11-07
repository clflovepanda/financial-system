package com.pro.financial.user.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.entity.UserEntity;
import com.pro.financial.user.dto.UserDto;
import com.pro.financial.utils.Converter;

public class UserEntity2Dto implements Converter<UserEntity, UserDto> {

    public static final UserEntity2Dto instance = new UserEntity2Dto();
    @Override
    public UserDto convert(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto = JSONObject.parseObject(JSONObject.toJSONString(userEntity), UserDto.class);
        return userDto;
    }
}
