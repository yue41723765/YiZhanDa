package com.android.yzd.tools;

import android.app.Activity;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/30.
 */

public class U {


    /**
     * 设置添加屏幕的背景透明度
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 是否为手机号 ture--是
     */
    public static boolean isTel(String tel) {
        Pattern p = Pattern.compile("1\\d{10}");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    public static void setWindows(Activity activity, float alphe) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alphe;
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 时间戳转为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToStr_(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        long lcc_time = Long.valueOf(timestamp);
        String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 时间戳转为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToStrDMY(long timestamp) {
//
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return String.format(sdf.format(new Date(timestamp)), calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    /**
     * 时间戳转为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToStr(String timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lcc_time = Long.valueOf(timestamp);
        String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
}
