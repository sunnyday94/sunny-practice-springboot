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
package com.sunny.practice.service.impl;

import com.sunny.practice.dao.jpa.IBasicDao;
import com.sunny.practice.service.IBaseService;
import com.sunny.practice.utils.ResBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * @author user
 *
 */
@Service
public class BaseService<E extends Serializable> // extends DaoImpl<E>
	implements IBaseService<E> {



	public static Log log = LogFactory.getLog(BaseService.class);
	@Resource(name="dao")
	protected IBasicDao<E> dao;


	@Override
	public void sessionFlash() {
		dao.getSession().flush();
	}

	/**
	 * 获取连接
	 * @return
	 */
	public Connection getConnection() {
		try {
			DataSource dataSource=SessionFactoryUtils.getDataSource(dao.getSession().getSessionFactory());
			return (dataSource==null)?null:dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteEntity(E e) {
		// TODO Auto-generated method stub
		dao.deleteEntity(e);
	}

	@Override
	public void deleteEntity(Class<E> clazz, Long id) {
		// TODO Auto-generated method stub
		dao.deleteEntity(clazz, id);
	}

	@Override
	public E loadEntity(Class<E> clazz, Long id) {
		// TODO Auto-generated method stub
		return dao.loadEntity(clazz, id);
	}

	@Override
	public E getEntity(Class<E> clazz, Long id) {
		// TODO Auto-generated method stub
		return dao.getEntity(clazz, id);
	}

	@Override
	public List<E> entityList(Class<E> clazz, String sqlParameter, Object... values) {
		// TODO Auto-generated method stub
		return dao.entityList(clazz, sqlParameter, values);
	}

	@Override
	public <T> List<T> entityListSQL(Class<T> t, String sql, Object... s) {
		// TODO Auto-generated method stub
		return dao.entityListSQL(t, sql, s);
	}

	@Override
	public <T> List<T> entityListSQL(Class<T> t, int first, int max, String sql, Object... s) {
		// TODO Auto-generated method stub
		return dao.entityListSQL(t, first, max, sql, s);
	}

//	@Override
//	public <T> List<T> entityListSQL(Class<T> t, int first, int max, String sql, Map<String, Object> pars) {
//		// TODO Auto-generated method stub
//		return dao.entityListSQL(t, first, max, sql, pars);
//	}

	@Override
	public int execSQL(String sql, Object... s) {
		// TODO Auto-generated method stub
		return dao.execSQL(sql, s);
	}

	@Override
	public void saveEntity(E e) {
		// TODO Auto-generated method stub
		dao.saveEntity(e);
	}

	@Override
	public E updateEntity(E e) {
		// TODO Auto-generated method stub
		return dao.updateEntity(e);
	}

	@Override
	public E mergeEntity(E e) {
		// TODO Auto-generated method stub
		return dao.mergeEntity(e);
	}

	@Override
	public int getTotalObj(String hql, Object... values) {
		// TODO Auto-generated method stub
		return dao.getTotalObj(hql, values);
	}

	@Override
	public int getTotalObjMap(String hql, Map<String, Object> values) {
		// TODO Auto-generated method stub
		return dao.getTotalObjMap(hql, values);
	}

	@Override
	public <T> ResBean.PageInfo<T> getPages(Class<T> clazzVO, int page, int pageSize, String hql, Object... values) {
		// TODO Auto-generated method stub
		return dao.getPages(clazzVO, page, pageSize, hql, values);
	}

	@Override
	public <T> ResBean.PageInfo<T> getPages(Class<T> clazzVO, int page, int pageSize, String hql, Map<String, Object> pars) {
		// TODO Auto-generated method stub
		return dao.getPages(clazzVO, page, pageSize, hql, pars);
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return dao.getSession();
	}

	@Override
	public ResBean.PageInfo<E> getPages(int page, int pagesize, String hql, Object... paras) {
		// TODO Auto-generated method stub
		return dao.getPages(page, pagesize, hql, paras);
	}

	@Override
	public ResBean.PageInfo<E> getPages(int page, int pagesize, String hql, Map<String, Object> pars) {
		// TODO Auto-generated method stub
		return dao.getPages(page, pagesize, hql, pars);
	}

	@Override
	public List<E> listPage(int page, int pagesize, String hql, Object... paras) {
		// TODO Auto-generated method stub
		return dao.listPage(page, pagesize, hql, paras);
	}

	@Override
	public List<E> entityList(String hql, Object... values) {
		// TODO Auto-generated method stub
		return dao.entityList(hql, values);
	}

	@Override
	public List<E> entityList(String hql, Map<String, Object> values) {
		// TODO Auto-generated method stub
		return dao.entityList(hql, values);
	}

	@Override
	public E getEntity(Class<E> clazz, String hql, Object... s) {
		// TODO Auto-generated method stub
		return dao.getEntity(clazz, hql, s);
	}

	@Override
	public <T> T getUniqueBy(Class<T> entityClass, String propertyName, Object propertyValue) throws Exception {
		// TODO Auto-generated method stub
		return dao.getUniqueBy(entityClass, propertyName, propertyValue);
	}

	@Override
	public int execHQL(String hql, Object... s) {
		// TODO Auto-generated method stub
		return dao.execHQL(hql, s);
	}

}
