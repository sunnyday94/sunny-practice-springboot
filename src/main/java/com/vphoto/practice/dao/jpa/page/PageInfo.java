package com.vphoto.practice.dao.jpa.page;

import lombok.Data;

import java.util.List;

/**
 * @Description: 分页查询返回类
 * @Author: sunny
 * @Date: 2018/8/24 15:52
 */
@Data
public class PageInfo {

	/**
	 * 分页List
	 */
	private List<?> list;
	
	/**
	 * 记录总数
	 */
	private int total;


//	/**传入序列化Class对象
//	 * @param t
//	 * @return
//	 */
//	public <T> List<T> getList(Class<T> t) {
////		Gson gson = new GsonBuilder()
////		  .setDateFormat("yyyy-MM-dd HH:mm:ss")  // HH:mm:ss 格式化
////		  .create();
////		List<T> reslist=new ArrayList<T>();
////		if(list!=null){
////			String body=gson.toJson(list);
////			JsonArray array = new JsonParser().parse(body).getAsJsonArray();
////	        for(final JsonElement elem : array){
////	            reslist.add(new Gson().fromJson(elem, t));
////	        }
////		}
//		return JsonUtils.toListObject(list, t);
//	}
}
