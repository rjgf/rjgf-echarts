package com.rjgf.echarts.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Echarts 生成帮助类
 *
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/8
 * @time: 20:39
 */
@Component
public class EchartsHelper {

    /**
     * echarts 生成服务地址
     */
    private static String serverUrl;

    /**
     * echarts 图片存放地址
     */
    private static String imgPath;


    public EchartsHelper(EchartsFileProperties echartsFileProperties) {
        serverUrl = echartsFileProperties.getServerUrl();
        imgPath = echartsFileProperties.getImgPath();
    }

    /**
     * 获取图表的base64 信息
     * @return
     */
    public static String getEChartsImg(String option) throws IOException {
        String base64 = "";
        if (option == null) {
            return null;
        }
        option = option.replaceAll(" ", "").replaceAll("\\s+", "").replaceAll("\"", "'");

        // 将option字符串作为参数发送给echartsConvert服务器
        Map<String, Object> params = new HashMap<>();
        params.put("opt", option);
        String response = HttpUtil.post(serverUrl, params);

        // 解析echartsConvert响应
        JSONObject jsonObject = JSONUtil.parseObj(response);
        String code = jsonObject.getStr("code");

        // 如果echartsConvert正常返回
        if ("1".equals(code)) {
            base64 = jsonObject.getStr("data");
        }
        // 未正常返回
        else {
            String string = jsonObject.getStr("msg");
            throw new RuntimeException(string);
        }
        return base64;
    }


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
