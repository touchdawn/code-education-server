package com.itheima.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //=================common===============
    /**
     * 指定缓存失效时间
     * @param key
     * @param time
     */
    public  boolean expire(String key,long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒)0代表永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    //=========================String=====================
    /**
     * 普通缓存获取
     * @param key
     */
    public Object get(String key ) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key
     * @param value
     * @param time
     * @return true成功 false失败
     */
    public boolean set (String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta (>0)
     */
    public long incr(String key,long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子需要大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key
     * @param delta (>0)
     */
    public long decr(String key,long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子需要大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //====================Map======================
    /**
     * HashGet
     * @param key
     * @param item
     */
    public Object hget(String key, String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取hashkey对应的所有键值
     * @param key
     * @return 对应的多个键值
     */
    public Object hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * hashset
     * @param key
     * @param map 对应的多个键值
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hashset + 时间
     * @param key
     * @param map 对应的多个键值
     * @param time 秒
     */
    public boolean hmset(String key, Map<String,Object> map, long time ){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            if (time > 0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向hash表放入数据，不存在就创建
     * @param key
     * @param item 项
     * @param value 值
     */
    public boolean hset(String key, String item, Object value ){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向hash表放入数据，不存在就创建
     * @param key
     * @param item 项
     * @param value 值
     * @param time
      */
    public boolean hset(String key, String item, Object value, long time ){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key
     * @param item 项
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key
     * @param item 项
     */
    public boolean hHaskey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增,如果不存在就会创建一个 并把新增后的值返回
     * @param key
     * @param item 项
     * @param by 要加几 >0
     */
    public double hincr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key,item,by);
    }

    /**
     * hash递减,如果不存在就会创建一个 并把新增后的值返回
     * @param key
     * @param item 项
     * @param by 要加几 >0
     */
    public double hdecr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key,item,-by);
    }

    //===============set
    /**
     * 根据key获取set中的所有值
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
