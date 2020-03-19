package com.xiaowei.springbootdemo.pro.utils;

import com.xiaowei.springbootdemo.pro.impl.RedisCacheServiceImpl;
import com.xiaowei.springbootdemo.pro.pojo.SystemInfo;
import org.apache.commons.collections.map.HashedMap;
import org.hyperic.sigar.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Max;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class SystemInfoUtils {
    public static SystemInfo getSystemUseInfo() throws SigarException, IOException {
        SystemInfo systeminfo = new SystemInfo();
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        CpuPerc cpuperc = sigar.getCpuPerc();
        //获取磁盘信息
        FileSystem fslist[] = sigar.getFileSystemList();
        File f = new File(SystemInfo.class.getResource("/").getPath());
        for (int i = 0; i < fslist.length; i++) {
            FileSystem fs = fslist[i];
            if (f.getCanonicalPath().indexOf(fs.getDirName()) != -1) {
                FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                systeminfo.setDiskUse((int) (usage.getUsePercent() * 100));
                break;
            }
        }
        //获取内存信息
        int memUse = (int) ((float) mem.getUsed() / mem.getTotal() * 100);
        systeminfo.setMemUse(memUse);
        //获取cpu信息
        int cpuUse = (int) (cpuperc.getUser() * 100);
        systeminfo.setCpuUse(cpuUse);
        return systeminfo;

    }

    public static SystemInfo getSystemUseLineInfo(RedisCacheServiceImpl redisCacheServiceImpl) throws SigarException, IOException {
        SystemInfo systeminfo = new SystemInfo();
        Map<Object, Object> cpuUseMap = redisCacheServiceImpl.getMapCache("cpuUse");
        Map<Object, Object> diskUseMap = redisCacheServiceImpl.getMapCache("diskUse");
            Map<Object, Object> memUseMap = redisCacheServiceImpl.getMapCache("memUse");
            Map<String,String[]> cpuUseMapResult = getKeyArrayAndValueArray(cpuUseMap);
        Map<String,String[]> diskUseMapResult = getKeyArrayAndValueArray(diskUseMap);
        Map<String,String[]> memUseMapResult = getKeyArrayAndValueArray(memUseMap);
        systeminfo.setLineTime(cpuUseMapResult.get("key"));
        systeminfo.setCpuLineUseVal(cpuUseMapResult.get("value"));
        systeminfo.setDiskLineUseVal(diskUseMapResult.get("value"));
        systeminfo.setMemLineUseVal(memUseMapResult.get("value"));
        return systeminfo;

    }
    public static Map<String,String[]> getKeyArrayAndValueArray(Map map){
        SortedMap<String,String> sort=new TreeMap<String,String>(map);
        List<String> listKey = new ArrayList();
        List<String> listvalue = new ArrayList();
        Set<Map.Entry<String,String>> entry1=sort.entrySet();
        Iterator<Map.Entry<String,String>> it=entry1.iterator();
        while(it.hasNext())
        {
            Map.Entry<String,String> entry=it.next();
            listKey.add(entry.getKey());
            listvalue.add(entry.getValue());
        }
        Map<String,String[]> result = new HashedMap();
        result.put("key",  listKey.toArray(new String[0]));
        result.put("value", listvalue.toArray(new String[0]));
        listKey.clear();
        listvalue.clear();
        return result;
    }
}
