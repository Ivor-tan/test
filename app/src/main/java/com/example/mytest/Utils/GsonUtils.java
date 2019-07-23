package com.example.mytest.Utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

    /**
     * json字符串转化成Bean
     *
     * @param jsonResponse
     * @param clz
     * @return
     */
    public static <T> T jsonToBean(String jsonResponse, Class<T> clz) {
/*		Gson gson = new Gson();
		try {
			T t = gson.fromJson(jsonResponse, clz);
			return t;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;*/

        Gson gson = new Gson();
        T t = gson.fromJson(jsonResponse, clz);
        return t;
    }


    /**
     * 转化String字符串
     *
     * @param object
     * @return
     */
    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    /**
     * json字符串转List<T>列表
     *
     * @param gsonString
     * @param type       转化的目标类型
     *                   new TypeToken<List<类名>>() {}.getType()
     * @return
     */
    public static <T> List<T> changeGsonToList(String gsonString, Type type) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(gsonString, type);
        return list;
    }

}
