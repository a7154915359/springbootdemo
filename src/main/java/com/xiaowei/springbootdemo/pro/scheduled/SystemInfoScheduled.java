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
import java.util.LinkedHashMap;
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
        if (cpuUseMap == null || cpuUseMap.size() == 0){
            cpuUseMap = initSystemInfo();
        }
        if (diskUseMap == null || diskUseMap.size() == 0){
            diskUseMap = initSystemInfo();
        }
        if (memUseMap == null || memUseMap.size() == 0){
            memUseMap = initSystemInfo();
        }
        cpuUseMap.put(currHour,String.valueOf(systeminfo.getCpuUse()));
        diskUseMap.put(currHour,String.valueOf(systeminfo.getDiskUse()));
        memUseMap.put(currHour,String.valueOf(systeminfo.getMemUse()));
        redisCacheServiceImpl.setMapCache("cpuUse", cpuUseMap);
        redisCacheServiceImpl.setMapCache("diskUse", diskUseMap);
        redisCacheServiceImpl.setMapCache("memUse", memUseMap);
        logger.info("=====>>>>>获取系统资源使用信息end");
    }
    public Map initSystemInfo(){
        Map<String,String> initSystemInfo = new LinkedHashMap<String,String>();
        initSystemInfo.put("0","0");
        initSystemInfo.put("3","0");
        initSystemInfo.put("6","0");
        initSystemInfo.put("9","0");
        initSystemInfo.put("12","0");
        initSystemInfo.put("15","0");
        initSystemInfo.put("18","0");
        initSystemInfo.put("21","0");
        return initSystemInfo;
    }
}
