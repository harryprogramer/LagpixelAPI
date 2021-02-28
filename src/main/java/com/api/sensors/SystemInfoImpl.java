package com.api.sensors;

import com.CPUTelemetry;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import util.Logger;

import java.util.List;

class SystemInfoImpl implements SystemInfo {
    private Components components;
    List<Temperature> temps;
    List<Cpu> cpus;


    @Override
    public void loadSensors() {
        Logger.Log_ln("Call to loadSensors, in progress", Logger.Level.DEBUG, Logger.Type.SYSTEM);
        components = JSensors.get.components();
        cpus = components.cpus;
        Logger.Log_ln("Done to loadSensors, in progress", Logger.Level.DEBUG, Logger.Type.SYSTEM);
    }

    @Override
    public void startMeasure(CPUTelemetry.TYPE type) {

    }

    @Override
    public void stopMeasure(CPUTelemetry.TYPE type) {

    }

    @Override
    public synchronized int getCPUTemp() {
        loadSensors();
        int allNumbers;
        if(components != null && cpus != null){
        int size = 0;
        double[] temparray;
            for (final Cpu cpu : cpus) {
                if (cpu.sensors != null) {
                    temps = cpu.sensors.temperatures;
                    for (final Temperature ignored : temps) {
                        size++;
                    }
                    if (size != 0) {
                        allNumbers = 0;
                        temparray = new double[size];
                        for (int i = 1; i < temps.size(); i++) {
                            temparray[i] = temps.get(i).value;
                        }
                        if (temparray.length != 1) {
                            int i;
                            for (i = 0; i < temparray.length; i++) {
                                allNumbers = allNumbers + (int) temparray[i];
                            }

                            Logger.Log_ln("Call to SystemInfoImpl, returned value: " + allNumbers / i + " bufured numbers: " + allNumbers, Logger.Level.DEBUG, Logger.Type.SYSTEM);
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
