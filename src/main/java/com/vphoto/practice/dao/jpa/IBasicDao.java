package com.vphoto.practice.dao.jpa;

import org.hibernate.Session;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.vphoto.practice.dao.jpa.page.PageInfo;

/**公共DAO接口
 * @author sunny
 *
 * @param <E>
 */
public interface IBasicDao<E extends Serializable> {

	/**
	 * 保存对象
	 * @param e
	 */
    void saveEntity(E e);

	/**修改对象
	 * @param e
	 * @return
	 */
    E updateEntity(E e);
	
	/**merge对象
	 * @param e
	 * @return
	 */
    E mergeEntity(E e);

	/**删除一个已经Load过的对象
	 * @param e
	 */
    void deleteEntity(E e);

	/**删除一个对象，须要对象ID
	 * @param clazz
	 * @param id
	 */
    void deleteEntity(Class<E> clazz, Long id);
//	public abstract void deleteEntity(ID id);

	/**查询一个对象，须要对象ID
	 * @param clazz
	 * @param id
	 * @return
	 */
    E getEntity(Class<E> clazz, Long id);

	/**Load一个对象，须要对象ID
	 * @param clazz
	 * @param id
	 * @return
	 */
    E loadEntity(Class<E> clazz, Long id);
	
	
	/** 分页，返回VO集合,不支持sql中带in(?,?,?)
	 * @param <T>
	 * @param clazzVO 要转换的VO类
	 * @param first
	 * @param max
	 * @param hql
	 * @param values
	 * @return 返回VO List
	 */
	public <T> PageInfo getPages(Class<T> clazzVO, int first, int max, String hql, Object... values);
	/**分页，返回VO集合 ,不支持sql中带in(?,?,?)
	 * @param clazzVO
	 * @param first
	 * @param max
	 * @param hql
	 * @param pars
	 * @return
	 */
	public <T> PageInfo getPages(Class<T> clazzVO, int first, int max, String hql, Map<String, Object> pars);
	
	/**多表查询分页，返回集合
	 * @param first
	 * @param max
	 * @param hql "select d,b.id as _id,b.name as _name from DemoBean d,DemoBean b where d.id=b.id"
	 * @param paras 参数数组
	 * @return
	 */
	PageInfo getPages(int first, int max, String hql, Object... paras);
	/**针对Hibernate4+的新写法  select a from A a where a=:a
	 * <BR>Map中的Value可以是数组
	 * @param first
	 * @param max
	 * @param hql
	 * @param pars
	 * @return
	 */
	PageInfo getPages(int first, int max, String hql, Map<String, Object> pars);
//	public abstract PageInfo getPages(Class<E> clazz,int first, int max, String hql, Map<String, Object> pars);
	/**分页直接返回List
	 * @param page
	 * @param pagesize
	 * @param hql
	 * @param paras
	 * @return
	 */
    List<E> listPage(int page, int pagesize, String hql, Object... paras);

	/** 返回集合  SELECT new com.centerm.TestVO(count(*),name) FROM A;可以map到VO类
	 * @param clazz 默认对象别名为: m     m.id=? or m.name=?
	 * @param hqlParameter 如：where id=? and name=?
	 * @param values 如：1,"king"
	 * @return List
	 */
    List<E> entityList(Class<E> clazz, String hqlParameter, Object... values);
	
	
	/** 返回集合
	 * @param hql,values
	 * @param values 如：1,"king"
	 * @return List
	 */
    List<E> entityList(String hql, Object... values);
	/**针对Hibernate4+的新写法  select a from A a where a=:a
	 * <BR>Map中的Value可以是数组
	 * @param hql
	 * @param values
	 * @return
	 */
    List<E> entityList(String hql, Map<String, Object> values);
	
	/** 自定义SQL语句查询
	 * 返回集合，单次查询限100条返回
	 * @param t 返回的对象Class<T>
	 * @param sql SQL语句中的别名一定要和对象中的属性名大小写对应。
	 * @param s
	 * @return
	 */
    <T> List<T> entityListSQL(Class<T> t, String sql, Object... s);
    <T> List<T> entityListSQL(Class<T> t, int first, int max, String sql, Object... s);
//    public <T> List<T> entityListSQL(Class<T> t,int first,int max, String sql, Map<String, Object> pars);
    /**执行自定义SQL操作update,delete
	 * @param sql
	 * @return
	 */
    int execSQL(String sql, Object... s);
	
	/**执行自定义HQL操作update,delete
	 * @param hql
	 * @param s
	 * @return
	 */
    int execHQL(String hql, Object... s);
	
	/**返回记录总数
	 * @param hql
	 * @param values
	 * @return
	 */
    int getTotalObj(String hql, Object... values);
	
	/**返回记录总数 H4+用法
	 * @param hql
	 * @param values
	 * @return
	 */
    int getTotalObjMap(String hql, Map<String, Object> values);
	
	/**单查询条件返回唯一对象
	 * @param entityClass
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 * @throws Exception 
	 */
    <T> T getUniqueBy(Class<T> entityClass, String propertyName, Object propertyValue) throws Exception;

	/**多查询条件返回唯一对象
	 * @param clazz
	 * @param hql
	 * @param values
	 * @return
	 */
    E getEntity(Class<E> clazz, String hql, Object... values);
	
	/**获取Session
	 * @return
	 */
    Session getSession();

}