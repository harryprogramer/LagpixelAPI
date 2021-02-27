package com.api.sensors;

import com.CPUTelemetry;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;

import java.util.List;

class SystemInfoImpl implements SystemInfo {
    private Components components;
    List<Cpu> cpus;


    @Override
    public void loadSensors() {
        components = JSensors.get.components();
        cpus = components.cpus;
    }

    @Override
    public void startMeasure(CPUTelemetry.TYPE type) {

    }

    @Override
    public void stopMeasure(CPUTelemetry.TYPE type) {

    }

    @Override
    public int getCPUTemp() {
        if(components != null && cpus != null){
        int size = 0;
        double[] temparray;
            for (final Cpu cpu : cpus) {
                if (cpu.sensors != null) {
                    List<Temperature> temps = cpu.sensors.temperatures;
                    for (final Temperature ignored : temps) {
                        size++;
                    }
                    if (size != 0) {
                        int allNumbers = 0;
                        temparray = new double[size];
                        for (int i = 1; i < temps.size(); i++) {
                            temparray[i] = temps.get(i).value;
                        }
                        if (temparray.length != 1) {
                            int i;
                            for (i = 0; i < temparray.length; i++) {
                                allNumbers = allNumbers + (int) temparray[i];
                            }
                            return allNumbers / i;
                        }
                    }

                }
            }
        }
        return 0;
    }

    @Override
    public double[] getDailyTemp() {
        return CPUTelemetry.getInstance().getDayTemp();
    }

    @Override
    public byte getDiskTemp() {
        return 0;
    }

    @Override
    public String getCPUName() {
        for(final Cpu cpu : cpus){
            return cpu.name;
        }

        return null;
    }
}
