package com.songsy.springboot.api.limit.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author songsy
 * @date 2019/11/1 18:22
 */
public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String xFor = null;
        String xip = null;
        try {
            xip = request.getHeader("X-Real-IP");
            xFor = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
                //多次反向代理后会有多个ip值，第一个ip才是真实ip
                int index = xFor.indexOf(",");
                if (index != -1) {
                    return xFor.substring(0, index);
                } else {
                    return xFor;
                }
            }
            xFor = xip;
            if (StringUtils.isNotEmpty(xFor) && !"unKnown".equalsIgnoreCase(xFor)) {
                return xFor;
            }
            if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                xFor = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                xFor = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                xFor = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isBlank(xFor) || "unknown".equalsIgnoreCase(xFor)) {
                xFor = request.getRemoteAddr();
            }
        } catch (Exception e) {
            logger.error("获取ip错误" + e);
        }
        return xFor;
    }
}
