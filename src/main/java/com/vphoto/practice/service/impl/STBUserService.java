/**
 * FileName: STBUserService
 * Author:   sunny
 * Date:     2018/8/24 15:03
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.vphoto.practice.service.impl;

import com.vphoto.practice.dao.jpa.page.PageInfo;
import com.vphoto.practice.entity.po.STBUser;
import com.vphoto.practice.entity.vo.STBUserVo;
import com.vphoto.practice.service.ISTBUserService;
import com.vphoto.practice.utils.BaseUtils;
import com.vphoto.practice.utils.CheckUtils;
import com.vphoto.practice.utils.DateUtils;
import com.vphoto.practice.utils.page.ReqPage;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
@Service("STBUserService")
@Transactional
public class STBUserService extends BaseService<STBUser> implements ISTBUserService {
    @Override
    public Integer addUser(STBUserVo vo) {
        CheckUtils.checkNull(vo, "userName,userPassword,gender");
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
        vo.setUpdateTime(DateUtils.getNow());
        STBUser u  = this.getEntity(STBUser.class, vo.getId());
        BaseUtils.copyBeanNullInvalid(vo,u);
        this.updateEntity(u);
        return 1;
    }

    @Override
    public Integer deleteUser(STBUserVo vo) {
        return null;
    }
}