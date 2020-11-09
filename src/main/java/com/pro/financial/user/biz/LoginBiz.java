package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.converter.PermissionEntity2Dto;
import com.pro.financial.user.converter.UserEntity2Dto;
import com.pro.financial.user.dao.PermissionDao;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dao.entity.UserEntity;
import com.pro.financial.user.dto.UserDto;
import com.pro.financial.utils.ConvertUtil;
import com.pro.financial.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginBiz {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;

    /**
     *
     * @param userName 可以是姓名,电话号,或者邮箱
     * @param password
     * @return
     */
    public JSONObject login(String userName, String password) {
        JSONObject result = new JSONObject();
        //获取用户信息
        List<UserEntity> userEntities = userDao.getUserInfo(userName);
        String md5Password = MD5Util.getMD5(password);
        if (CollectionUtils.isEmpty(userEntities)) {
            result.put("code", 1002);
            result.put("msg", "未查询到用户");
            return result;
        }
        //根据用户名查询,只有一个
        if (userEntities.size() == 1) {
            if (StringUtils.equals(md5Password, userEntities.get(0).getPassword())) {
                //登陆成功 需要干点啥呢  返回这个人的权限
                List<Integer> roleIds = new ArrayList<>();
                if (CollectionUtils.isEmpty(userEntities.get(0).getRoles())) {
                    result.put("code", 1003);
                    result.put("msg", "该用户没有配置角色");
                }
                for (RoleEntity roleEntity : userEntities.get(0).getRoles()) {
                    roleIds.add(roleEntity.getRoleId());
                }
                List<PermissionEntity> permissionEntities = permissionDao.getPermissionByRoleIds(roleIds);
                UserDto userDto = UserEntity2Dto.instance.convert(userEntities.get(0));
                userDto.setPermissions(ConvertUtil.convert(PermissionEntity2Dto.instance, permissionEntities));
                result.put("code", 0);
                result.put("msg", "");
                result.put("data", userDto);
                return result;
            } else {
                //密码错误
                result.put("code", 1002);
                result.put("msg", "密码错误");
                return result;
            }
        } else {
            //查询出多条(重名) 匹配密码选择
            for (UserEntity userEntity : userEntities) {
                if (StringUtils.equals(md5Password, userEntity.getPassword())) {
                    //登陆成功 需要干点啥呢
                    //TODO 密码重复了怎么办呢
                    List<Integer> roleIds = new ArrayList<>();
                    if (CollectionUtils.isEmpty(userEntity.getRoles())) {
                        result.put("code", 1003);
                        result.put("msg", "该用户没有配置角色");
                    }
                    for (RoleEntity roleEntity : userEntity.getRoles()) {
                        roleIds.add(roleEntity.getRoleId());
                    }
                    List<PermissionEntity> permissionEntities = permissionDao.getPermissionByRoleIds(roleIds);
                    UserDto userDto = UserEntity2Dto.instance.convert(userEntity);
                    userDto.setPermissions(ConvertUtil.convert(PermissionEntity2Dto.instance, permissionEntities));
                    result.put("code", 0);
                    result.put("msg", "");
                    result.put("data", userDto);
                    return result;
                } else {
                    //密码错误
                    result.put("code", 1002);
                    result.put("msg", "密码错误");
                    return result;
                }
            }
        }
        return result;
    }
}
