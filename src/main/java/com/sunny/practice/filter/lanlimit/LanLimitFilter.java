/**
 * FileName: LanLimitFilter Author:   sunny Date:     2021/1/4 1:34 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.filter.lanlimit;

import com.alibaba.fastjson.JSON;
import com.sunny.practice.utils.ResBean;
import com.sunny.practice.utils.exception.ResultCodeEnum;

import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2021/1/4
 * @since 1.0.0
 */
@Slf4j
public class LanLimitFilter implements Filter {
    private final static String INTERNET_FLAG="internet";

    @Value("${filter.custom.lanlimit.igoreIP:false}")
    private boolean ipIgore=false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        String ip=analysisIpAddress(httpServletRequest);
        String from=httpServletRequest.getHeader("YZS-Request-From");
        if(!isFromLan(ip,from)){
            log.warn("内部服务{},被外网调用(YZS-Request-From {}),来源IP：{} ,已被拦截",httpServletRequest.getRequestURI(),from,ip);
            responseLimitMsg((HttpServletResponse)servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void responseLimitMsg(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(404);//指定的资源不存在
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getOutputStream().write(JSON.toJSONString(ResBean.builder(ResultCodeEnum.请求的资源不存在.flag,ResultCodeEnum.请求的资源不存在.defaultMsg)).getBytes());
    }

    private  String analysisIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if(Objects.nonNull(ip)){
            return ip;
        }else{
            String ip2 = request.getHeader("X-Forwarded-For");
            if(Objects.nonNull(ip2)){
                return ip2.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }

    private boolean isFromLan(String ip,String from){
        if(Objects.nonNull(from)){
            if(from.toLowerCase().contains("nginx")){
                log.warn("本次请求来源于 nginx,");
                return false;
            }
        }
        if(ipIgore){
            return true;
        }else {

            return Objects.nonNull(ip) && ("localhost".equalsIgnoreCase(ip)
                    || "127.0.0.1".equalsIgnoreCase(ip)
                    || ip.startsWith("172.16")
                    || ip.startsWith("10")
                    || ip.startsWith("192.168"));
        }
    }
}
