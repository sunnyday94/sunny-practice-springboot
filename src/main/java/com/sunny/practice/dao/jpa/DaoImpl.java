/**
 * 类名 : DaoImpl.java
 * <p>
 * 包名 : com.sfcs.dao
 * <p>
 * 创建人 : sunny
 * <p>
 * 创建时间: 2018-7-31 下午05:35:41
 * <p>
 * 类说明 :
 */
package com.sunny.practice.dao.jpa;

import com.sunny.practice.utils.ResBean;
import com.sunny.practice.utils.json.JsonUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 公共DAO层，不能随意添加或修改其中方法
 * @param <E>
 */
@Repository("dao")
@Transactional
public class DaoImpl<E extends Serializable>
// 		extends SimpleJpaRepository<E, Long>
        implements IBasicDao<E> {


    private static final Log log = LogFactory.getLog(DaoImpl.class);

//	@Resource(name = "sessionFactory")
//	private SessionFactory sessionFactory1;

    @Resource()
    EntityManager entityManager;

//	public DaoImpl(Class<E> domainClass, EntityManager em) {
//		super(domainClass, em);
//		log.info(sessionFactory1);
//		log.info("entityManager:"+em);
//	}

    /**
     *
     */
    public DaoImpl() {
//		log.info(sessionFactory1);
        // log.info("entityManager:"+entityManager);
    }


    public void deleteEntity(E e) {
        log.info("updateEntity " + e.getClass().getName());
        getSession().delete(e);
        // entityManager.remove(e);
    }


    public void deleteEntity(Class<E> clazz, Long id) {
        getSession().delete(getSession().load(clazz, id));
        // entityManager.remove(getSession().load(clazz, id));
        log.info("deleteEntity " + clazz.getSimpleName() + " ID : " + id);
    }


    public E getEntity(Class<E> clazz, Long id) {
        log.info("findEntity " + clazz.getSimpleName() + " ID : " + id);
        return getSession().get(clazz, id);
    }


    public List<E> entityList(Class<E> clazz, String sqlParameter,
                              Object... values) {
        return getListObject(clazz, -1, -1, sqlParameter, values);
    }

    public <T> List<T> entityListSQL(Class<T> t, String sql, Object... s){
        return this.entityListSQL(t, 0, 100, sql, s);
    }

    public <T> List<T> entityListSQL(Class<T> t,int first,int max, String sql, Object... s) {
        log.info(sql + " pars:" + ArrayUtils.toString(s));
        NativeQuery<?> query = (NativeQuery<?>) getSession().createNativeQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//        	query.setResultTransformer(Transformers.aliasToBean(t));
        if(first>=0 && max>0){
            query.setFirstResult(first);
            query.setMaxResults(max);
        }
        if (s != null && s.length != 0) {
            for (int i = 0; i < s.length; i++) {
                Object obj = (Object) s[i];
                query.setParameter(i+1, obj);
//            	if(obj instanceof Collection<?>){
//                    query.setParameter(i+1, (Collection<?>)obj);
//                    log.info(ArrayUtils.toString(obj));
//                }else if(obj instanceof Object[]){
//                    query.setParameter(i+1, (Object[])obj);
//                    log.info(ArrayUtils.toString(obj));
//                }else{
//                    query.setParameter(i+1, obj);
//                }
            }
        }

        List<?> list= query.list();
        return JsonUtils.toListObject(list, t);
    }

    public <T> List<T> entityListSQL(Class<T> t,int first,int max, String sql, Map<String, Object> pars) {
        log.info(sql + " pars:" + ArrayUtils.toString(pars));
        NativeQuery<?> query = (NativeQuery<?>) getSession().createNativeQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//        	query.setResultTransformer(Transformers.aliasToBean(t));

        if(pars!=null && !pars.isEmpty()){
            for(Entry<String, Object>  ent:pars.entrySet()){
                Object obj = ent.getValue();
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if(obj instanceof Collection<?>){
                    query.setParameterList(ent.getKey(), (Collection<?>)obj);
                    log.info(ArrayUtils.toString(obj));
                }else if(obj instanceof Object[]){
                    query.setParameterList(ent.getKey(), (Object[])obj);
                    log.info(ArrayUtils.toString(obj));
                }else{
                    query.setParameter(ent.getKey(), obj);
                }
            }
            log.info(ArrayUtils.toString(pars)+",first:"+first+",max:"+max);
        }
        if(first>=0 && max>0){
            query.setFirstResult(first);
            query.setMaxResults(max);
        }

        return JsonUtils.toListObject(query.getResultList(), t);
    }

    public int execSQL(String sql, Object... s) {
        NativeQuery<?> query = getSession().createNativeQuery(sql);
        log.info(sql + " pars:" + ArrayUtils.toString(s));
        if (s != null && s.length != 0) {
            for (int i = 0; i < s.length; i++) {
                query.setParameter(i+1, (Object) s[i]);
            }
        }
        return query.executeUpdate();
    }



    public E loadEntity(Class<E> clazz, Long id) {
        return getSession().load(clazz, id);
    }


    public void saveEntity(E e) {
        getSession().save(e);
        // log.info(entityManager.isJoinedToTransaction());
        // log.info(entityManager.getEntityManagerFactory());
    }


    public E updateEntity(E e) {
        log.info("updateEntity " + e.getClass().getName());
        getSession().update(e);
        return e;
    }


    public E mergeEntity(E e) {
        log.info("updateEntity " + e.getClass().getName());
        getSession().merge(e);
        // entityManager.merge(e);
        return e;
    }

    /**
     * 分页，返回集合
     *
     * @param clazz
     * @param first
     * @param max
     * @param hqlParameter 如：where id=? and name=?
     * @param values       如：values.add(1);values.add("king");
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<E> getListObject(Class<E> clazz, int first, int max,
                                  String hqlParameter, Object... values) {
        String querys = "from " + clazz.getSimpleName() + " as m "
                + (StringUtils.isBlank(hqlParameter) ? "" : hqlParameter);
        if (hqlParameter != null
                && hqlParameter.toUpperCase().contains("FROM")) {
            querys = hqlParameter;
        }
        return (List<E>) PageUtil.getListObject(getSession(), first, max, querys, values);
    }
//
//
//    @SuppressWarnings({"unchecked", "unused"})
//    private List<E> getListObjectMap(Class<E> clazz, int first, int max,
//                                     String sqlParameter, Map<String, Object> maps) {
//        String querys = "from " + clazz.getSimpleName() + " as m "
//                + (StringUtils.isBlank(sqlParameter) ? "" : sqlParameter);
//        if (sqlParameter != null
//                && sqlParameter.toUpperCase().indexOf("FROM") != -1) {
//            querys = sqlParameter;
//        }
//        return ((List<E>) PageUtil.getListObjectMap(getSession(), first, max, querys, maps));
//    }


    /**
     * 分页，返回记录总数
     *
     * @param clazz
     * @param sqlParameter
     * @param values
     * @return
     */
    /*
	private int getTotalObject(Class<E> clazz, String sqlParameter,
			Object... values) {
		String sql = "select count(*) from " + clazz.getSimpleName() + " as m "
				+ (StringUtils.isBlank(sqlParameter) ? "" : sqlParameter);

		String nhql = sqlParameter;
		if (nhql.toUpperCase().indexOf("FROM") != -1) {
			sql = "select count(*) "
					+ sqlParameter.substring(nhql.toUpperCase().indexOf("FROM"));
		}
		return PageUtil.getTotalObject(getSession(), sql, values);
	}*/

	/*private int getTotalObjectMap(Class<E> clazz, String sqlParameter,
			Map<String, Object> values) {
		String sql = "select count(*) from " + clazz.getSimpleName() + " as m "
				+ (StringUtils.isBlank(sqlParameter) ? "" : sqlParameter);

		String nhql = sqlParameter;
		if (nhql.toUpperCase().indexOf("FROM") != -1) {
			sql = "select count(*) "
					+ sqlParameter.substring(nhql.toUpperCase().indexOf("FROM"));
		}
		return PageUtil.getTotalObjectMap(getSession(), sql, values);
	}*/

    /**
     * @param hql
     * @param values
     * @return
     */
    @Override
    public int getTotalObj(String hql, Object... values) {
        return PageUtil.getTotalObject(getSession(), hql, values);
    }

    /**
     * @param hql
     * @param values
     * @return
     */
    @Override
    public int getTotalObjMap(String hql, Map<String, Object> values) {
        return PageUtil.getTotalObjectMap(getSession(), hql, values);
    }



    public <T> ResBean.PageInfo<T> getPages(Class<T> clazzVO, int page, int pageSize,
                                            String hql, Object... values) {
        if (page > 0)
            page--;
        int first = page * pageSize;

        ResBean.PageInfo<T> pageBean = new ResBean.PageInfo<>();
        // 得到sql
//		HQLQueryPlan plan = queryParameters.getQueryPlan();
//		if ( plan == null ) {
//			plan = getQueryPlan( query, false );
//		}
        String sql = DaoUtil.getHql2Sql(hql, this.getSession());
        sql=DaoUtil.changSQL(sql,values);
        sql=DaoUtil.formatSQL(sql);
        System.out.println(sql);
        values=DaoUtil.chang2List(values).toArray();
        pageBean.setList(this.entityListSQL(clazzVO, first, pageSize, sql, values));

        if (sql.toUpperCase().contains("FROM")) {
            sql = "select count(*) "
                    + sql.substring(sql.toUpperCase().indexOf("FROM"));
            if (page > -1 && pageSize > 0) { // 不是分页不再查询总数
                //如果有Group by再套层查询
                if(sql.toUpperCase().contains("GROUP")) {
                    String nsql=getGroupBySql(sql);
                    pageBean.setTotal(PageUtil.getSQLSingleResult(getSession(), nsql, values));
                }else
                    pageBean.setTotal(PageUtil.getSQLSingleResult(getSession(), sql, values));
            }
        }else {
            pageBean.setTotal(0);
        }
        return pageBean;
    }

    public <T> ResBean.PageInfo<T> getPages(Class<T> clazzVO,int page, int pageSize, String hql, Map<String, Object> pars) {
        if (page > 0)
            page--;
        int first = page * pageSize;

        ResBean.PageInfo<T> pageBean = new ResBean.PageInfo<>();
        // 得到sql
        String sql = DaoUtil.getHql2Sql(hql, this.getSession());
        sql=DaoUtil.changSQL(sql,pars);
        sql=DaoUtil.formatSQL(sql);
        System.out.println(sql);
        pageBean.setList(this.entityListSQL(clazzVO, first, pageSize, sql, pars));
//        String sqlParameter=hql;

        if (hql.toUpperCase().indexOf("FROM") != -1) {
            hql = "select count(*) "
                    + hql.substring(hql.toUpperCase().indexOf("FROM"));

            if (page > -1 && pageSize > 0) {// 不是分页不再查询总数
                //如果有Group by再套层查询
                if(hql.toUpperCase().indexOf("GROUP") != -1) {
                    hql=getGroupByHql2Sql(hql);
                    pageBean.setTotal(PageUtil.getSQLSingleResult(getSession(), hql, pars.values().toArray()));
                }else {
                    pageBean.setTotal(PageUtil.getTotalObjectMap(getSession(), hql, pars));
                }
            }
        } else
            pageBean.setTotal(0);

        return pageBean;
    }



    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    public ResBean.PageInfo<E> getPages(int page, int pagesize, String hql,
                                           Object... paras) {
        if (page > 0)
            page--;
        int first = page * pagesize;

        ResBean.PageInfo pageBean = new ResBean.PageInfo();
        pageBean.setList(PageUtil.getListObject(getSession(), first, pagesize,
                hql, paras));
        String nhql = hql;
        if (nhql.toUpperCase().indexOf("FROM") != -1) {
            nhql = "select count(*) "
                    + hql.substring(nhql.toUpperCase().indexOf("FROM"));
            if (page > -1 && pagesize > 0)// 不是分页不再查询总数
                //如果有Group by再套层查询
                if(nhql.toUpperCase().indexOf("GROUP") != -1) {

                    nhql=getGroupByHql2Sql(hql);
                    pageBean.setTotal(PageUtil.getSQLSingleResult(getSession(), nhql, paras));
                }else
                    pageBean.setTotal(PageUtil.getTotalObject(getSession(), nhql, paras));
        } else
            pageBean.setTotal(0);

        return pageBean;
    }


    public ResBean.PageInfo<E> getPages(int page, int pagesize, String hql, Map<String, Object> pars) {
        if (page > 0)
            page--;
        int first = page * pagesize;

        ResBean.PageInfo pageBean = new ResBean.PageInfo();
        pageBean.setList(PageUtil.getListObjectMap(this.getSession(), first, pagesize,
                hql, pars));
//        String sqlParameter=hql;
        if (hql.toUpperCase().indexOf("FROM") != -1) {
            hql = "select count(*) "
                    + hql.substring(hql.toUpperCase().indexOf("FROM"));

            if (page > -1 && pagesize > 0) {// 不是分页不再查询总数
                //如果有Group by再套层查询
                if(hql.toUpperCase().indexOf("GROUP") != -1) {
                    hql=getGroupByHql2Sql(hql);
                    pageBean.setTotal(PageUtil.getSQLSingleResult(getSession(), hql, pars.values().toArray()));
                }else {
                    pageBean.setTotal(PageUtil.getTotalObjectMap(getSession(), hql, pars));
                }
            }
        } else
            pageBean.setTotal(0);

        return pageBean;
    }

    @SuppressWarnings("unchecked")
    public List<E> listPage(int page, int pagesize, String hql,
                            Object... paras) {
        if (page > 0)
            page--;
        int first = page * pagesize;


        return (List<E>) PageUtil.getListObject(getSession(), first, pagesize,
                hql, paras);
    }

    @SuppressWarnings("unchecked")
    public List<E> entityList(String hql, Object... values) {

        return (List<E>) PageUtil.getListObject(getSession(), -1, -1, hql, values);
    }

    @SuppressWarnings("unchecked")
    public List<E> entityList(String hql, Map<String, Object> values) {

        return (List<E>) PageUtil.getListObjectMap(getSession(), -1, -1, hql, values);
    }


//    public Connection getConnection() throws PersistenceException {
//        return null;
//        // return new HibernateJpaDialect().getJdbcConnection(entityManager,
//        // true).getConnection();
//    }


    public E getEntity(Class<E> clazz, String hql, Object... s) {
        E e;
        Query<?> query = null;
        try {
            log.info(hql);
            log.info(ArrayUtils.toString(s));
            query = (Query<?>) this.getSession().createQuery(hql);
            if (s != null && s.length != 0) {
                for (int i = 0; i < s.length; i++) {
                    query.setParameter(i, (Object) s[i]);
                }
            }

            e = (E) query.uniqueResult();
        } catch (RuntimeException re) {
            log.error("get(" + hql + ") all failed", re);
            throw re;
        }
        return e;
    }

    @SuppressWarnings("unchecked")
    public <T> T getUniqueBy(Class<T> entityClass, String propertyName,
                             Object propertyValue) throws Exception {
        try {
            Assert.hasText(propertyName, "not null");
            log.info(propertyName + " : " + propertyValue);
            return (T) this.getSession()
                    .createCriteria(entityClass)
                    .add(Restrictions.eq(propertyName, propertyValue))
                    .uniqueResult();
        } catch (RuntimeException e) {
            // 写异常日志，并抛出自定义DAORuntimeException
            e.printStackTrace();
            log.error(e);
            throw new Exception("执行含动态单属性名和属性值，并实现结果唯一的QBC查询时发生DAO运行时异常", e);
        }
    }



    public int execHQL(String hql, Object... s) {
        Query<?> query = null;
        int exec = 0;
        log.info(hql);
        try {
            query = this.getSession().createQuery(hql);
            if (s != null && s.length > 0) {
                for (int i = 0; i < s.length; i++) {
                    query.setParameter(i, (Object) s[i]);
                }
                log.info(ArrayUtils.toString(s));
            }

            exec = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return exec;
    }



    /**
     * @param hql
     * @return sql
     */
    private  String getGroupByHql2Sql(String hql) {
        int index=hql.toUpperCase().indexOf("ORDER BY");
        if(index!=-1) {
            hql=hql.substring(0, index);
        }
        // 得到sql
        String sql = DaoUtil.getHql2Sql(hql, getSession());
        return "SELECT COUNT(*) FROM ("+sql+") as x";
    }
    /**
     * @param sql
     * @return
     */
    private  String getGroupBySql(String sql) {
        int index=sql.toUpperCase().indexOf("ORDER BY");
        if(index!=-1) {
            sql=sql.substring(0, index);
        }

        return "SELECT COUNT(*) FROM ("+sql+") as x";
    }

}
