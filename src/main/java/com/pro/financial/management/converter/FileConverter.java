package com.pro.financial.management.converter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileConverter {

    public static String ConvertCSVToXLS(String file, String name) throws IOException {

        String filePath = "";

        if (file.indexOf(".csv") < 0) {
            return "Error converting file: .csv file not given.";
        }
//        String name = FileManager.getFileNameFromPath(file, false);
        ArrayList<ArrayList<String>> arList = new ArrayList<ArrayList<String>>();
        ArrayList<String> al = null;

        String thisLine;
        DataInputStream myInput = new DataInputStream(new FileInputStream(file));

        while ((thisLine = myInput.readLine()) != null) {
            al = new ArrayList<String>();
            String strar[] = thisLine.split(",");

            for (int j = 0; j < strar.length; j++) {
                // My Attempt (BELOW)
                String edit = strar[j].replace('\n', ' ');
                al.add(edit);
            }

            arList.add(al);
            System.out.println();
        }

        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");

            for (int k = 0; k < arList.size(); k++) {
                ArrayList<String> ardata = (ArrayList<String>) arList.get(k);
                HSSFRow row = sheet.createRow((short) 0 + k);

                for (int p = 0; p < ardata.size(); p++) {
                    System.out.print(ardata.get(p));
                    HSSFCell cell = row.createCell((short) p);
                    cell.setCellValue(ardata.get(p).toString());
                }
            }

            FileOutputStream fileOut = new FileOutputStream(filePath + name + ".xls");
            hwb.write(fileOut);
            fileOut.close();

            System.out.println(name + ".xls has been generated");
            return filePath + name + ".xls";
        } catch (Exception ex) {
        }
        return "";
    }
}
