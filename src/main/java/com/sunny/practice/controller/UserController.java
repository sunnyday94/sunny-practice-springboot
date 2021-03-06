/**
 * FileName: UserController
 * Author:   sunny
 * Date:     2018/8/24 14:08
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.controller;

import com.sunny.practice.controller.base.BaseController;
import com.sunny.practice.entity.vo.STBUserVo;
import com.sunny.practice.service.ISTBUserService;
import com.sunny.practice.utils.BaseUtils;
import com.sunny.practice.utils.ResBean;
import com.sunny.practice.utils.exception.ResultCodeEnum;
import com.sunny.practice.utils.exception.VPhotoException;
import com.sunny.practice.utils.page.ReqPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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

    @Resource(name="STBUserService")
    private ISTBUserService userService;


    /**
     * @Description: 新增用户
     * @Author: sunny
     * @Date: 2018/8/24 16:17
     */

    @PostMapping(value="/users",produces = {"application/json"})
    @ApiOperation(value="新增用户",notes = "新增用户,参数为用户对象",response = ResBean.class)
    public ResBean addUser(@RequestBody  STBUserVo vo){
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
    public ResBean getUserList(@RequestBody ReqPage<STBUserVo> page){
        if(BaseUtils.isNull(page)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        if(BaseUtils.isNull(page.getObj())) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        ResBean.PageInfo pageInfo = userService.getUserList(page);
        return this.getResBean(pageInfo);
    }

    /**
     * @Description: 更新用户
     * @Author: sunny
     * @Date: 2018/8/24 16:25
     */

    @PutMapping(value="/users",produces = {"application/json"})
    @ApiOperation(value="更新用户信息",notes = "更新用户信息,vo中id必传",response = ResBean.class)
    public ResBean updateUser(@RequestBody  STBUserVo vo){
        if(BaseUtils.isNull(vo)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        userService.updateUser(vo);
        return this.getResBean("更新用户成功!");
    }


    /**
     * @Description: 删除用户(逻辑删除)
     * @Author: sunny
     * @Date: 2018/8/24 16:26
     */
    @PostMapping(value="/deleteUser",produces = {"application/json"})
    @ApiOperation(value="删除用户",notes = "删除用户,vo中id必传",response = ResBean.class)
    public ResBean deleteUser(@RequestBody  STBUserVo vo){
        if(BaseUtils.isNull(vo)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        userService.deleteUser(vo);
        return this.getResBean("删除用户成功!");
    }


    /**
      * @Description: 删除用户(物理删除)
      * @Author: sunny
      * @Date: 0:01 2018/11/8
      */
    @DeleteMapping(value="/deleteUserById/{id}",produces = {"application/json"})
    @ApiOperation(value="根据用户id删除用户",notes = "根据用户id删除用户",response = ResBean.class)
    public ResBean deleteUserById(@PathVariable(value = "id")  Long id){
        if(BaseUtils.isNull(id)) throw new VPhotoException(ResultCodeEnum.参数异常, "请求参数不能为空!");
        userService.deleteUserById(id);
        return this.getResBean("删除用户成功!");
    }
}