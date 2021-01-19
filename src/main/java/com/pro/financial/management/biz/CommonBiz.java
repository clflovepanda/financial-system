package com.pro.financial.management.biz;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.google.zxing.WriterException;
import com.pro.financial.utils.QRCodeUtil;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CommonBiz {

    private final static String OSS_ACCESS_KEY_ID	= "PaAwKyzWb7iXA4pH";
    private final static String OSS_ACCESS_KEY_SECRET = "HqWFph7HKRQsbqLErke7JDL6k999PH";
    private final static String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private final static String OSS_BUKET = "yxms";
    private final static String OSS_URL = "http://yxms.oss-cn-beijing.aliyuncs.com/qrcode/";


    public String createQrcode(String code) {
        int width = 800;
        int height = 800;
        String format = "png";
        String filePath = System.currentTimeMillis() +".png";
        try {
            ByteArrayOutputStream os = QRCodeUtil.getQRCodeStream(width, height, format, code);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(os.size());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(5000);
            conf.setMaxErrorRetry(3);
            OSSClient client = new OSSClient(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET, conf);
            client.putObject(OSS_BUKET, "qrcode/" + filePath, new ByteArrayInputStream(os.toByteArray()), objectMetadata);

            return OSS_URL + filePath;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return "";
    }
}
