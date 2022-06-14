package com.my.common.log4j;

import android.os.Environment;

import org.apache.log4j.Level;
import org.litepal.BuildConfig;

import java.io.File;

/**
 * @ClassName : Log4jConfigure
 * @Description : Log4J
 * @Author : chenshaopeng
 * @Date: 2020-07-31 15:04
 */
public class Log4jConfigure {
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 3;
    public static final String DEFAULT_LOG_DIR = "/fang/log/";
    public static final String TOOL_LOG_DIR = "/fang/toolLog/";
    public static final String OLD_LOG_DIR = "/fang/newlog/";
    private static final String DEFAULT_LOG_FILE_NAME = "app.log";
    private static final String TAG = "Log4jConfigure";
    // 对应AndroidManifest文件中的package
    private static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    /** 生产环境下的log等级 **/
    private static final Level LOG_LEVEL_PRODUCE = Level.ALL;
    /** 发布以后的log等级 **/
    private static final Level LOG_LEVEL_RELEASE = Level.INFO;
    public static boolean isConfigSuccess = false;

    public static void configure (String fileName) {
        final LogConfigurator logConfigurator = new LogConfigurator();
        try {
            if (isSdcardMounted()) {
                logConfigurator.setFileName(Environment.getExternalStorageDirectory().getAbsolutePath() + DEFAULT_LOG_DIR + fileName);
            } else {
                logConfigurator.setFileName("//data//data//" + PACKAGE_NAME + "//files" + File.separator + fileName);
            }
            //追加
            logConfigurator.setImmediateFlush(true);
            // 设置日志等级
            logConfigurator.setRootLevel(LOG_LEVEL_PRODUCE);
            //格式
            logConfigurator.setFilePattern("%d\t%p/%c:\t%m%n");
//            logConfigurator.setMaxBackupSize(2);
            logConfigurator.configure();
            android.util.Log.e(TAG, "Log4j config finish");
            isConfigSuccess = true;
        } catch (Throwable throwable) {
            logConfigurator.setResetConfiguration(true);
            android.util.Log.e(TAG, "Log4j config error, use default config. Error:" + throwable);
            isConfigSuccess = false;
        }
    }

    public static void configure () {
        configure(DEFAULT_LOG_FILE_NAME);
    }

    private static boolean isSdcardMounted () {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
