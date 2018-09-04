/**
 * FileName: BaseController
 * Author:   sunny
 * Date:     2018/8/24 15:45
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.controller.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunny.practice.dao.jpa.page.PageInfo;
import com.sunny.practice.utils.BaseUtils;
import com.sunny.practice.utils.ResBean;
import com.sunny.practice.utils.exception.ResultCodeEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseController {


    @JsonIgnore
    @Resource
    public HttpServletRequest req;

    public ResBean getResBean() {
        return ResBean.builder(ResultCodeEnum.成功.flag, ResultCodeEnum.成功.defaultMsg);
    }

    /**
     * 执行成功，直接返回对像
     *
     * @param obj
     * @return
     */
    public ResBean getResBean(Object obj) {
        return ResBean.builder(ResultCodeEnum.成功.flag, ResultCodeEnum.成功.defaultMsg, obj);
    }

    /**
     * 执行成功，直接返回分页对像
     *
     * @param pageInfo
     * @return
     */
    public ResBean getResBean(PageInfo pageInfo) {
        ResBean.PageInfo page=new ResBean().istansPageInfo();
        BaseUtils.copyBeanNullInvalid(pageInfo, page);
        return ResBean.builder(ResultCodeEnum.成功.flag, ResultCodeEnum.成功.defaultMsg,  page);
    }

    /**
     * 执行失败设置错误码和错误消息
     *
     * @param code
     * @param msg
     * @return
     */
    public ResBean getResBean(ResultCodeEnum code, String msg) {
        return ResBean.builder(code.flag, msg,  null);
    }

    /**
     * 手动设置属性
     *
     * @param code
     * @param msg
     * @param obj
     * @return
     */
    public ResBean getResBean(ResultCodeEnum code, String msg, Object obj) {
        return ResBean.builder(code.flag, msg, obj);
    }

    /**
     * 手动设置属性
     *
     * @param code
     * @param msg
     * @param pageInfo
     * @return
     */
    public ResBean getResBean(ResultCodeEnum code, String msg, PageInfo pageInfo) {
        return ResBean.builder(code.flag, msg, pageInfo);
    }

}