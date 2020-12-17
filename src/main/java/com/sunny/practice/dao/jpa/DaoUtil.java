/**
 * FileName: DaoUtil
 * Author:   sunny
 * Date:     2019/2/18 15:40
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.sunny.practice.dao.jpa;

import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.hibernate.internal.SessionFactoryImpl;

import java.util.*;

/**
 * @description
 * @author sunny
 * @create 2019/2/18
 * @since 1.0.0
 */
@NoArgsConstructor
public class DaoUtil {

    /**格式化SQL
     * @param sql
     * @param pars
     * @return
     */
    public static String changSQL(String sql, Map<String,Object> pars) {
        StringBuilder sqls=new StringBuilder();
        Map<String, Object> map = new HashMap<>(pars);
        int num=0,sumIndex=0;
//		System.out.println(sql);
        String temp=sql;
        Map<String, Object> mapSql;
        while(!(mapSql=getStr(temp)).isEmpty()) {
            System.out.println(mapSql.get("index")+" == "+mapSql.get("sql"));
            sqls.append(mapSql.get("sql"));
            int index=Integer.valueOf(mapSql.get("index").toString())+1;
            temp=temp.substring(index);
            sumIndex+=index;
            sqls=setAsName(sqls, num, map);

            num++;
        }
//		System.out.println(sumIndex);
        sql=sql.substring(sumIndex);
        sqls.append(sql);
        System.out.println("chang sql: "+sqls.toString());
        return sqls.toString();
    }
    /**
     * @param sql
     * @return
     */
    private static Map<String, Object> getStr(String sql) {
        Map<String, Object> map=new HashMap<>();
        int index=sql.indexOf("?");
        if(index<0)return map;

        map.put("index", index);
        map.put("sql", sql.substring(0, index));
        return map;
    }
    /**
     * @param sqls
     * @param num
     * @param map
     * @return
     */
    private static StringBuilder setAsName(StringBuilder sqls,int num,Map<String, Object> map) {
        int length=0;
        for(Map.Entry<String, Object> ent:map.entrySet()){
            Object obj = ent.getValue();
            if(length==num) {
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同
                if(obj instanceof Collection<?> ){
                    Collection<?> temps= (Collection<?>) obj;
                    length=temps.size();
//					map.remove(ent.getKey());
                    sqls.append(":").append(ent.getKey());
                    break;
                }else if(obj instanceof Object[]){
                    Object[] temps=(Object[]) obj;
                    length=temps.length;
//					map.remove(ent.getKey());
                    sqls.append(":").append(ent.getKey());
                    break;
                }else {
                    sqls.append(":").append(ent.getKey());
                    break;
                }
            }
            length++;
        }
        return sqls;
    }



    private static List<Object> chang2List(Map<String, Object> map) {
        List<Object> values=new ArrayList<>();
        for(Map.Entry<String, Object> ent:map.entrySet()){
            Object obj = ent.getValue();
            //这里考虑传入的参数是什么类型，不同类型使用的方法不同
            if(obj instanceof Collection<?> ){
                Collection<?> temps= (Collection<?>) obj;
                values.addAll(temps);
            }else if(obj instanceof Object[]){
                Object[] temps=(Object[]) obj;
                values.addAll(Arrays.asList(temps));
            }else
                values.add(ent.getValue());
        }
        return values;
    }

    public static List<Object> chang2List(Object ...pars) {
        List<Object> values=new ArrayList<>();
        for(Object obj :pars){
            //这里考虑传入的参数是什么类型，不同类型使用的方法不同
            if(obj instanceof Collection<?> ){
                Collection<?> temps= (Collection<?>) obj;
                values.addAll(temps);
            }else if(obj instanceof Object[]){
                Object[] temps=(Object[]) obj;
                values.addAll(Arrays.asList(temps));
            }else
                values.add(obj);
        }
        return values;
    }

    /**格式化列名
     * @param sql
     * @return
     */
    public static String formatSQL(String sql) {
        String temp=sql.substring(0, sql.indexOf("from"));
        System.out.println(temp);
        String cols[]=temp.split(",");
        StringBuilder nsql=new StringBuilder();
        Map<String, Object> map=new HashMap<>();
        for(String col:cols) {
            col=col.substring(0,col.indexOf(" as "));
            System.out.println(col);
            int ind=col.indexOf(".");
            String formatCol=changCol(col.substring(ind+1)).trim();
            if(!map.containsKey(formatCol)) {
                map.put(formatCol, null);
                nsql.append(col)
                        .append(" as ")
                        .append(formatCol)
                        .append(",");
            }
        }
        String select=nsql.toString().substring(0, nsql.toString().length()-1);
        String from=" "+sql.substring(sql.indexOf("from"));

        return select + from;
    }
    private static String changCol(String col) {
        if(col==null) return null;
        StringBuilder sb=new StringBuilder();
        String temp;
        if(!col.contains("_")) {
            return col;
        }else {
            String strs[]=col.split("_");
            for(int i=0;i<strs.length;i++) {
                if(i==0) {
                    sb.append(strs[i]);
                }else {
                    temp=strs[i];
                    sb.append(temp.substring(0, 1).toUpperCase())
                            .append(temp.substring(1));
                }
            }
        }
        return sb.toString();
    }

    public static String changSQL(String sql,Object... values) {
        if(!sql.contains(" in (?)"))
            return sql;

        StringBuilder sqls=new StringBuilder();
        List<Object> list = new ArrayList<>(Arrays.asList(values));
        int num=0;
        for(String s :sql.split(" in (?)")) {
//			System.out.println(s);
            if(!s.contains("(?)")){
                if(num==0) {
                    sqls.append(s);
                }else {
                    sqls.append(" in ").append(s);
                }
                num++;
            }else {
                sqls.append(" in (");
                int length=0;
                for(Object obj:list){
                    //这里考虑传入的参数是什么类型，不同类型使用的方法不同
                    if(obj instanceof Collection<?>){
                        Collection<?> temps= (Collection<?>) obj;
                        length=temps.size();
                        list.remove(obj);
                        break;
                    }else if(obj instanceof Object[]){
                        Object[] temps=(Object[]) obj;
                        length=temps.length;
                        list.remove(obj);
                        break;
                    }
                }
                StringBuilder temp=new StringBuilder();
                for(int i=0;i<length;i++) {
                    temp.append("?,");
                }
                String sqlt=temp.toString().substring(0, temp.toString().length()-1);
                sqls.append(sqlt);
                sqls.append(") ");
                sqls.append(s.replace("(?)", ""));
            }
        }

        System.out.println("chang sql: "+sqls.toString());
        return sqls.toString();
    }

    /**HQL 转  SQL
     * @param hql
     * @param session
     * @return
     */
    public static  String getHql2Sql(String hql, Session session) {
        SessionFactoryImpl sfi = (SessionFactoryImpl)session.getSessionFactory();
        QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql,
                Collections.EMPTY_MAP, sfi);
        queryTranslator.compile(Collections.EMPTY_MAP, false);
        // 得到sql
        return queryTranslator.getSQLString();
    }
}