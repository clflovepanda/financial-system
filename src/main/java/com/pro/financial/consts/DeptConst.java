package com.pro.financial.consts;

import java.util.HashMap;
import java.util.Map;

public class DeptConst {
    public static final Map<Integer, String> department = new HashMap<Integer, String>() {
        {
            put(1,"IT部");
            put(2,"执行部");
            put(3,"行政");
            put(4,"财务");
            put(5,"主场部");
            put(6,"搭建");
            put(7,"销售");
            put(8,"医学发展");
        }
    };
}
