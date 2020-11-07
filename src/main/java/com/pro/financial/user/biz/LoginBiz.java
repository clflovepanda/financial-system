package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.user.dao.entity.UserEntity;
import com.pro.financial.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class LoginBiz {

    @Autowired
    private UserDao userDao;

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
                //登陆成功 需要干点啥呢
                result.put("code", 0);
                result.put("msg", "");
                result.put("data", userEntities.get(0));
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
                if (StringUtils.equals(md5Password, userEntities.get(0).getPassword())) {
                    //登陆成功 需要干点啥呢
                    result.put("code", 0);
                    result.put("msg", "");
                    result.put("data", userEntity);
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
