/**
 * FileName: IPUtil Author:   sunny Date:     2021/1/4 1:20 History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.utils;

import org.springframework.util.StringUtils;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @author sunny
 * @create 2021/1/4
 * @since 1.0.0
 */
public class IPUtil {

    /**
     * 获取本地IP地址
     *
     * @throws SocketException
     */
    public static String getLocalIP() {
        if (isWindowsOS()) {
            return getWindowsLocalIP();
        } else {
            return getLinuxLocalIp();
        }
    }

    public static String localIP2String(){
        String ip=getLocalIP();
        if(ip!=null){
            String[] ips=ip.split("\\.");
            String ipnumber="";
            for(String i:ips){
                String ix=Integer.toHexString(Integer.parseInt(i));
                if(ix.length()<2) ix="0"+ix;
                ipnumber=ipnumber+ix;
            }
            return ipnumber;
        }
        return null;
    }

    public static String localIP2String(int radix){
        String ipAddress=getLocalIP();
        long result=0;
        if(ipAddress!=null){
            String[] ips=ipAddress.split("\\.");
            for (int i = 3; i >= 0; i--) {
                long ip = Long.parseLong(ips[3 - i]);
                result |= ip << (i * 8);
            }
            return Long.toString(result,radix);
        }
        return null;
    }



    /**
     * 判断操作系统是否是Windows
     *
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取本地Host名称
     */
    public static String getWindowsLocalIP()   {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {

        }
        return null;
    }


    public static boolean isIpV4(String ipStr){
        String[] ips=ipStr.split("\\.");
        if(ips.length!=4){
            return false;
        }
        for(String ip:ips){
            int ipNum=Integer.parseInt(ip);
            if(ipNum<0 || ipNum>255){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断该IP是否为局域网IP
     * @param ip
     * @return
     */
    public static boolean isLanIp(String ip){
        return null==ip
                ||"localhost".equalsIgnoreCase(ip)
                ||"127.0.0.1".equalsIgnoreCase(ip)
                ||ip.startsWith("172.")
                ||ip.startsWith("10.")
                ||ip.startsWith("192.168");
    }

    public static boolean isLanIp(String ipPattern,String ip){
        if(isIpV4(ip)){
            return ip.matches(ip);
        }
        return false;
    }

    /**
     * 获取Linux下的IP地址
     *
     * @return IP地址
     * @throws SocketException
     */
    private static String getLinuxLocalIp() {
        String localIp =null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface networkInterface = en.nextElement();
                if(networkInterface.isLoopback()||networkInterface.isVirtual()||!networkInterface.isUp()||networkInterface.isPointToPoint()){
                    continue;
                }
                Enumeration<InetAddress> addresses=networkInterface.getInetAddresses();
                while(addresses.hasMoreElements()){
                    InetAddress inetAddress=addresses.nextElement();
                    if(inetAddress instanceof Inet4Address){
                        localIp=inetAddress.getHostAddress();
                        if(inetAddress.isSiteLocalAddress()){
                            return localIp;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            return null;
        }
        return localIp;
    }

    public static String getWebRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String ipType="x-forwarded-for";
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            ipType="Proxy-Client-IP";
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip =request.getHeader("WL-Proxy-Client-IP");
            ipType="WL-Proxy-Client-IP";
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip =request.getHeader("HTTP_CLIENT_IP");
            ipType="HTTP_CLIENT_IP";
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            ipType="HTTP_X_FORWARDED_FOR";
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            ipType="remoteAddr";
        }
        return ipType+":"+ip;
    }

}
