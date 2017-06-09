/**
 * @author xiaohuan
 * 2015年6月13日
 */
package com.example.lcsrq.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
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

    /**
     * 获取非空字符串
     */
    public static String getNotNullStr(String str) {
        if (isNotNull(str))
            return str;
        return "";
    }

    /**
     * 毫秒数转日期字符串
     *
     * @param time   毫秒数
     * @param format 字符串格式
     * @return 日期字符串
     */
    public static String parseDateToString(long time, String format) {
        if (time <= 0)
            return "";
        return new SimpleDateFormat(format).format(new Date(time));
    }


    /*
    * 作用:
    * 参数说明:
    * 作者:*/
    public static int getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
         //  zy   2017-5-19    reason    ------------- begin
        if (w < 0)
            w = 0;
        return w;

        // ------------------- end


    }

    /**
     * 含T的日期字符串转换成yyyy-mm-dd hh:mm:ss
     *
     * @param timeStr String类型的时间
     * @return 日期字符串
     */
    public static String stringTimeFormat(String timeStr) {
        if (StringTool.isNotNull(timeStr)) {
            if (timeStr.contains("T")) {
                String[] tempArray = timeStr.split("T");
                if (tempArray.length == 2)
                    timeStr = tempArray[0] + " " + tempArray[1];
            }
        }

        return timeStr;
    }

    /**
     * 日期字符串转换成毫秒数
     *
     * @param dateTime
     * @return 毫秒数
     */
    public static long getLongFromString(String dateTime, String format) {

        if (StringTool.isNotNull(dateTime)) {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            try {
                Date date = sf.parse(dateTime);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 判断是否是电话号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        if (!isNotNull(mobiles))
            return false;
        mobiles = mobiles.trim();
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }



    /**
     * 通过name获取字符资源
     */
    public static String getStringByName(Context context, String name) {
        if (!isNotNull(name))
            return "无";
        String str = "";
        try {

            str = context.getString(context.getResources().getIdentifier(name, "string", context.getPackageName()));
        } catch (Exception e) {
            return "";
        }
        return str;
    }

    public static String cutShareStr(String info) {
        if (!StringTool.isNotNull(info)) return "点击了解更多";
        String rexgString = "(\\{img:[^{}]*\\})";
        info = info.replaceAll(rexgString, "");
        rexgString = "<[^>]*>"; // 去除所有的标签
        info = info.replaceAll(rexgString, "");
        info = info.replaceAll("\\r|\\t|\\n|\\f", "");
        rexgString = "&[^;]*;"; // 去除所有的标签
        info = info.replaceAll(rexgString, "");
        info = info.replaceAll(" ", "");
        if (info.length() > 110) {
            info = info.substring(0, 110);
        }
        info += "点击了解更多";

        return info;
    }

    /**
     * 获取字符串前6个字符
     * return
     */
    public static String getPrefixString(String str) {
        if (!TextUtils.isEmpty(str) && str.length() > 6) {
            return str.substring(0, 5);
        }
        return str;
    }

    /**
     * 判断是否是6位纯数字
     */

    public static boolean isSixNumber(String yzmNum) {
        String regExp = "[0-9]+";
        return yzmNum.matches(regExp) && yzmNum.toString().length() == 6;
    }

    /**
     * 注意事项：
     * 1、月份从0开始，0代表一月
     * 2、当月份参数month的实际天数小于31天时，打印的月份结果都是加1
     *
     * @param year
     * @param month
     * @return
     */
    public static int judgeDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1); // 设置日期
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当前时间转成yyyy-MM-dd
     */

    public static String getCalendarTime() {
        Calendar cl = Calendar.getInstance();
        Date time = cl.getTime();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        return ft.format(time);//返回格式化的字符串，日期


    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatDate(String date, boolean isStart) {
        if ("".equals(date)) {
            return "";
        }
        String[] times = date.split("-");
        String year = times[0];
        String month = times[1];
        String day = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (isStart) {
            day = "01";
        } else {
            int totalDay = judgeDay(Integer.parseInt(year), (Integer.parseInt(month)));
            if (totalDay < 10) {
                day = "0" + totalDay;
            } else {
                day = totalDay + "";
            }
        }
        return year + month + day;
    }

    /**
     * 判断邮箱是否正确
     */

    public static boolean ifEmail(String email) {

        String regExp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regExp);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }


    /**
     * 获取当前年
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }
}
