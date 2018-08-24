/**
 * FileName: ISTBUserService
 * Author:   sunny
 * Date:     2018/8/24 15:03
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.vphoto.practice.service;

import com.vphoto.practice.dao.jpa.page.PageInfo;
import com.vphoto.practice.entity.po.STBUser;
import com.vphoto.practice.entity.vo.STBUserVo;
import com.vphoto.practice.utils.page.ReqPage;

/**
 * @description
 * @author sunny
 * @create 2018/8/24
 * @since 1.0.0
 */
public interface ISTBUserService extends IBaseService<STBUser> {

    Integer addUser(STBUserVo vo);

    PageInfo getUserList(ReqPage<STBUserVo> page);

    Integer updateUser(STBUserVo vo);

    Integer deleteUser(STBUserVo vo);
}