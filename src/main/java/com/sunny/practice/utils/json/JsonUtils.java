package com.sunny.practice.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名 : JasonUtils.java
 * 包名 : com.vphoto.practice.utils.json
 * 创建人 : sunny
 * 创建时间: 2018-8-24 下午2:49:44
 * 类说明 :
 * 关于日期格式，默认是据条例1970-1-1的毫秒数 如果想指定固定的格式有两个方法
 * 1.在属性方法上增加注解@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , locale = "zh" , timezone="GMT+8")
 * 2.在环境参数中 application.properties 中增加两个属性    spring.jackson.date-format=yyyy-MM-dd HH:mm:ss   spring.jackson.time-zone=GMT+8
 */
public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper()//定义全局操作mapper 线程安全可以全局使用
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);  //对不能序列化的部分对象不进行序列化操作

    static {
    	mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.getSerializationConfig().getDefaultPropertyFormat(JsonDoubleFormat.class);
        mapper.getSerializationConfig().getDefaultPropertyFormat(JsonDateFormat.class);
        
        
    }
//	private static final XmlMapper xmlMapper = new XmlMapper();
    //==1.基本方法 ====================================

    /**
     * 把对象转换成json字符串
     * 注意：如果是list
     *
     * @param obj 可以是JavaBean 也可以是list
     * @return obj==null return {}
     */
    public static String toJson(Object obj) {
        if (obj == null) return "{}";
        String json = "";
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 返回指定对象
     *
     * @param json
     * @return Map<String   ,   Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toObject(String json) {
        if (StringUtils.isBlank(json)) return null;
        try {
            return mapper.readValue(json, Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回指定对象
     *
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || clazz == null) return null;
        try {
            //==解决json中属性比class属性多报错的问题
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回指定List<T>
     *
     * @param json
     * @param clazz T
     * @return
     */
    public static List<Map<String, Object>> toListObject(String json) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (StringUtils.isBlank(json)) return list;
        JavaType javaType = getCollectionType(ArrayList.class, Map.class);
        try {
            list = mapper.readValue(json, javaType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, Object> toMap(String json) {

        return toObject(json);
    }


    /**
     * 返回指定List<T>
     *
     * @param json
     * @param clazz T
     * @return
     */
    public static <T> List<T> toListObject(String json, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        if (StringUtils.isBlank(json) || clazz == null) return list;
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        try {
            list = mapper.readValue(json, javaType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 结构化数据转DTO对象<br>
     * 这个是Dao使用的，不建议其他人使用<br>
     * 对象有date时 请使用@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
     * @param objs
     * @param clazz
     * @return
     */
    public static <T> List<T> toListObject(List<?> objs, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        if (objs == null || clazz == null)
            return list;
        log.info(clazz + toJson(objs));
        for (Object obj : objs) {
            list.add(toObject(toJson(obj), clazz));
        }
        return list;
    }

    //====xml==========================
//	/**
//	 * 把对象转换成json字符串
//	 * @param obj 可以是JavaBean 也可以是list
//	 * @return
//	 */
//	public static String toXml(Object obj){
//		String xml = "";
//		if(obj==null)return xml;
//		try {
//			xml = xmlMapper.writeValueAsString(obj);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return xml;
//	}
//	
//
//	/** 返回指定对象
//	 * @param json
//	 * @return Map<String,Object> 
//	 */
//	@SuppressWarnings("unchecked")
//	public static Map<String,Object> toObjectXml(String xml){
//		if(isNull(xml)) return null;
//		try {
//			return xmlMapper.readValue(xml, Map.class);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/** 返回指定对象
//	 * @param <T>
//	 * @param xml
//	 * @param clazz
//	 * @return
//	 */
//	public static <T> T toObjectXml(String xml,Class<T> clazz){
//		if(isNull(xml) || clazz == null) return null;
//		try {
//			return xmlMapper.readValue(xml, clazz);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**返回指定List<T>
//	 * @param <T>
//	 * @param xml 最外层 必须有且仅有一个顶层标签
//	 * @param clazz
//	 * @return
//	 */
//	public static <T> List<Map<String,Object>> toListObjectXml(String xml){
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		if(isNull(xml)) return list;
//        JavaType javaType = getCollectionType(ArrayList.class, Map.class); 
//        try {
//			list =  xmlMapper.readValue(xml, javaType);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//        return list;  
//	}
//	
//	/**返回指定List<T>
//	 * @param <T>
//	 * @param xml 最外层 必须有且仅有一个顶层标签
//	 * @param clazz
//	 * @return
//	 */
//	public static <T> List<T> toListObjectXml(String xml,Class<T> clazz){
//		List<T> list = new ArrayList<T>();
//		if(isNull(xml) || clazz ==null) return list;
//        JavaType javaType = getCollectionType(ArrayList.class, clazz); 
//        try {
//			list =  xmlMapper.readValue(xml, javaType);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//        return list;  
//	}
//	
//	/**
//	 * 该方法测试没有通过
//	 * json转换成xml
//	 * 由于xml与json结构的特殊性，所以在转换后xml最外面会自动增加一层标签<xmroot></xmroot> <br>
//	 * 多数据方式无法转换成xml [{a:1,b:1},{a:2,b:2}] 这种格式 由于没有标签导致无法转换成xml
//	 * @param jsonStr
//	 * @return
//	 * @throws Exception
//	 */
//	@Deprecated 
//	public static String json2xml(String json){  
//		if(isNull(json))return "";
//        JsonNode root;
//		try {
////			if(json.startsWith("[")) {
////				json = json.substring(1);
////				json = "{"+json;
////			}
////			if(json.endsWith("]")) {
////				json = json.substring(0, json.length()-1);
////				json = json + "}";
////			}
//			root = mapper.readTree(json);
//			if(root.isArray()) {
//				log.debug("======array==================");
//			}
//			String xml = xmlMapper.writeValueAsString(root);
//			if(xml!=null) { //默认会在最外面增加 ObjectNode标签   这里把标签修改为 xmroot
//				xml = xml.replaceAll("ObjectNode", "xmroot");
//			}
//			return xml;
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}  
//		return "";
//	}  
//	/**
//	 * 该方法测试没有通过
//	 * xml转换成json<br>
//	 * 1.最外面有且只有一个根节点，如果是多个的一定自己在最外面增加一个节点，否则会异常<br>
//	 * 例子： <root><a><b>1</b></a><a><b>2</b></a></root> 一定不要缺少root根 节点 <br>
//	 * @param xml <root><a><b>1</b></a><a><b>2</b></a></root>
//	 * @return
//	 */
//	@Deprecated 
//	public static String xml2json(String xml) {
//		if(isNull(xml)) {
//			return xml = "";//如果是null 则会抛异常
//		} else {
//			xml = "<_root>"+xml+"</_root>";//如果按照正常的 会把最外面的标签丢掉 所以自动补充了外层标签
//		}
//		StringWriter w = new StringWriter();
//		JsonParser jp = null;
//		JsonGenerator jg = null;
//		try {
//			jp = xmlMapper.getFactory().createParser(xml);
//			jg = mapper.getFactory().createGenerator(w);
//			while (jp.nextToken() != null) {
//				jg.copyCurrentEvent(jp);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if(jp!=null)jp.close();
//				if(jg!=null)jg.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return w.toString();
//	}

    //==============其他方法========================================

    private static Map<String, String> obj2Map(Object obj) {

        Map<String, String> map = new HashMap<String, String>();
        // System.out.println(obj.getClass());  
        // 获取f对象对应类中的所有属性域  
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性  
            try {
                // 获取原来的访问控制权限  
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限  
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量  
                Object o = fields[i].get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);  
                // 恢复访问控制权限  
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }


    //========================私有方法=================================

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


}
