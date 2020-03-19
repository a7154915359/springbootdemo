package com.xiaowei.springbootdemo.pro.pojo;

public class SystemInfo {
    int cpuUse;
    int memUse;
    int diskUse;
    String[] lineTime;
    String[] cpuLineUseVal;
    String[] memLineUseVal;
    String[] diskLineUseVal;

    public int getCpuUse() {
        return cpuUse;
    }

    public void setCpuUse(int cpuUse) {
        this.cpuUse = cpuUse;
    }

    public int getMemUse() {
        return memUse;
    }

    public void setMemUse(int memUse) {
        this.memUse = memUse;
    }

    public int getDiskUse() {
        return diskUse;
    }

    public void setDiskUse(int diskUse) {
        this.diskUse = diskUse;
    }


    public String[] getCpuLineUseVal() {
        return cpuLineUseVal;
    }

    public void setCpuLineUseVal(String[] cpuLineUseVal) {
        this.cpuLineUseVal = cpuLineUseVal;
    }


    public String[] getMemLineUseVal() {
        return memLineUseVal;
    }

    public void setMemLineUseVal(String[] memLineUseVal) {
        this.memLineUseVal = memLineUseVal;
    }

    public String[] getDiskLineUseVal() {
        return diskLineUseVal;
    }

    public void setDiskLineUseVal(String[] diskLineUseVal) {
        this.diskLineUseVal = diskLineUseVal;
    }

    public String[] getLineTime() {
        return lineTime;
    }

    public void setLineTime(String[] lineTime) {
        this.lineTime = lineTime;
    }
}
