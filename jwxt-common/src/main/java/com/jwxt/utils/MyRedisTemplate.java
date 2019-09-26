package com.jwxt.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRedisTemplate {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void setObject(String key,Object o){
        try {
            if(o != null){
                redisTemplate.opsForValue().set(key,JSON.toJSONString(o));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hSetObject(String key,String field,Object o){
        try {
            if(o != null){
                redisTemplate.opsForHash().put(key,field,JSON.toJSONString(o));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Object getObject(String key,Class<?> clazz){
        try {
            if(!redisTemplate.hasKey(key)){
                return null;
            }else{
                Object o = redisTemplate.opsForValue().get(key);
                return JSONUtils.ObjectToJavaBean(o, clazz);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Object hGetObject(String key,String field,Class<?> clazz){
        try {
            if(!redisTemplate.hasKey(key)){
                return null;
            }else{
                Object o = redisTemplate.opsForHash().get(key,field);
                return JSONUtils.ObjectToJavaBean(o, clazz);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    /***********************  List集合   **************************/


    public void setList(String key, List<?> list){
        try{
            if(list != null && list.size() > 0){
                //redisTemplate.opsForValue().set(key,SerializeUtil.serializeList(list));
                redisTemplate.opsForValue().set(key, JSON.toJSONString(list));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hSetList(String key,String field,List<?>list){
        try{
            if(list != null && list.size() > 0){
                //redisTemplate.opsForValue().set(key,SerializeUtil.serializeList(list));
                redisTemplate.opsForHash().put(key,field,JSON.toJSONString(list));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public  List<?> getList(String key,Class<?>clazz){
        try {
            if(!redisTemplate.hasKey(key)){
                return null;
            }else{
                Object o = redisTemplate.opsForValue().get(key);
                List<?> objects = JSONUtils.ObjectToList(o, clazz);
                return objects;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public List<?> hGetList(String key,String field,Class<?>clazz){
        try {
            if(!redisTemplate.hasKey(key)){
                return null;
            }else{
                Object o = redisTemplate.opsForHash().get(key,field);
                List<?> objects = JSONUtils.ObjectToList(o, clazz);
                return objects;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
