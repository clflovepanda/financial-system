package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final static String OSS_ACCESS_KEY_ID	= "PaAwKyzWb7iXA4pH";
    private final static String OSS_ACCESS_KEY_SECRET = "HqWFph7HKRQsbqLErke7JDL6k999PH";
    private final static String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private final static String OSS_BUKET = "yxms";
    private final static String OSS_URL = "http://yxms.oss-cn-beijing.aliyuncs.com/excel/";

    @RequestMapping("/excel")
    public JSONObject uploadExcel(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        if (file.isEmpty()) {
            result.put("code", 3001);
            result.put("msg", "请选择文件");
            return result;
        }
        String fileType = request.getParameter("fileType");
        String mode = request.getParameter("mode");
        if (StringUtils.isEmpty(fileType) || StringUtils.isEmpty(mode)) {
            result.put("code", 3002);
            result.put("msg", "缺少关键参数");
            return result;
        }
        String fileName = System.currentTimeMillis() + "_" + mode + "." + fileType;
        OSS client = new OSSClientBuilder().build(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
        client.putObject(OSS_BUKET, "excel/" + fileName, file.getInputStream());
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", OSS_URL + fileName);
        return result;
    }
}
