package com.my.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 吐司工具类,需要初始化
 * @author zhanglong
 */
public class ToastUtils {
    private static Toast toast;

    public static void init (Context context) {
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
    }

    public static void showText (String text) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        LogUtil.d( "显示吐司:" + text);
        toast.setText(text);
        toast.show();
    }
}
