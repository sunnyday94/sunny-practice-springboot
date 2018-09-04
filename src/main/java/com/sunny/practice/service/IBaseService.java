/**
 * 
 * 类名 : IBaseService.java
 *
 * 包名 : com.sfcs.service
 *
 * 创建人 : 杨坤国
 * 
 * 创建时间: 2014-8-1 上午09:29:05
 *
 * 类说明 : 
 *
 *
 *
 */
package com.sunny.practice.service;



import com.sunny.practice.dao.jpa.IBasicDao;

import java.io.Serializable;

/**
 * @author user
 *
 */
public interface IBaseService<E extends Serializable> extends IBasicDao<E> {

	void sessionFlash();
}
