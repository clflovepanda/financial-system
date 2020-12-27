package com.pro.financial.utils;

import java.text.NumberFormat;
import java.util.HashMap;

public class SimpleMoneyFormat {
        public static final String EMPTY = "";
        public static final String ZERO = "零";
        public static final String ONE = "壹";
        public static final String TWO = "贰";
        public static final String THREE = "叁";
        public static final String FOUR = "肆";
        public static final String FIVE = "伍";
        public static final String SIX = "陆";
        public static final String SEVEN = "柒";
        public static final String EIGHT = "捌";
        public static final String NINE = "玖";
        public static final String TEN = "拾";
        public static final String HUNDRED = "佰";
        public static final String THOUSAND = "仟";
        public static final String TEN_THOUSAND = "万";
        public static final String HUNDRED_MILLION = "亿";
        public static final String YUAN = "元";
        public static final String JIAO = "角";
        public static final String FEN = "分";
        public static final String DOT = ".";
        private static SimpleMoneyFormat formatter = null;
        private HashMap<String, String> chineseNumberMap = new HashMap();
        private HashMap<String, String> chineseMoneyPattern = new HashMap();
        private NumberFormat numberFormat = NumberFormat.getInstance();

        private SimpleMoneyFormat() {
            this.numberFormat.setMaximumFractionDigits(4);
            this.numberFormat.setMinimumFractionDigits(2);
            this.numberFormat.setGroupingUsed(false);
            this.chineseNumberMap.put("0", "零");
            this.chineseNumberMap.put("1", "壹");
            this.chineseNumberMap.put("2", "贰");
            this.chineseNumberMap.put("3", "叁");
            this.chineseNumberMap.put("4", "肆");
            this.chineseNumberMap.put("5", "伍");
            this.chineseNumberMap.put("6", "陆");
            this.chineseNumberMap.put("7", "柒");
            this.chineseNumberMap.put("8", "捌");
            this.chineseNumberMap.put("9", "玖");
            this.chineseNumberMap.put(".", ".");
            this.chineseMoneyPattern.put("1", "拾");
            this.chineseMoneyPattern.put("2", "佰");
            this.chineseMoneyPattern.put("3", "仟");
            this.chineseMoneyPattern.put("4", "万");
            this.chineseMoneyPattern.put("5", "拾");
            this.chineseMoneyPattern.put("6", "佰");
            this.chineseMoneyPattern.put("7", "仟");
            this.chineseMoneyPattern.put("8", "亿");
        }

        public static synchronized SimpleMoneyFormat getInstance() {
            if (formatter == null) {
                formatter = new SimpleMoneyFormat();
            }

            return formatter;
        }

        public String format(String moneyStr) {
            this.checkPrecision(moneyStr);
            String result = this.convertToChineseNumber(moneyStr);
            result = this.addUnitsToChineseMoneyString(result);
            return result;
        }

        public String format(double moneyDouble) {
            return this.format(this.numberFormat.format(moneyDouble));
        }

        public String format(int moneyInt) {
            return this.format(this.numberFormat.format((long)moneyInt));
        }

        public String format(long moneyLong) {
            return this.format(this.numberFormat.format(moneyLong));
        }

        public String format(Number moneyNum) {
            return this.format(this.numberFormat.format(moneyNum));
        }

        private String convertToChineseNumber(String moneyStr) {
            StringBuffer cMoney = new StringBuffer();

            int indexOfDot;
            for(indexOfDot = 0; indexOfDot < moneyStr.length(); ++indexOfDot) {
                cMoney.append((String)this.chineseNumberMap.get(moneyStr.substring(indexOfDot, indexOfDot + 1)));
            }

            indexOfDot = cMoney.indexOf(".");
            int moneyPatternCursor = 1;

            for(int i = indexOfDot - 1; i > 0; --i) {
                cMoney.insert(i, (String)this.chineseMoneyPattern.get("" + moneyPatternCursor));
                moneyPatternCursor = moneyPatternCursor == 8 ? 1 : moneyPatternCursor + 1;
            }

            String fractionPart = cMoney.substring(cMoney.indexOf("."));
            cMoney.delete(cMoney.indexOf("."), cMoney.length());

            while(cMoney.indexOf("零拾") != -1) {
                cMoney.replace(cMoney.indexOf("零拾"), cMoney.indexOf("零拾") + 2, "零");
            }

            while(cMoney.indexOf("零佰") != -1) {
                cMoney.replace(cMoney.indexOf("零佰"), cMoney.indexOf("零佰") + 2, "零");
            }

            while(cMoney.indexOf("零仟") != -1) {
                cMoney.replace(cMoney.indexOf("零仟"), cMoney.indexOf("零仟") + 2, "零");
            }

            while(cMoney.indexOf("零万") != -1) {
                cMoney.replace(cMoney.indexOf("零万"), cMoney.indexOf("零万") + 2, "万");
            }

            while(cMoney.indexOf("零亿") != -1) {
                cMoney.replace(cMoney.indexOf("零亿"), cMoney.indexOf("零亿") + 2, "亿");
            }

            while(cMoney.indexOf("零零") != -1) {
                cMoney.replace(cMoney.indexOf("零零"), cMoney.indexOf("零零") + 2, "零");
            }

            if (cMoney.lastIndexOf("零") == cMoney.length() - 1) {
                cMoney.delete(cMoney.length() - 1, cMoney.length());
            }

            cMoney.append(fractionPart);
            String result = cMoney.toString();
            return result;
        }

        private String addUnitsToChineseMoneyString(String moneyStr) {
            StringBuffer cMoney = new StringBuffer(moneyStr);
            int indexOfDot = cMoney.indexOf(".");
            cMoney.replace(indexOfDot, indexOfDot + 1, "元");
            cMoney.insert(cMoney.length() - 1, "角");
            cMoney.insert(cMoney.length(), "分");
            if (cMoney.indexOf("零角零分") != -1) {
                cMoney.replace(cMoney.indexOf("零角零分"), cMoney.length(), "整");
            } else if (cMoney.indexOf("零分") != -1) {
                cMoney.replace(cMoney.indexOf("零分"), cMoney.length(), "整");
            } else if (cMoney.indexOf("零角") != -1) {
                cMoney.delete(cMoney.indexOf("零角"), cMoney.indexOf("零角") + 2);
            }

            String result = cMoney.toString();
            return result;
        }

        private void checkPrecision(String moneyStr) {
            int fractionDigits = moneyStr.length() - moneyStr.indexOf(".") - 1;
            if (fractionDigits > 2) {
                throw new RuntimeException("金额" + moneyStr + "的小数位多于两位。");
            }
        }
}
