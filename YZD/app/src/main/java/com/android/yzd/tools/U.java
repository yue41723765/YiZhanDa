package com.android.yzd.tools;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
}
