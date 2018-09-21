/**
 * FileName: STBUserService
 * Author:   sunny
 * Date:     2018/8/24 15:03
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.service.impl;

import com.sunny.practice.dao.mybatis.mapper.STBUserMapper;
import com.sunny.practice.service.ISTBUserService;
import com.sunny.practice.dao.jpa.page.PageInfo;
import com.sunny.practice.entity.po.STBUser;
import com.sunny.practice.entity.vo.STBUserVo;
import com.sunny.practice.utils.BaseUtils;
import com.sunny.practice.utils.CheckUtils;
import com.sunny.practice.utils.DateUtils;
import com.sunny.practice.utils.exception.ResultCodeEnum;
import com.sunny.practice.utils.exception.VPhotoException;
import com.sunny.practice.utils.page.ReqPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Service("STBUserService")
@Transactional(rollbackFor = Exception.class)
public class STBUserService extends BaseService<STBUser> implements ISTBUserService {

    @Resource
    private STBUserMapper userMapper;


    @Override
    public Integer addUser(STBUserVo vo) {
        CheckUtils.checkNull(vo, "userName,userPassword,gender");
        //校验
        if(isExisted(vo))
            throw new VPhotoException(ResultCodeEnum.系统异常,vo.getUserName().concat("已存在!"));
        vo.setCreateTime(DateUtils.getNow());
        vo.setUpdateTime(DateUtils.getNow());
        vo.setDelFlag("0");
        STBUser u = new STBUser();
        BaseUtils.copyBeanNullInvalid(vo,u);
        this.saveEntity(u);
        return 1;
    }

    @Override
    public PageInfo getUserList(ReqPage<STBUserVo> page) {
        CheckUtils.checkNull(page,"obj");
        STBUserVo vo = page.getObj();
        StringBuilder hql = new StringBuilder(" from STBUser u where u.delFlag =?");
        List<Object> params = new ArrayList<>();
        if(vo.getDelFlag()==null)
            vo.setDelFlag("0");
        params.add(vo.getDelFlag());
        if(vo.getId()!=null){
            hql.append(" and u.id = ?");
            params.add(vo.getId());
        }
        if(vo.getUserName()!=null && !vo.getUserName().equals("")){
            hql.append(" and u.userName like ?");
            params.add("%"+vo.getUserName()+"%");
        }
        if(vo.getGender()!=null && !vo.getGender().equals("")){
            hql.append(" and u.gender = ?");
            params.add(vo.getGender());
        }
        if(vo.getTelPhone()!=null && !vo.getTelPhone().equals("")){
            hql.append(" and u.telPhone = ?");
            params.add(vo.getTelPhone());
        }
        PageInfo pageInfo = this.getPages(page.getPageIndex(), page.getPageSize(),
                hql.toString(),params.toArray());
        return pageInfo;
    }

    @Override
    public Integer updateUser(STBUserVo vo) {
        CheckUtils.checkNull(vo, "id");
        //校验
        if(isExisted(vo))
            throw new VPhotoException(ResultCodeEnum.系统异常,vo.getUserName().concat("已存在!"));
        vo.setUpdateTime(DateUtils.getNow());
        STBUser u  = this.getEntity(STBUser.class, vo.getId());
        BaseUtils.copyBeanNullInvalid(vo,u);
        this.updateEntity(u);
        return 1;
    }

    @Override
    public Integer deleteUser(STBUserVo vo) {
        CheckUtils.checkNull("vo", "id");
        vo.setDelFlag("1");
        vo.setDelTime(DateUtils.getNow());
        STBUser u = this.getEntity(STBUser.class, vo.getId());
        this.deleteEntity(u);
        return 1;
    }


    /**
      * @Description: 判断User是否存在
      * @Author: sunny
      * @Date: 17:57 2018/8/26
      */
    private boolean isExisted(STBUserVo vo){
        List<Object> params = new ArrayList<>();
        StringBuilder hql  = new StringBuilder("select count(u.id) from STBUser u where u.delFlag='0'");
        hql.append(" and u.userName=?");
        params.add(vo.getUserName());
        if(vo.getId()!=null){
            hql.append(" and u.id!=?");
            params.add(vo.getId());
        }
       return getTotalObj(hql.toString(), params.toArray())>0 ? true : false;
    }
}