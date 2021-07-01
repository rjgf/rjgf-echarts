package com.rjgf.echarts.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @create 2021-06-07 12:06
 */
@Component
@ConfigurationProperties(prefix = "echarts")
@Data
public class EchartsFileProperties {

    /**
     * echarts 生成服务地址
     */
    private String serverUrl;

    /**
     * echarts 图片存放地址
     */
    private String imgPath;
}
