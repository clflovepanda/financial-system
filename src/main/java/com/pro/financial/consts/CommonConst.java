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
    //工时-审核通过
    public static final String project_task_status_template = "-1";
}
