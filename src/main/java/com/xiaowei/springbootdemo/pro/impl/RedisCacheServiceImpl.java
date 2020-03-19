package com.xiaowei.springbootdemo.pro.impl;

import com.xiaowei.springbootdemo.pro.interf.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheServiceImpl implements ICacheService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String setCache(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "";
    }

    @Override
    //springboot内置缓存不支持过期设定
    public String setCache(String key, String value, Long outTime) {
        redisTemplate.opsForValue().set(key, value, outTime, TimeUnit.MILLISECONDS);
        return "";
    }

    @Override
    public void removeCache(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public String setMapCache(String key, Map map) {
        try {
            redisTemplate.opsForHash().putAll(key,map);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "";
    }
    public Map<Object, Object> getMapCache(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
