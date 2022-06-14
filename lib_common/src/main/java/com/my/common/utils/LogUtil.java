package com.my.common.utils;

import android.text.TextUtils;
import android.util.Log;

import com.my.common.log4j.Log4jConfigure;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName : LogUtil
 * @Description : 日志输出
 * @Author : chenshaopeng
 * @Date: 2020-07-31 15:12
 */
public class LogUtil {
    /**
     * log开关,是否打印log
     */
    public static boolean SWITCH_LOG = true;
    /**
     * log开关,是否将log写入日志文件
     */
    public static boolean SWITCH_WRITE_LOG = true;
    private static boolean isConfigured = false;
    private static boolean isOutputScreen = false;

    public static void d (String message) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.debug(message);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.d(tag, message);
        }

    }

    /**
     * 方法调用栈，用于记录方法在哪里被调用
     * @return
     */
    public static String getCallerInfo () {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement;
        if (stackTrace.length < 5) {
            stackTraceElement = stackTrace[stackTrace.length - 1];
        } else {
            stackTraceElement = stackTrace[4];
        }
        Log.d("stackTraceElement", stackTraceElement.getClassName());
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        if (TextUtils.isEmpty(methodName)) {
            methodName = "  ";
        }
        int lineNumber = stackTraceElement.getLineNumber();
        return getSimpleName(className) + ":" + methodName + ":" + lineNumber;
    }

    private static String getSimpleName (String fullname) {
        if (TextUtils.isEmpty(fullname)) {
            return "";
        }
        String[] strs1 = fullname.split("\\$");
        if (strs1 == null || strs1.length == 0) {
            return "";
        }
        String[] strs2 = strs1[0].split("\\.");
        if (strs2 == null || strs2.length == 0) {
            return "";
        }
        return strs2[strs2.length - 1];
    }

    public static void e (Throwable e) {
        LogUtil.e(e.getMessage());
        while (e.getCause() != null) {
            LogUtil.e(e.getCause());
        }
    }

    public static void dMap (Map map) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        LogUtil.d("tag---" + getCallerInfo());
        for (Map.Entry<String, Object> entry : entries) {
            LogUtil.d("---key:" + entry.getKey() + "---" + "value:" + entry.getValue().toString());
        }
    }

    public static void d (String message, Throwable exception) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.debug(message, exception);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.d(tag, message, exception);
        }
    }

    public static void i (String message) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.info(message);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.i(tag, message);
        }
    }

    public static void i (String message, Throwable exception) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.info(message, exception);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.i(tag, message, exception);
        }
    }

    public static void w (String message) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.warn(message);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.w(tag, message);
        }
    }

    public static void w (String message, Throwable exception) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.warn(message, exception);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.w(tag, message, exception);
        }
    }

    public static void e (String message) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.error(message);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.e(tag, message);
        }
    }

    public static void e (String message, Throwable exception) {
        String tag = getCallerInfo();
        if (SWITCH_LOG && SWITCH_WRITE_LOG) {
            Logger LOGGER = getLogger(Thread.currentThread().getName() + "==>" + tag);
            LOGGER.error(message, exception);
        } else if (SWITCH_LOG && !SWITCH_WRITE_LOG) {
            Log.e(tag, message, exception);
        }
    }

    private static Logger getLogger (String tag) {
        if (!isConfigured) {
            Log4jConfigure.configure();
            isConfigured = true;
        }
        Logger logger;
        if (TextUtils.isEmpty(tag)) {
            logger = Logger.getRootLogger();
        } else {
            logger = Logger.getLogger(tag);
        }
        return logger;
    }
}
