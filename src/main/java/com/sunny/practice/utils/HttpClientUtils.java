/**
 * FileName: HttpClientUtils
 * Author:   sunny
 * Date:     2018/10/19 18:05
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.utils;

/**
 * @description
 * @author sunny
 * @create 2018/10/19
 * @since 1.0.0
 */
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClientUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志记录
    private static RequestConfig requestConfig = null;

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject doPost(String url, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    public static JSONObject doPost(String url, JSONObject jsonParam, JSONObject headers) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                httpPost.setHeader(key, headers.getString(key));
            }
        }
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    public static JSONObject doPost(String url, String strParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url      url地址
     * @param strParam 参数
     * @return
     */
    public static JSONObject doPost(String url, String strParam, JSONObject headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                httpPost.setHeader(key, headers.getString(key));
            }
        }
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url     路径
     * @param headers
     * @return
     */
    public static JSONObject doGet(String url, JSONObject headers) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                request.setHeader(key, headers.getString(key));
            }
        }
        try {
            CloseableHttpResponse response = httpClient.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }


    /**
     * @Description: doPut方法
     * @Author: sunny
     * @Date: 2018/9/6 14:34
     */
    public static JSONObject doPut(String url, String strParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        JSONObject jsonResult = null;
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPut.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPut);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPut.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * @Description: doPut方法
     * @Author: sunny
     * @Date: 2018/9/7 13:50
     */
    public static JSONObject doPut(String url, JSONObject jsonParam, JSONObject headers) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (StringUtils.isNotBlank(url) && url.startsWith("https"))
            httpClient = createSSLClientDefault();
        JSONObject jsonResult = null;
        HttpPut httpPut = new HttpPut(url);
        // 设置请求和传输超时时间
        httpPut.setConfig(requestConfig);
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                httpPut.setHeader(key, headers.getString(key));
            }
        }
        try {
            if (null != jsonParam && !jsonParam.isEmpty()) {
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPut.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPut);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPut.releaseConnection();
        }
        return jsonResult;
    }

    public static void main(String[] args) {
/*		StringBuilder postParams = new StringBuilder();
		postParams.append("tokenUrl=").append("https://api.xiaoshouyi.com/oauth2/token");
		postParams.append("&grant_type=").append("password");
		postParams.append("&client_id=").append("0b42ebdbbb509489a15aaca051c0f8d3");
		postParams.append("&client_secret=").append("9d0f24fff546b374aaa64ded5a641b56");
		postParams.append("&username=").append("cooker.chen@v.photos");
		postParams.append("&password=").append("1qaz2WSXRgFLOAoA");
		System.out.println(HttpClientUtils.doPost("https://api.xiaoshouyi.com/oauth2/token", postParams.toString()));
	*/
        JSONObject crmUserParams = new JSONObject();
        JSONObject headers = new JSONObject();
//		headers.put("Authorization", "96360c9b765d213bf569b76d4eaaf16aff9076e111c88bcdcd433c8fb05a0f2b.MTg2MzU1");
        headers.put("Authorization", "43d905e5c33e0572e7ae5cb844e6ce656aad99b78729da49e0209c999e0218bd.MTg2MzU1");
		/*crmUserParams.put("q", "select * from contact where accountId=26644247 order by id asc limit 0,10");
		crmUserParams.put("employeeCode", "");

		JSONObject params = new JSONObject();
		params.put("record", crmUserParams);
		JSONObject contacts = HttpClientUtils.doPost(
				"https://api.xiaoshouyi.com/data/v1/query","q=select id,entityType,ownerId,contactName,accountId,depart,dbcSelect3,phone,mobile,email,state,gender,birthday from contact where accountId=26644247 order by id asc limit 0,10",
				headers);
		JSONObject object = JSONObject.parseArray(contacts.get("records").toString()).getJSONObject(0);*/
        //crmUserParams.put("dbcVarchar30","");
        //crmUserParams.put("id",26644247);
        //System.out.println("同步用户账号>>>>>>>>>>>>>>>>>" + HttpClientUtils.doPost("https://api.xiaoshouyi.com/data/v1/objects/account/update", crmUserParams, headers));
        //JSONObject orderParams=new JSONObject();
        //orderParams.put("id", "1986567");
        //orderParams.put("approvalStatus", 3);
        //orderParams.put("dbcVarchar18", "OD20180710161117356465");
        //orderParams.put("dbcReal2", 1);
        //orderParams.put("dbcSelect5", StringUtils.equals(params.getString("offerFood"), "1")?1:2);
        //orderParams.put("dbcSelect6", StringUtils.equals(params.getString("offerTraffic"),"1")?1:2);
        //orderParams.put("dbcReal3", 1);
        //orderParams.put("dbcVarchar13", params.getString("shootingAddress"));
        //orderParams.put("dbcVarchar14", params.getString("linkManName"));
        //orderParams.put("dbcVarchar15", params.getString("linkManPhone"));
        //orderParams.put("dbcVarchar16", params.getString("shootingName"));
        //orderParams.put("dbcVarchar12", params.getString("cityName"));
        //orderParams.put("dbcSelect11", params.getString("crmImporantOrderMark"));
        //orderParams.put("dbcSelect10", params.getString("crmIsAuthVphoto"));
        //orderParams.put("dbcSelect7", params.getString("crmGetAlbumType"));
        //orderParams.put("dbcDate2", DateUtils.getDateString(DateUtils.getFormatDate("2018-07-10 10:10:10", DateUtils.PATTERN_YMSHMS), "yyyy-MM-dd HH:mm"));
        //orderParams.put("dbcDate3", DateUtils.getDateString(DateUtils.getFormatDate("2018-07-10 18:10:10", DateUtils.PATTERN_YMSHMS), "yyyy-MM-dd HH:mm"));
//		System.out.println(DateUtils.getFormatDate("2018-08-08 18:30:10", DateUtils.PATTERN_YMSHMS).getTime());
//		JSONObject contacts = HttpClientUtils.doPost(
//				"https://api.xiaoshouyi.com/data/v1/query","q=select id,entityType,ownerId,contactName,accountId,depart,dbcSelect3,phone,mobile,email,state,gender,birthday from contact where accountId=18071720 order by id asc limit 0,10",
//				headers);
//		System.out.println(DateUtils.getFormatDate("2018-08-16 18:30:10", DateUtils.PATTERN_YMSHMS).getTime());
		/*String sql="q=select id,dbcReal5,approvalStatus,transactionDate,entityType,accountId,po,dbcSelect15,dbcVarchar18 from _order where approvalStatus in(0,3) and dbcVarchar18 is null and dbcReal5<300 and transactionDate>1534903200000 and entityType=1263617 and dbcSelect15=1   order by id limit 0,100";
		JSONObject contacts = HttpClientUtils.doPost("https://api.xiaoshouyi.com/data/v1/query",
				"q=select id,po,dbcSelect15,dbcVarchar18 from _order where  po in ('SO#20180904-0067','SO#20180904-0086')",headers);
		System.out.println("同步订单1>>>>>>>>>>>>>>>>>" + contacts);*/
        JSONObject orderParams = new JSONObject();
        orderParams.put("id", "2161533");
        orderParams.put("dbcVarchar18", "OD20180905113609692845");
        System.out.println(HttpClientUtils.doPut("http://127.0.0.1:8082/v1/digitals", orderParams, headers));


    }
}
