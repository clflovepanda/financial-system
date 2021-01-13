package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.ExportUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;

@Service
public class ExportBiz {

    private final static String OSS_ACCESS_KEY_ID	= "PaAwKyzWb7iXA4pH";
    private final static String OSS_ACCESS_KEY_SECRET = "HqWFph7HKRQsbqLErke7JDL6k999PH";
    private final static String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private final static String OSS_BUKET = "yxms";
    private final static String OSS_URL = "http://yxms.oss-cn-beijing.aliyuncs.com/export/";

    public JSONObject exportDepositCSV(List<RevenueDto> revenueDtos, String flag) {
        JSONObject result = new JSONObject();
        String fileName = "deposit_" + System.currentTimeMillis() + ".csv";

        try ( CSVPrinter printer = ExportUtil.getCsvPrinter(fileName, CommonConst.export_deposit)){
            int i = 1;
            for (RevenueDto revenueDto : revenueDtos) {
                String[] line = new String[]{i+"",revenueDto.getRevenueNo(),revenueDto.getCoName(),revenueDto.getReceivementTypeName()
                       ,revenueDto.getRemitter(),revenueDto.getRemitterMethodName()
                       ,revenueDto.getCnyMoney()+"",revenueDto.getUsername(),revenueDto.getCtime()+""
                       ,revenueDto.getToBeReturned()+"",revenueDto.getReturning()+"",revenueDto.getReturned()+""};
                printer.printRecord(line);
                i++ ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 8001);
            result.put("msg", 8001);
            return result;
        }
        String url = this.upload2OSS(fileName);
        result.put("code", 0);
        result.put("msg", "");
        result.put("url", url);
        return result;
    }

    private String upload2OSS(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            OSS client = new OSSClientBuilder().build(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
            client.putObject(OSS_BUKET, "export/" + fileName, inputStream);
            client.shutdown();
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return OSS_URL + fileName;
    }
}
