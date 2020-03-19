package com.xiaowei.springbootdemo.pro.interf;

public interface ICacheService {
    public String setCache(String key, String value);

    public String setCache(String key, String value, Long outTime);

    public void removeCache(String key);

    public String getCache(String key);
}
