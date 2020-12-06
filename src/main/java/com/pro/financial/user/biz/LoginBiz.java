package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.user.converter.UserEntity2Dto;
import com.pro.financial.user.dao.DataSourceDao;
import com.pro.financial.user.dao.PermissionDao;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.user.dao.ValidateDao;
import com.pro.financial.user.dao.entity.*;
import com.pro.financial.user.dto.UserDto;
import com.pro.financial.utils.CookieUtil;
import com.pro.financial.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginBiz {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private DataSourceDao dataSourceDao;
    @Autowired
    private ValidateDao validateDao;

    /**
     *
     * @param userName 可以是姓名,电话号,或者邮箱
     * @param password
     * @return
     */
    public JSONObject login(String userName, String password, Integer validateCode, String code, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        if (validateCode == null || validateCode < 1 || StringUtils.isEmpty(code)) {
            result.put("code", 1002);
            result.put("msg", "验证码错误1");
            return result;
        }
        ValidateEntity validateEntity = validateDao.selectById(validateCode);
        if (validateEntity == null || !StringUtils.equals(code, validateEntity.getValidateCode())) {
            result.put("code", 1002);
            result.put("msg", "验证码错误2");
            return result;
        }
        //获取用户信息
        List<UserEntity> userEntities = userDao.userRealInfo(userName);
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
                List<DataSourceEntity> dataSourceEntities = dataSourceDao.getDatasourceByRoleIds(roleIds);
                UserDto userDto = UserEntity2Dto.instance.convert(userEntities.get(0));
                String permissionJsonStr = JSONObject.toJSONString(permissionEntities);
                String permissionCookieName = CommonConst.cookie_permission_head + userDto.getUserId();
                String datasourceCookieName = CommonConst.cookie_datasource_head + userDto.getUserId();
                String datasourceJsonStr = JSONObject.toJSONString(dataSourceEntities);
                String userJsonStr = JSONObject.toJSONString(userDto);
                //菜单权限存入cookie
                CookieUtil.addCookie(response, permissionCookieName, permissionJsonStr, 3600);
                // 数据权限存入cookie
                CookieUtil.addCookie(response, datasourceCookieName, datasourceJsonStr, 3600);
                CookieUtil.addCookie(response, CommonConst.cookie_user_head, userJsonStr, 3600);
                request.getSession().setAttribute(permissionCookieName, permissionJsonStr);
                request.getSession().setAttribute(datasourceCookieName, datasourceJsonStr);
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
                    List<DataSourceEntity> dataSourceEntities = dataSourceDao.getDatasourceByRoleIds(roleIds);
                    UserDto userDto = UserEntity2Dto.instance.convert(userEntity);
                    String permissionJsonStr = JSONObject.toJSONString(permissionEntities);
                    String permissionCookieName = CommonConst.cookie_permission_head + userDto.getUserId();
                    String datasourceCookieName = CommonConst.cookie_datasource_head + userDto.getUserId();
                    String datasourceJsonStr = JSONObject.toJSONString(dataSourceEntities);
                    String userJsonStr = JSONObject.toJSONString(userDto);
                    String permissionCookieEncode = null;
                    String datasourceCookieEncode = null;
                    String userCookieEncode = null;
                    try {
                        permissionCookieEncode = URLEncoder.encode(permissionJsonStr, "utf-8");
                        datasourceCookieEncode = URLEncoder.encode(datasourceJsonStr, "utf-8");
                        userCookieEncode = URLEncoder.encode(userJsonStr, "utf-8");

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        result.put("code", 9000);
                        result.put("msg", "保存cookie失败");
                        return result;
                    }
                    if (StringUtils.isEmpty(permissionCookieEncode) || StringUtils.isEmpty(datasourceCookieEncode) || StringUtils.isEmpty(userCookieEncode)) {
                        result.put("code", 9000);
                        result.put("msg", "保存cookie失败");
                        return result;
                    }
                    //菜单权限存入cookie
                    CookieUtil.addCookie(response, permissionCookieName, permissionCookieEncode, 3600);
                    // 数据权限存入cookie
                    CookieUtil.addCookie(response, datasourceCookieName, datasourceCookieEncode, 3600);
                    CookieUtil.addCookie(response, CommonConst.cookie_user_head, userCookieEncode, 3600);
                    request.getSession().setAttribute(CommonConst.cookie_user_head, userJsonStr);
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
