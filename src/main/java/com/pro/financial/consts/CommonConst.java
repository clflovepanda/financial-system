package com.pro.financial.consts;

public class CommonConst {
    //通用有效
    public static final String common_valid = "1";
    //通用无效
    public static final String common_invalid = "0";

    //cookie 权限
    public static final String cookie_permission_head = "permission_";
    //cookie 数据
    public static final String cookie_datasource_head = "datasource_";
    //cookie 用户信息
    public static final String cookie_user_head = "user_id";
    public static final String cookie_user_name_head = "user_name";

    //合同首字母
    public static final String initials_contract = "HT";
    //报价单首字母
    public static final String initials_quotation = "BJD";
    //结算单首字母
    public static final String initials_settlement = "JSD";
    //应收单首字母
    public static final String initials_receivable = "YSD";
    //收入标号
    public static final String initials_revenue = "SR";
    public static final String initials_project = "PRO";

    //支出首字母
    public static final String initials_expenditure = "ZC";

    //应收发票
    public static final String initials_invoice = "IN";


    //对应projecttask task_status
    //工时状态-初始化
    public static final String task_status_init = "00";
    //工时状态-已分配
    public static final String task_status_distribution = "01";
    //工时状态-已确认
    public static final String task_status_confirm = "02";

    //对应projecttask status
    //工时-审核中
    public static final String project_task_status_review = "0";
    //工时-审核通过
    public static final String project_task_status_approved = "1";
    //工时-模板
    public static final String project_task_status_template = "-1";

    //支出审批状态
    public static final Integer expenditure_audit_type_not_submit = 0;
    public static final Integer expenditure_audit_type_submit = 1;
    public static final Integer expenditure_audit_type_reviewed = 2;
    public static final Integer expenditure_audit_type_rejected = 3;
    public static final Integer expenditure_audit_type_paid = 4;
    public static final Integer expenditure_audit_type_cancel = 5;
    public static final Integer expenditure_audit_type_flat = 6;

    //押金导出表头
    public static final String[] export_deposit = new String[]{"序号","押金编号","到款账户","到款种类","汇款方","认款类型","认款金额/元","认款人","认款时间","待退回金额/元","退回中押金/元","已退回押金/元"};
}
