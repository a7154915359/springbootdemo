package com.xiaowei.springbootdemo.pro.controller;

import com.xiaowei.springbootdemo.pro.impl.RedisCacheServiceImpl;
import com.xiaowei.springbootdemo.pro.pojo.SystemInfo;
import com.xiaowei.springbootdemo.pro.result.Result;
import com.xiaowei.springbootdemo.pro.result.ResultFactory;
import com.xiaowei.springbootdemo.pro.utils.SystemInfoUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.hyperic.sigar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class SystemInfoController {
    @Autowired
    private RedisCacheServiceImpl redisCacheServiceImpl;
    @CrossOrigin
    @GetMapping(value = "/api/systeminfo/useinfo")
    @ResponseBody
    public Result getSystemUseInfo() {
        try {
            SystemInfo systeminfo = SystemInfoUtils.getSystemUseInfo();
            return ResultFactory.buildSuccessResult(systeminfo);
        } catch (SigarException e) {
            e.printStackTrace();
            String message = "查询出错";
            return ResultFactory.buildFailResult(message);
        } catch (IOException e) {
            e.printStackTrace();
            String message = "查询出错";
            return ResultFactory.buildFailResult(message);
        }
    }
    @CrossOrigin
    @GetMapping(value = "/api/systeminfo/uselineinfo")
    @ResponseBody
    public Result getSystemUseLineInfo() {
        try {
            SystemInfo systeminfo = SystemInfoUtils.getSystemUseLineInfo(redisCacheServiceImpl);
            return ResultFactory.buildSuccessResult(systeminfo);
        } catch (SigarException e) {
            e.printStackTrace();
            String message = "查询出错";
            return ResultFactory.buildFailResult(message);
        } catch (IOException e) {
            e.printStackTrace();
            String message = "查询出错";
            return ResultFactory.buildFailResult(message);
        }
    }

}
