package com.rjgf.echarts.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

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
public class EchartsHelper {

    private static String url = "http://localhost:6666";

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
        String response = HttpUtil.post(url, params);

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
}
