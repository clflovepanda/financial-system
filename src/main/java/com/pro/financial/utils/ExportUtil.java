package com.pro.financial.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ExportUtil {
    /**
     *
     * @param fileName 文件名字地址
     * @param header 表头
     * @return
     * @throws Exception
     */
    public static CSVPrinter getCsvPrinter(String fileName, String[] header) throws Exception {
        OutputStream out = new FileOutputStream(fileName);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(header);
        OutputStreamWriter osw = new OutputStreamWriter(out);
        // 防止Excel打开乱码
        byte[] bytes = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        osw.write(new String(bytes));
        return new CSVPrinter(osw, csvFormat);
    }
}
