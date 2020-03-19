package com.xiaowei.springbootdemo.pro.impl;

import com.xiaowei.springbootdemo.pro.interf.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements ICacheService {
    @Override
    @CachePut(value = "global", key = "#key")
    public String setCache(String key, String value) {
        return value;
    }

    @Override
    //springboot内置缓存不支持过期设定
    public String setCache(String key, String value, Long outTime) {
        return value;
    }

    @Override
    @CacheEvict(value = "global", key = "#key")
    public void removeCache(String key) {

    }

    @Override
    @Cacheable(value = "global", key = "#key")
    public String getCache(String key) {
        return null;
    }
}
