package com.rjgf.echarts.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/8
 * @time: 20:23
 */
public class VelocityUtil {

    private static final String path = VelocityUtil.class.getClassLoader().getResource("").getPath();

    /**
     * 模板数据生成
     *
     * @param templateFileName
     * @param datas
     * @return
     * @throws IOException
     */
    public static String generateString(String templateFileName, Map<String, Object> datas) {

        /* 1.初始化 Velocity */
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
        velocityEngine.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, path + "static/echarts");
        velocityEngine.init();

        /* 2.创建一个上下文对象 */
        VelocityContext context = new VelocityContext();

        /* 3.添加你的数据对象到上下文 */
        datas.entrySet().forEach(s -> context.put(s.getKey(), s.getValue()));
        /* 4.选择一个模板 */
        Template template = velocityEngine.getTemplate(templateFileName);
        /* 5.将你的数据与模板合并，产生输出内容 */
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }


    public static void main(String[] args) throws IOException {
        Map<String, Object> datas = new HashMap<>();
        datas.put("title", "test");
        datas.put("subTitle", "test");
        datas.put("legendData", "['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']");
        datas.put("seriesName", "test");
        datas.put("seriesData", "[\n" +
                "                {value: 335, name: '直接访问'},\n" +
                "                {value: 310, name: '邮件营销'},\n" +
                "                {value: 234, name: '联盟广告'},\n" +
                "                {value: 135, name: '视频广告'},\n" +
                "                {value: 1548, name: '搜索引擎'}\n" +
                "            ]");
        String temp = generateString("pie.vm", datas);
        System.out.println(temp);
    }
}
