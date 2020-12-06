package com.pro.financial.user.biz;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.pro.financial.user.dao.entity.ValidateEntity;
import com.pro.financial.user.dao.ValidateDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.user.dto.ValidateDto;
import com.pro.financial.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * <p>
 * 验证码表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-12-06
 */
@Service
public class ValidateBiz extends ServiceImpl<ValidateDao, ValidateEntity> {

    private final static String OSS_ACCESS_KEY_ID	= "PaAwKyzWb7iXA4pH";
    private final static String OSS_ACCESS_KEY_SECRET = "HqWFph7HKRQsbqLErke7JDL6k999PH";
    private final static String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private final static String OSS_BUKET = "yxms";
    private final static String OSS_URL = "http://yxms.oss-cn-beijing.aliyuncs.com/code/";


    @Autowired
    private ValidateDao validateDao;

    public ValidateDto addValidate(VerifyCodeUtils vCode) {
        ValidateDto validateDto = new ValidateDto();
        OSS client = new OSSClientBuilder().build(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String fileName = System.currentTimeMillis() + ".png";
        String code = vCode.getCode();
        BufferedImage codeImage = vCode.getBuffImg();
        InputStream inputStream = null;
        try {
            ImageIO.write(codeImage, "png", os);
            inputStream = new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.putObject(OSS_BUKET, "code/" + fileName, inputStream);
        client.shutdown();
        ValidateEntity validateEntity = new ValidateEntity();
        validateEntity.setValidateCode(code);
        validateEntity.setValidateUrl(OSS_URL + fileName);
        validateDao.insert(validateEntity);
        validateDto.setValidateUrl(OSS_URL + fileName);
        validateDto.setValidateId(validateEntity.getValidateId());
        return validateDto;
    }
}
