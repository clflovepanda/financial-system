package com.pro.financial.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共内容生成工具类
 */
public class CommonUtil {

    /**
     * 生成各类编号
     * 数字规则前，加上二级类目名称+模块的功能的首字母缩写+数字
     * @param type 模块功能首字母
     * @param dataSource 二级类目名称
     * @param no 编号顺序(数据库查出并返回) 需要拆解后三位
     * @return
     */
    public static String generatorNO(String type, String dataSource, String no) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(new Date());
        Integer realNo = 1;
        String date;
        //获取最后一个编号上的时间,如果与现在时间相同 就用后三位编号＋1 否则就是001
        try {
            date = no.substring(no.length() - 11, no.length() - 3);
        } catch (Exception e) {
            date = null;
            e.printStackTrace();
        }

        if (StringUtils.equals(dateStr, date)) {
            realNo = Integer.parseInt(no.substring(no.length() - 3)) + 1;
        }
        return StringUtils.isEmpty(dataSource) ? type + "-" + dateStr + int2String3(realNo) :  dataSource + "-" + type + "-" + dateStr + int2String3(realNo);
    }

    private static String int2String3(int no) {
        if (no > 999) {
            return "error";
        }
        if (no > 99) {
            return String.valueOf(no);
        }
        if (no > 9) {
            return "0" + no;
        }
        return "00" + no;
    }

    public static void main(String[] args) {
        String no = "IT-HT-20201120999";
        System.out.println(generatorNO("IT", "HT", no));
    }
}
