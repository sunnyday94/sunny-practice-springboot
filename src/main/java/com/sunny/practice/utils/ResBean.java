/**
 * FileName: ResBean
 * Author:   sunny
 * Date:     2018/8/24 15:38
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResBean {

    /**
     * 默认设置为成功状态，code=200
     * Response code;
     */
//    private ResultCodeEnum code;
    private Integer code;

    /**
     * 错误消息Set到此属性
     * Response message;
     */
    private String msg;

    /**
     * 返回为单个对象时，set到此属性
     * Response Object;
     */
    private Object data = null;

    /**
     * 返回为分页对象时，set到此属性
     * <br>List集合对象set到PageInfo中的list属性中
     */
    private PageInfo pageInfo = null;

    public ResBean() {

    }

    private ResBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResBean(Integer code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResBean(Integer code, String msg, PageInfo pageInfo) {
        super();
        this.code = code;
        this.msg = msg;
        this.pageInfo = pageInfo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    /**
//     * Json格式化回指定对象
//     *
//     * @param T
//     * @return
//     */
//    public <T> Object getObj(Class<T> T) {
//        if (data instanceof String) {
//            String json = data.toString();
//            return JsonUtils.toObject(json, T);
//        }
//        return null;
//    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        this.data=pageInfo;
    }

    @Override
    public String toString() {
        return "ResBean [code=" + code + ", msg=" + msg + ", data=" + data
                + ", pageInfo=" + pageInfo + "]";
    }


    public void setData(Object data) {
        this.data = data;
    }


    public Object getData() {
        return data;
    }

    public static final ResBean builder(int err, String msg) {
        return new ResBean(err, msg);
    }

    public static final ResBean builder(int err, String msg, Object data) {
        return new ResBean(err, msg, data);
    }

    public static final ResBean builder(int err, String msg,  PageInfo p) {
        return new ResBean(err, msg, p);
    }

    public PageInfo istansPageInfo() {
        return new PageInfo();
    }

    /**
     * 静态内部类
     * @param <T>
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageInfo<T> {
        /**
         * 分页list
         */
        private List<T> list = Collections.emptyList();

        /**
         * 总条目数
         */
        private int total;
    }
}