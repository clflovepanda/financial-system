package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.FileConverter;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.ExportUtil;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class ExportBiz {

    private final static String OSS_ACCESS_KEY_ID	= "PaAwKyzWb7iXA4pH";
    private final static String OSS_ACCESS_KEY_SECRET = "HqWFph7HKRQsbqLErke7JDL6k999PH";
    private final static String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private final static String OSS_BUKET = "yxms";
    private final static String OSS_URL = "http://yxms.oss-cn-beijing.aliyuncs.com/export/";

    public JSONObject exportDepositCSV(List<RevenueDto> revenueDtos) {
        JSONObject result = new JSONObject();
        String fileName = "deposit_" + System.currentTimeMillis() + ".csv";
        String excelFileName = "deposit_" + System.currentTimeMillis() + ".xls";
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
        //        String url = this.upload2OSS(fileName);
        try {
            String url = this.upload2OSSXls(fileName, excelFileName);
            result.put("code", 0);
            result.put("msg", "");
            result.put("url", url);
        } catch (Exception e) {
            result.put("code", 1001);
            result.put("msg", "生成文件失败");
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    public JSONObject exportExpenditureCSV(List<ExpenditureDto> expenditureDtos) {
        JSONObject result = new JSONObject();
        String fileName = "expenditure_" + System.currentTimeMillis() + ".csv";
        String excelFileName = "expenditure_" + System.currentTimeMillis() + ".xls";

        try ( CSVPrinter printer = ExportUtil.getCsvPrinter(fileName, CommonConst.export_expenditure)){
            int i = 1;
            for (ExpenditureDto expenditureDto : expenditureDtos) {
                String[] line = new String[]{i+"", expenditureDto.getNumbering(), expenditureDto.getCoName(), expenditureDto.getExpenditureMethodName(),
                        expenditureDto.getExpenditureTypeName(), expenditureDto.getExpenditurePurposeName(), expenditureDto.getBeneficiaryUnit(), expenditureDto.getExpenditureMoney()+"",
                        expenditureDto.getUsername(), expenditureDto.getCtime()+"", expenditureDto.getState()+"", expenditureDto.getUtime()+"", expenditureDto.getAuditType()};
                printer.printRecord(line);
                i++ ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 8001);
            result.put("msg", 8001);
            return result;
        }
//        String url = this.upload2OSS(fileName);
        try {
            String url = this.upload2OSSXls(fileName, excelFileName);
            result.put("code", 0);
            result.put("msg", "");
            result.put("url", url);
        } catch (Exception e) {
            result.put("code", 1001);
            result.put("msg", "生成文件失败");
        }

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

    /**
     * csv转xls 并上传
     * @param fileName
     * @return
     */
    private String upload2OSSXls(String fileName, String excelFileName) throws Exception {
        File file = new File(fileName);
        FileConverter.ConvertCSVToXLS(fileName, excelFileName);
        if (!file.exists()) {
            return "";
        }
        try {
            InputStream inputStream = new FileInputStream(file);
            OSS client = new OSSClientBuilder().build(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
            client.putObject(OSS_BUKET, "export/" + excelFileName, inputStream);
            client.shutdown();
            file.delete();
            File file1 = new File(excelFileName);
            if (file1.exists()) {
                file1.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return OSS_URL + excelFileName;
    }

    public JSONObject exportStatisticsExpenditureCSV(List<ExpenditureDto> expenditureDtos) {
        JSONObject result = new JSONObject();
        String fileName = "statistics_expenditure_" + System.currentTimeMillis() + ".csv";
        String excelFileName = "statistics_expenditure_" + System.currentTimeMillis() + ".xls";
        try ( CSVPrinter printer = ExportUtil.getCsvPrinter(fileName, CommonConst.export_statistics_expenditure)){
            int i = 1;
            for (ExpenditureDto expenditureDto : expenditureDtos) {
                String[] line = new String[]{expenditureDto.getExpenditureId()+"", expenditureDto.getCoName(), expenditureDto.getNumbering(),
                        expenditureDto.getProject() != null ? expenditureDto.getProject().getName() : "",
                        expenditureDto.getBeneficiaryUnit(),
                        expenditureDto.getExpenditureTypeEntity() != null ? expenditureDto.getExpenditureTypeEntity().getExpenditureTypeName() : "",
                        expenditureDto.getExpenditurePurposeContent(), expenditureDto.getExpenditureMoney()+"", expenditureDto.getUsername(), expenditureDto.getState()+""};
                printer.printRecord(line);
                i++ ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 8001);
            result.put("msg", 8001);
            return result;
        }
        //        String url = this.upload2OSS(fileName);
        try {
            String url = this.upload2OSSXls(fileName, excelFileName);
            result.put("code", 0);
            result.put("msg", "");
            result.put("url", url);
        } catch (Exception e) {
            result.put("code", 1001);
            result.put("msg", "生成文件失败");
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    public JSONObject exportStatisticsProject(List<ProjectDto> projectDtos) {
        JSONObject result = new JSONObject();
        String fileName = "statistics_project_" + System.currentTimeMillis() + ".csv";
        String excelFileName = "statistics_project_" + System.currentTimeMillis() + ".xls";
        try ( CSVPrinter printer = ExportUtil.getCsvPrinter(fileName, CommonConst.export_statistics_project)){
            int i = 1;
            for (ProjectDto projectDto : projectDtos) {
                String[] line = new String[]{projectDto.getProjectId()+"", projectDto.getCode(), projectDto.getName(), projectDto.getManagerName(), "", projectDto.getSettlementIncome()+"", projectDto.getRelRevenue()+"", projectDto.getReceivable()+""};
                printer.printRecord(line);
                i++ ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 8001);
            result.put("msg", 8001);
            return result;
        }
        //        String url = this.upload2OSS(fileName);
        try {
            String url = this.upload2OSSXls(fileName, excelFileName);
            result.put("code", 0);
            result.put("msg", "");
            result.put("url", url);
        } catch (Exception e) {
            result.put("code", 1001);
            result.put("msg", "生成文件失败");
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    public static JSONObject exportStatisticsProject() {
        JSONObject result = new JSONObject();
        //设置到款统计的表头
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("id");
        row1.createCell(1).setCellValue("到款");
        row1.createCell(9).setCellValue("认款");
        HSSFRow row2 = sheet.createRow(1);
        row2.createCell(1).setCellValue("账户");
        row2.createCell(2).setCellValue("汇款方");
        row2.createCell(3).setCellValue("种类");
        row2.createCell(4).setCellValue("人民币");
        row2.createCell(5).setCellValue("外币");
        row2.createCell(6).setCellValue("时间");
        row2.createCell(7).setCellValue("备注");
        row2.createCell(8).setCellValue("凭证号");
        row2.createCell(9).setCellValue("名称");
        row2.createCell(10).setCellValue("编号");
        row2.createCell(11).setCellValue("收入类型");
        row2.createCell(12).setCellValue("收入属性");
        row2.createCell(13).setCellValue("收入部门");
        row2.createCell(14).setCellValue("收入");
        row2.createCell(15).setCellValue("押金");
        row2.createCell(16).setCellValue("认款人");
        row2.createCell(17).setCellValue("发票");
        row2.createCell(18).setCellValue("时间");

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 合并单元格：参数：起始行, 终止行, 起始列, 终止列
        CellRangeAddress cra = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(cra);
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 1, 8);
        sheet.addMergedRegion(cra1);
        CellRangeAddress cra2 = new CellRangeAddress(0, 0, 9, 18);
        sheet.addMergedRegion(cra2);
        row1.getCell(0).setCellStyle(style);
        row1.getCell(1).setCellStyle(style);
        row1.getCell(9).setCellStyle(style);
        try {
            FileOutputStream output=new FileOutputStream("expenditure.xls");
            wb.write(output);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
