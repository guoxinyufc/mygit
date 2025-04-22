package com.example.finalwork.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
    //读取文件内容，作为字符串返回
    public static String readFileAsString(String filePath) throws IOException{
        File file=new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }
        StringBuilder sb=new StringBuilder((int)(file.length()));
        FileInputStream inputStream=new FileInputStream(filePath);//创建字节输入流
        byte[] buf=new byte[10240];//创建一个长度为10240的缓冲区
        int hasRead=0;//保存实际读取的字节
        while ( (hasRead = inputStream.read(buf)) > 0 ) {
            sb.append(new String(buf, 0, hasRead));
        }
        inputStream.close();
        return sb.toString();
    }
    //根据文件路径读取byte[]数组
    public static byte[] readFileByBytes(String filePath) throws IOException{
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream((int)(file.length()));
            BufferedInputStream bufferedInputStream=null;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = bufferedInputStream.read(buffer, 0, bufSize))) {
                    byteArrayOutputStream.write(buffer, 0, len1);
                }

                byte[] var7 = byteArrayOutputStream.toByteArray();
                return var7;
            } finally {
                try {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
                byteArrayOutputStream.close();
            }
        }
    }
}
