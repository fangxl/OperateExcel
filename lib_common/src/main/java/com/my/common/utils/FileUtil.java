package com.my.common.utils;

import android.os.Build;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;

/**
 * @author zhanglong
 * @description
 * @date 2020/8/17 14:19
 */
public class FileUtil {
    /**
     * 断点上传控制每个断点字节大小
     */
    public static final int BUFFER_LENGTH = 1024 * 1024;

    /**
     * 获取sdcard路径
     * @return
     */
    public static String getSDPath () {
        String filePath = "";
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString();
        } else {
            filePath = Environment.getDownloadCacheDirectory().toString();
        }
        return filePath;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param file 将要删除的文件目录
     * @return
     */
    public static boolean delete (File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            for (File each : file.listFiles()) {
                boolean result = delete(each);
                if (!result) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static List<File> sortFileBylastModified (List<File> files) {
        Collections.sort(files, (file1, file2) -> (file1.lastModified() - file2.lastModified()) > 0 ? (-1) : 1);
        return files;
    }

    public static long getCreateTime (File file) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                return basicFileAttributes.creationTime().toMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 数组保存到文件中
     * @param bt
     */
    public static void saveBytes (byte[] bt, String fileName) throws Exception {
        // 创建指定路径的文件
        File file = new File(fileName);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        // 如果文件存在
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        // 获取文件的输出流对象
        FileOutputStream outStream = new FileOutputStream(file);
        // 获取字符串对象的byte数组并写入文件流
        outStream.write(bt);
        // 最后关闭文件输出流
        outStream.close();
    }

    public static void createFile (File file) throws IOException {
        if (file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            file.mkdirs();
        } else {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
    }

    /**
     * 读取文件
     */
    public static byte[] readFile (String filePath, long seek, long readLength)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return readFile(filePath, seek, readLength, BUFFER_LENGTH);
    }

    /**
     * 读取文件
     */
    public static byte[] readFile (String filePath, long seek, long readLength, int bufferLength)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        bufferLength = bufferLength <= 0 ? BUFFER_LENGTH : bufferLength;
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            try (FileChannel fc = raf.getChannel()) {
                long size = fc.size();
//                boolean isReadAll = false;
                if (readLength <= 0) {
//                    isReadAll = true;
                    readLength = size;
                }
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    long total = size - seek;
//                    long len = isReadAll ? (total < readLength ? total : readLength) : (total > readLength ? readLength : total);
                    long len = total < readLength ? total : readLength;
                    MappedByteBuffer mbbo = fc.map(FileChannel.MapMode.READ_ONLY, seek, len);
                    int readCount = 0;
                    byte[] buffer = new byte[bufferLength];
                    while (len > 0) {
                        readCount = (int) (len >= bufferLength ? bufferLength : len);
                        len -= readCount;
                        mbbo.get(buffer, 0, readCount);
                        byteArrayOutputStream.write(buffer, 0, readCount);
                    }
//                    unmap(fc, mbbo);    //释放
                    return byteArrayOutputStream.toByteArray();
                }
            }
        }
    }

    /**
     * MappedByteBuffer的释放操作
     */
    public static void unmap (FileChannel fco, MappedByteBuffer buffer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //sun.misc.Cleaner是内部专用 API, 可能会在未来发行版中删除
        //sun.nio.ch.DirectBuffer是内部专用 API, 可能会在未来发行版中删除
//        Cleaner cl = ((DirectBuffer)fco).cleaner();
//        if (cl != null){
//            cl.clean();
//        }
        Class<? extends FileChannel> clazz = fco.getClass();
        Method m = clazz.getDeclaredMethod("unmap",
                MappedByteBuffer.class);
        m.setAccessible(true);
        m.invoke(clazz, buffer);
    }
}