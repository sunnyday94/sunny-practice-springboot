package com.vphoto.practice.dao.jpa;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PageUtil {

	private static final Log log = LogFactory.getLog(PageUtil.class);
	
	@SuppressWarnings({ "unchecked" })
	public static List<?> getListObject(Session em,int first, int max, String hql, Object ... s) {
		
		Query<?> query=null;
	   	List <Object>list=new ArrayList<Object>();
		log.info(hql);	
	   	try { 
				query= em.createQuery(hql);
				if(s!=null && s.length>0){
					for(int i=0;i<s.length;i++){
						query.setParameter(i, (Object)s[i]);
					}
					log.info(ArrayUtils.toString(s)+",first:"+first+",max:"+max);
				}
				if(first>=0 && max>0){
					query.setFirstResult(first);
					query.setMaxResults(max);
				}
		    	list=(List<Object>) query.list();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("get("+hql+") all failed", e);
				throw e;
			}
			
			return list;
	}

	/** 针对Hibernate4+的新写法  select a from A a where a=:a
	 * @param em
	 * @param first
	 * @param max
	 * @param sql
	 * @param pars
	 * @return
	 */
	public static List<?> getListObjectMap(Session em,int first, int max, String hql, Map<String, Object> pars) {
		
		Query<?> query=null;
	   	List<?>list=new ArrayList<Object>();
		log.info(hql);	
	   	try { 
				query= em.createQuery(hql);
				if(pars!=null && !pars.isEmpty()){
					for(Entry<String, Object>  ent:pars.entrySet()){
						Object obj = ent.getValue();  
						//这里考虑传入的参数是什么类型，不同类型使用的方法不同  
		                if(obj instanceof Collection<?>){  
		                    query.setParameterList(ent.getKey(), (Collection<?>)obj);  
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
		    	list=query.list();//getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("get("+hql+") all failed", e);
				throw e;
			}
			
			return list;
	}
	
	public static int getTotalObject(Session em,String hql, Object ... s) {
		int count=0;
		Query<?> query=null;
		try {
			log.info(hql);
		
			query=em.createQuery(hql);
			if(s!=null && s.length!=0){
				for(int i=0;i<s.length;i++){
					query.setParameter(i,s[i]);
				}
				log.info(ArrayUtils.toString(s));
			}
			Object obj=query.list();//有group by时 无数据返回 空list
			log.info("count: "+obj);
//			if(obj instanceof List<?>) {
				if(((List) obj).isEmpty()) 
					return 0;
				else {
					obj=((List) obj).get(0);
					
					if(obj instanceof Integer)
						count=Integer.valueOf((Integer)obj).intValue();
					else if(obj instanceof Long)
						count=Long.valueOf((Long)obj).intValue();
					return count;
				}
//			}else {
//				obj=query.getSingleResult();//uniqueResult();
//			}
			
		} catch (Exception re) {
			log.error("get("+hql+") all failed", re);
			throw re;
		}
//		return count;
		
	}
	/**sql参数顺序一定要正确
	 * @param em
	 * @param sql
	 * @param s
	 * @return
	 */
	public static int getSQLSingleResult(Session em,String sql, Object ... s) {
    	int count=0;
        NativeQuery<?> query = em.createNativeQuery(sql);
        log.info(sql + " pars:" + ArrayUtils.toString(s));
        if(s!=null && s.length>0){
            for (int i = 0; i < s.length; i++) {
                query.setParameter(i+1, (Object) s[i]);
            }
        }
        Object obj=query.getSingleResult();//uniqueResult();
		log.info("count: "+obj);
		if(obj instanceof BigInteger)
			count=Integer.valueOf(((BigInteger) obj).intValue()).intValue();
		else if(obj instanceof Integer)
			count=Integer.valueOf((Integer)obj).intValue();
		else if(obj instanceof Long)
			count=Long.valueOf((Long)obj).intValue();
		
		return count;
		
    }
	
	/**针对Hibernate4+的新写法  select a from A a where a=:a
	 * @param em
	 * @param sql
	 * @param pars
	 * @return
	 */
	public static int getTotalObjectMap(Session em,String hql, Map<String, Object> pars) {
		int count=0;
		Query<?> query=null;
		try {
			log.info(hql);
		
			query=em.createQuery(hql);
			if(pars!=null && !pars.isEmpty()){
				for(Entry<String, Object>  ent:pars.entrySet()){
					Object obj = ent.getValue();  
					//这里考虑传入的参数是什么类型，不同类型使用的方法不同  
	                if(obj instanceof Collection<?>){  
	                    query.setParameterList(ent.getKey(), (Collection<?>)obj);  
	                }else if(obj instanceof Object[]){  
	                    query.setParameterList(ent.getKey(), (Object[])obj);  
	                    log.info(ArrayUtils.toString(obj));
	                }else{  
	                    query.setParameter(ent.getKey(), obj);  
	                }  
				}
				log.info(ArrayUtils.toString(pars));
			}
			
			Object obj=query.uniqueResult();
			count=Long.valueOf((Long)obj).intValue();
			log.info("count: "+obj);
		} catch (RuntimeException re) {
			log.error("get("+hql+") all failed", re);
			throw re;
		}
		return count;
		
	}
	
	/**Jdbc操作时对返回结果序列到对象中
	 * @param rs 列名与对象属性名对应
	 * @param k 要转的对象
	 * @return
	 * @throws SQLException
	 */
	public static <T> List<T> getResultSetList(ResultSet rs, Class<T> k)  
            throws SQLException {  
        List<T> bl = new ArrayList<T>();  
        if (rs != null) {  
            while (rs.next()) {  
                T o = null;  
                try {  
                    o = k.newInstance();  
                    for (Method m : k.getDeclaredMethods()) {  
                        String name = m.getName();  
                        if (name.startsWith("set")) {  
                          log.info(rs.getObject(name.substring(3)).getClass().getName());  
                            m.invoke(o, rs.getObject(name.substring(3)));
                        }  
                    }  
                    bl.add(o);  
                
                } catch (IllegalArgumentException e) {  
                    e.printStackTrace();  
                } catch (InvocationTargetException e) {  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}  
            }  
            return bl;  
        }  
        return null;  
    }  
	
	
}
