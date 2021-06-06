package com.rjgf.echarts.util;

import cn.hutool.core.lang.UUID;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/10
 * @time: 15:00
 */
public class FileUtil {

    /**
     * 图片的保存地址
     */
    private static String imgPath = "E:\\Users\\xulia\\公司项目\\润建\\app大数据\\报告模块\\img\\";


    /**
     * 获取字节数组
     *
     * @param base64
     * @return
     * @throws IOException
     */
    public static byte[] base64ToByte(String base64) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(base64);
        if (base64 == null) {
            return null;
        }
        String imgUrl = imgPath + UUID.fastUUID() + ".png";
        File file = new File(imgUrl);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return decoder.decodeBuffer(base64);
    }

    /**
     * 获取echarts图片地址
     *
     * @return
     */
    public static String getImgUrl(String base64) {
        if (base64 == null) {
            return null;
        }
        String imgUrl = imgPath + UUID.fastUUID() + ".png";
        File file = new File(imgUrl);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(base64ToByte(base64));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgUrl;
    }
}
