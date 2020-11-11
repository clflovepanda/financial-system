package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.user.converter.CompanyEntity2Dto;
import com.pro.financial.user.converter.UserEntity2Dto;
import com.pro.financial.user.dao.CompanyDao;
import com.pro.financial.user.dao.RoleDao;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.user.dao.entity.CompanyEntity;
import com.pro.financial.user.dao.entity.UserEntity;
import com.pro.financial.user.dto.CompanyDto;
import com.pro.financial.user.dto.RoleDto;
import com.pro.financial.user.dto.UserDto;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserBiz {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CompanyDao companyDao;

    @Transactional(rollbackFor = Exception.class)
    public int addUser(UserDto user, List<Integer> roleIds) {
        UserEntity userEntity = JSONObject.parseObject(JSONObject.toJSONString(user), UserEntity.class);
        userEntity.setCreateDatetime(new Date());
        userEntity.setRegisterTime(new Date());
        int result = userDao.add(userEntity);
        //添加角色
        if (userEntity.getUserId() == null) {
            throw new RuntimeException();
        }
        int addroleCount = roleDao.addUserRoleRelation(userEntity.getUserId(), roleIds);

        return result;
    }

    public boolean isRegist(String mobile) {
        int amount = userDao.isRegist(mobile);
        if (amount > 0) {
            return true;
        }
        return false;
    }

    public List<UserDto> userList(String username, String mobile, String role, String state, String depId, String startDt, String endDt, Integer limit, Integer offset) {
        List<UserDto> userDtos = new ArrayList<>();
        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(startDt) && StringUtils.isNotEmpty(endDt)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                startDate = simpleDateFormat.parse(startDt);
                endDate = simpleDateFormat.parse(endDt);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        List<UserEntity> userList = userDao.userList(username, mobile, role, state, depId, startDate, endDate, limit, offset);
        return ConvertUtil.convert(UserEntity2Dto.instance, userList);
    }

    public int update(UserDto user) {
        UserEntity userEntity = JSONObject.parseObject(JSONObject.toJSONString(user), UserEntity.class);
        userEntity.setRegisterTime(new Date());
        int count = userDao.update(userEntity);
        userDao.deleteRole(userEntity.getUserId());
        roleDao.addUserRoleRelation(userEntity.getUserId(), user.getRoleId());
        return count;
    }

    public UserDto getUserById(int userId) {
        UserEntity userEntity = userDao.getUserById(userId);

        return UserEntity2Dto.instance.convert(userEntity);
    }

    public boolean changeUserState(int userId) {
        boolean result = true;
        String state = CommonConst.common_invalid;
        try {
            UserDto user = this.getUserById(userId);
            if (StringUtils.equals(CommonConst.common_invalid, user.getState())) {
                userDao.changeUserState(userId, CommonConst.common_valid);
            }
            userDao.changeUserState(userId, CommonConst.common_invalid);
        } catch (Exception e) {
            e.printStackTrace();
            return !result;
        }
        return result;
    }

    public List<CompanyDto> getCompany() {
        List<CompanyEntity> companyEntities = companyDao.getCompany();
        return ConvertUtil.convert(CompanyEntity2Dto.instance, companyEntities);
    }
}
