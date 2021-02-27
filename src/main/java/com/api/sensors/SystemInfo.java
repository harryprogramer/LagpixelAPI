package com.api.sensors;

import com.CPUTelemetry;

public interface SystemInfo {
    void loadSensors();

    void startMeasure(CPUTelemetry.TYPE type);

    void stopMeasure(CPUTelemetry.TYPE type);

    int getCPUTemp();

    double[] getDailyTemp();

    byte getDiskTemp();

    String getCPUName();
}
