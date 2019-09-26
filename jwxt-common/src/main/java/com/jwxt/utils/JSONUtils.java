package com.jwxt.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.NullArgumentException;

import java.util.List;

public class JSONUtils {

    public static<T> List<T> ObjectToList(Object o,Class<T> clazz){
        if(o == null) throw new NullArgumentException("参数为空");
        try{
            String string = JSON.toJSONString(o);
            List<T> list = JSONArray.parseArray(string, clazz);
            return list;
        }catch (Exception e){
            throw new ClassCastException(e.getMessage());
        }
    }

    /**
     *  Object类型转换成JavaBean
     * @param o 对象
     * @param clazz 字节码
     * @param <T> 泛型
     * @return 传入的泛型对象
     */
    public static<T>  T ObjectToJavaBean(Object o,Class<T> clazz){
        String string = JSON.toJSONString(o);
        T t = JSON.parseObject(string, clazz);
        return t;
    }

}
