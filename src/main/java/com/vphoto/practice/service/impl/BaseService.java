/**
 * 
 * 类名 : ServiceImpl.java
 *
 * 包名 : com.sfcs.service
 *
 * 创建人 : 杨坤国
 * 
 * 创建时间: 2014-8-1 上午09:30:51
 *
 * 类说明 : 
 *
 *
 *
 */
package com.vphoto.practice.service.impl;

import com.vphoto.practice.dao.jpa.DaoImpl;
import com.vphoto.practice.dao.jpa.IBasicDao;
import com.vphoto.practice.service.IBaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;


/**
 * @author user
 *
 */
@Service
public class BaseService<E extends Serializable> extends DaoImpl<E>
	implements IBaseService<E> {



	public static Log log = LogFactory.getLog(BaseService.class);
	@Resource(name="dao")
	protected IBasicDao<E> dao;
	
	

	@Override
	public void sessionFlash() {
		dao.getSession().flush();
	}



}
