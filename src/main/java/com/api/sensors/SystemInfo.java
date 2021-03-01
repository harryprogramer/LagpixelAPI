package com.api.sensors;

public interface SystemInfo {
    void loadSensors();

    int getCPUTemp();

    double[] getDailyTemp();

    byte getDiskTemp();

    long getUsedMemory();

    long getFreeMemory();

    long getTotalMemory();

    double getInternetPing();

    String getCPUName();
}
