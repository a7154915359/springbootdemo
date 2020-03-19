package com.xiaowei.springbootdemo.pro.service;

import com.xiaowei.springbootdemo.pro.interf.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class CaptchaService {
    @Value("${client.validateCode.timeout}")
    private Long timeout;
    @Autowired
    ICacheService redisCacheServiceImpl;

    public Map<String, Object> createToken(String captcha) {
        //生成一个token
        String cToken = UUID.randomUUID().toString();
        //生成验证码对应的token  以token为key  验证码为value存在缓存中
        redisCacheServiceImpl.setCache(cToken, captcha, timeout);
        Map<String, Object> map = new HashMap<>();
        map.put("cToken", cToken);
        map.put("expire", timeout);
        return map;
    }
}
