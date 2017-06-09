package com.example.lcsrq.crame;

/**
 * Created by 苏毅 on 2017/4/14.
 */

public class StringTool {

    /**
     * 判断是否为非空字符串
     */
    public static boolean isNotNull(String str) {
        if (str != null && !"".equals(str.trim()) && !"null".equalsIgnoreCase(str.trim()))
            return true;
        return false;
    }

}
