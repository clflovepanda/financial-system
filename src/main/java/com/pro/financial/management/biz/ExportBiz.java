package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.ExportUtil;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
            //状态(0:未提交 1:已提交 2:审核中 3:被驳回 4:已支付 5:作废 6:平借款)
            for (ExpenditureDto expenditureDto : expenditureDtos) {
                String stateStr = "";
                switch (expenditureDto.getState()) {
                    case 0:
                        stateStr = "已提交";
                    case 1:
                        stateStr = "已提交";
                    case 2:
                        stateStr = "已提交";
                    case 3:
                        stateStr = "已提交";
                        break;
                    case 4:
                        stateStr = "已支付";
                        break;
                    case 5:
                        stateStr = "已驳回";
                        break;
                    case 6:
                        stateStr = "平借款";
                        break;
                }
                String auditTypeStr = "";
                switch (expenditureDto.getState()) {
                    case 0:
                        auditTypeStr = "财务审批";
                    case 1:
                        auditTypeStr = "财务审批";
                    case 2:
                        auditTypeStr = "财务审批";
                    case 3:
                        auditTypeStr = "财务审批";
                        break;
                    case 4:
                        auditTypeStr = "已支付";
                        break;
                    case 5:
                        auditTypeStr = "已驳回";
                        break;
                    case 6:
                        auditTypeStr = "平借款";
                        break;
                }
                String[] line = new String[]{i+"", expenditureDto.getNumbering(), expenditureDto.getProjectNo(), expenditureDto.getProjectName() , expenditureDto.getCoName(), expenditureDto.getExpenditureMethodName(),
                        expenditureDto.getExpenditureTypeName(), expenditureDto.getExpenditurePurposeName(), expenditureDto.getBeneficiaryUnit(), expenditureDto.getExpenditureMoney()+"",
                        expenditureDto.getUsername(), expenditureDto.getCtime()+"",
                        stateStr,
                        expenditureDto.getUtime()+"", auditTypeStr};
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
        try {
            InputStream inputStream = null;
            if (StringUtils.isNotEmpty(fileName)) {
                inputStream = new FileInputStream(fileName);
            } else {
                inputStream = new FileInputStream(excelFileName);
            }
            OSS client = new OSSClientBuilder().build(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
            client.putObject(OSS_BUKET, "export/" + excelFileName, inputStream);
            client.shutdown();
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
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
                String[] line = new String[]{projectDto.getProjectId()+"", projectDto.getCode(), projectDto.getName(), projectDto.getManagerName(), "",
                        projectDto.getSettlementIncome() == null ? "" : projectDto.getSettlementIncome()+"",
                        projectDto.getRelRevenue() == null ? "" : projectDto.getRelRevenue()+"",
                        projectDto.getReceivable() == null ? "" : projectDto.getReceivable()+""};
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

    public JSONObject exportStatisticsReceivement(List<ReceivementEntity> receivementEntities) {
        JSONObject result = new JSONObject();
        String excelFileName = "receivement_" + System.currentTimeMillis() + ".xls";
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < receivementEntities.size(); i++) {
            ReceivementEntity receivementEntity = receivementEntities.get(i);
            //前两行是表头 从第三行还是写
            HSSFRow row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(receivementEntity.getId());
            row.createCell(1).setCellValue(receivementEntity.getCoName());
            row.createCell(2).setCellValue(receivementEntity.getRemitter());
            row.createCell(3).setCellValue(receivementEntity.getReceivementTypeName());
            row.createCell(4).setCellValue(receivementEntity.getReceivementMoney()+"");
            row.createCell(5).setCellValue(receivementEntity.getForeignMoney());
            row.createCell(6).setCellValue(simpleDateFormat.format(receivementEntity.getReceiveDate()));
            row.createCell(7).setCellValue(receivementEntity.getRemark());
            row.createCell(8).setCellValue(receivementEntity.getVoucherNo());
            row.createCell(9).setCellValue(receivementEntity.getProjectName());
            row.createCell(10).setCellValue(receivementEntity.getProjectNo());
            row.createCell(11).setCellValue(receivementEntity.getRevenueTypeName());
            row.createCell(12).setCellValue(receivementEntity.getDataSourceName());
            row.createCell(13).setCellValue(receivementEntity.getRevenueDept());
            row.createCell(14).setCellValue(receivementEntity.getCnyMoney() == null ? "" : receivementEntity.getCnyMoney()+"");
            row.createCell(15).setCellValue(receivementEntity.getDeposit() == null ? "" : receivementEntity.getDeposit()+"");
            row.createCell(16).setCellValue(receivementEntity.getUsername());
            row.createCell(17).setCellValue(receivementEntity.getInvoice());
            row.createCell(18).setCellValue(simpleDateFormat.format(receivementEntity.getCtime()));
        }
        try {
            FileOutputStream output=new FileOutputStream(excelFileName);
            wb.write(output);
            output.flush();
            String url = this.upload2OSSXls("", excelFileName);
            result.put("code", 0);
            result.put("msg", "");
            result.put("url", url);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 2001);
            result.put("msg", "导出失败");
        }

        return result;
    }
}
