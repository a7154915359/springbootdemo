package com.xiaowei.springbootdemo.pro.scheduled;

import com.xiaowei.springbootdemo.pro.impl.RedisCacheServiceImpl;
import com.xiaowei.springbootdemo.pro.pojo.SystemInfo;
import com.xiaowei.springbootdemo.pro.utils.SystemInfoUtils;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Async
@Component
public class SystemInfoScheduled {
    private final Logger logger = LoggerFactory.getLogger(SystemInfoScheduled.class);
    @Autowired
    RedisCacheServiceImpl redisCacheServiceImpl;
    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * ?")
    public void scheduled() throws IOException, SigarException {
        logger.info("=====>>>>>获取系统资源使用信息start");
        Calendar now = Calendar.getInstance();
        String currHour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        Map<Object, Object> cpuUseMap = redisCacheServiceImpl.getMapCache("cpuUse");
        Map<Object, Object> diskUseMap = redisCacheServiceImpl.getMapCache("diskUse");
        Map<Object, Object> memUseMap = redisCacheServiceImpl.getMapCache("memUse");
        SystemInfo systeminfo = SystemInfoUtils.getSystemUseInfo();
        if (cpuUseMap == null){
            cpuUseMap = new HashMap<Object, Object>();
        }
        if (diskUseMap == null){
            diskUseMap = new HashMap<Object, Object>();
        }
        if (memUseMap == null){
            memUseMap = new HashMap<Object, Object>();
        }
        cpuUseMap.put(currHour,String.valueOf(systeminfo.getCpuUse()));
        diskUseMap.put(currHour,String.valueOf(systeminfo.getDiskUse()));
        memUseMap.put(currHour,String.valueOf(systeminfo.getMemUse()));
        redisCacheServiceImpl.setMapCache("cpuUse", cpuUseMap);
        redisCacheServiceImpl.setMapCache("diskUse", diskUseMap);
        redisCacheServiceImpl.setMapCache("memUse", memUseMap);
        logger.info("=====>>>>>获取系统资源使用信息end");
    }
}
