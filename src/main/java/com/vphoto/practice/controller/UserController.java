/**
 * FileName: UserController
 * Author:   sunny
 * Date:     2018/8/24 14:08
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.vphoto.practice.controller;

import com.vphoto.practice.controller.base.BaseController;
import com.vphoto.practice.dao.jpa.page.PageInfo;
import com.vphoto.practice.entity.vo.STBUserVo;
import com.vphoto.practice.service.ISTBUserService;
import com.vphoto.practice.utils.BaseUtils;
import com.vphoto.practice.utils.ResBean;
import com.vphoto.practice.utils.exception.ResultCodeEnum;
import com.vphoto.practice.utils.exception.VPhotoException;
import com.vphoto.practice.utils.page.ReqPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequestMapping(value="/user")
@Api(tags = {"用户api"})
public class UserController extends BaseController {

    @Autowired
    private ISTBUserService userService;

    public UserController(){
        log.info("执行UserController构造方法");
    }

    @PostConstruct
    public void init(){
        log.info("执行init方法");
    }

    /**
     * @Description: 新增用户
     * @Author: sunny
     * @Date: 2018/8/24 16:17
     */

    @PostMapping(value="/addUser",produces = {"application/json"})
    @ApiOperation(value="新增用户",notes = "新增用户,参数为用户对象",response = ResBean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(dataType="STBUserVo",name="vo",required=true,paramType="body")
    })
    public ResBean addUser(STBUserVo vo){
        if(BaseUtils.isNull(vo)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        userService.addUser(vo);
        return this.getResBean("新增用户成功!");
    }

    /**
     * @Description: 获取用户列表
     * @Author: sunny
     * @Date: 2018/8/24 16:21
     */
    @PostMapping(value="/getUserList",produces = {"application/json"})
    @ApiOperation(value="获取用户列表",notes = "获取用户列表,page中的obj必传",response = ResBean.class)
    @ApiImplicitParams(
            @ApiImplicitParam(name="page",dataType ="ReqPage<STBUserVo>",required = true, paramType = "body",value="{\"pageIndex\":1,\"pageSize\":10,\"obj\":{\"id\":}}")
    )
    public ResBean getUserList(ReqPage<STBUserVo> page){
        if(BaseUtils.isNull(page)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        if(BaseUtils.isNull(page.getOjb())) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        PageInfo pageInfo =  userService.getUserList(page);
        return this.getResBean(pageInfo);
    }

    /**
     * @Description: 更新用户
     * @Author: sunny
     * @Date: 2018/8/24 16:25
     */

    @PostMapping(value="/updateUser",produces = {"application/json"})
    @ApiOperation(value="更新用户信息",notes = "更新用户信息,vo中id必传",response = ResBean.class)
    @ApiImplicitParams(
            @ApiImplicitParam(name="vo",dataType = "STBUserVo",required = true,paramType = "body")
    )
    public ResBean updateUser(STBUserVo vo){
        if(BaseUtils.isNull(vo)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        userService.updateUser(vo);
        return this.getResBean("更新用户成功!");
    }


    /**
     * @Description: 删除用户
     * @Author: sunny
     * @Date: 2018/8/24 16:26
     */
    @Deprecated
    @PostMapping(value="/deleteUser",produces = {"application/json"})
    @ApiOperation(value="删除用户",notes = "删除用户,vo中id必传",response = ResBean.class)
    @ApiImplicitParams(
            @ApiImplicitParam(name="vo",dataType = "STBUserVo",required = true,paramType = "body")
    )
    public ResBean deleteUser(STBUserVo vo){
        if(BaseUtils.isNull(vo)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        userService.deleteUser(vo);
        return this.getResBean("删除用户成功!");
    }
}