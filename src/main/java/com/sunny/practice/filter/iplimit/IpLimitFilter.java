/**
 * FileName: IpLimitFilter Author:   sunny Date:     2021/1/4 1:07 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.filter.iplimit;

import com.alibaba.fastjson.JSON;
import com.sunny.practice.utils.IPUtil;
import com.sunny.practice.utils.ResBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
public class IpLimitFilter implements Filter {

    private boolean  useWhiteList=false;
    private int interval=10;
    private int frequency=10;

    private final static String FREQ_KEY_PREFIX="FREQUENCY-IP-LIMIT-J2D29fs=S:";
    private final static String WHITE_KEY_PREFIX="WHITE-IP-LIMIT-J2D29fs=S:";
    private final static String BLACK_KEY_PREFIX="BLACK-IP-LIMIT-J2D29fs=S:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.useWhiteList=Boolean.parseBoolean(filterConfig.getInitParameter("useWhiteList"));
        String intervalStr=filterConfig.getInitParameter("interval");
        String frequencyStr=filterConfig.getInitParameter("frequency");
        if(null!=intervalStr) {
            this.interval= Integer.parseInt(intervalStr);
            if(this.interval<1){
                this.interval=1;
            }
        }
        if(null!=frequencyStr){
            this.frequency= Integer.parseInt(frequencyStr);
            if(this.frequency<1){
                this.frequency=1;
            }
        }
        if(log.isInfoEnabled()){
            log.info("IP访问限制是否启用白名单：{}",this.useWhiteList);
            log.info("IP访问现在访问时间周期：{}秒",this.interval);
            log.info("IP访问现在访问次数：{}次",this.frequency);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        String ip= analysisIpAddress(httpServletRequest);
        if(IPUtil.isLanIp(ip) || this.useWhiteList && inWhiteList(ip)){
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }

        if(inBackList(ip)){
            responseLimitMsg((HttpServletResponse)servletRequest);
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

        //通过黑白名单校验后，进行频率限制
        int number = frequencyLimit(ip);
        int times = number / this.frequency;
        if(number>0 &&times<5){
            if (times >= 0 && number%this.frequency==0) {
                addBackList(ip,(int)(Math.pow(5,times)));
            }
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 黑名单模式，指定名单不可访问
     * @return
     */
    private boolean inBlackModel(String ip){
        return "1".equals(stringRedisTemplate.opsForValue().get(BLACK_KEY_PREFIX+ip));
    }

    /**
     * 白名单模式，仅限指定名单访问
     * @return
     */
    private boolean inWhiteList(String ip){
        return "1".equals(stringRedisTemplate.opsForValue().get(WHITE_KEY_PREFIX+ip));
    }

    private boolean inBackList(String ip){
        return  "1".equals(stringRedisTemplate.opsForValue().get(BLACK_KEY_PREFIX+ip));
    }

    private void addBackList(String ip,int minutes){
        stringRedisTemplate.opsForValue().set(BLACK_KEY_PREFIX+ip,"1",minutes, TimeUnit.MINUTES);
    }

    /**
     * 频率校验
     * @return  是否超过限制
     */
    private int frequencyLimit(String ip){
        String limit_key=FREQ_KEY_PREFIX+ip;
        Long total=stringRedisTemplate.opsForValue().increment(limit_key,1);
        if(null!=total){
            if(1==total.intValue()){
                /* 设置失效时间 */
                stringRedisTemplate.expire(limit_key,interval, TimeUnit.SECONDS);
            }
            return total.intValue()-frequency;
        }else{
            //这个版本的spring-data-redis，setIfAbsent只支持两个参数,不支持设置过期时间,高版本之后支持四个参数
            //采用高版本后,写法可改进
            if(!stringRedisTemplate.hasKey(limit_key)){
                stringRedisTemplate.opsForValue().set(limit_key,"1",interval,TimeUnit.SECONDS);
            }
        }
        return 0;
    }

    private void responseLimitMsg(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(429);//访问次数太多
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getOutputStream().write(JSON.toJSONString(ResBean.builder(600, "IP访问限制")).getBytes());
    }

    private  String analysisIpAddress(HttpServletRequest request) {
        String ip=request.getHeader("X-Real-IP");

        if(null==ip||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip =request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip =request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
