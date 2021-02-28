package com;

import com.api.sensors.SystemInfo;
import com.api.sensors.SystemInfoAPI;
import util.Logger;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class CPUTelemetry{
    private final Thread measureThread;
    private static CPUTelemetry instance;
    private boolean measureBoolean;
    private boolean dailyMeasureBool;
    private int temp = 0;
    private volatile double[] tempDay;
    LocalDateTime startTime;
    Thread tempDayMeasureThread;
    SystemInfo systemAPI = SystemInfoAPI.getAPI();

    private CPUTelemetry() {
        tempDayMeasureThread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    while (dailyMeasureBool) {
                        if (tempDay == null) {
                            tempDay = new double[100];
                        }
                        startTime = LocalDateTime.now();
                        for(int i = 0; i < tempDay.length; i++) {
                            Logger.Log_ln("CPU temp day api value update, buffor index: " + i, Logger.Level.DEBUG, Logger.Type.SYSTEM);
                            tempDay[i] = getTemp();
                            try {
                                this.wait(900000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Logger.Log_ln("Temp cpu Buffor full, clearing...", Logger.Level.INFO, Logger.Type.SYSTEM);
                        tempDay = new double[100];
                    }
                }
            }
        };

        measureThread =  new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    while (measureBoolean) {
                        Logger.Log_ln("CPU temp api value update", Logger.Level.DEBUG, Logger.Type.SYSTEM);
                        temp = systemAPI.getCPUTemp();
                        try {
                            this.wait(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    public enum TYPE{
        CURRENT,
        DAILY,
        ALL
    }

    public synchronized static CPUTelemetry getInstance(){
        if(instance == null){
            instance = new CPUTelemetry();
        }
        return instance;
    }

    public synchronized void startMeasure(TYPE type){
        switch (type){
            case CURRENT:
                measureBoolean = true;
                measureThread.start();
                break;
            case DAILY:
                dailyMeasureBool = true;
                tempDayMeasureThread.start();
                break;
            case ALL:
                measureBoolean = true;
                measureThread.start();
                dailyMeasureBool = true;
                tempDayMeasureThread.start();
        }
    }

    public synchronized double[] getDayTemp(){
       return tempDay;
    }

    public synchronized void stopMeasure(TYPE type){
        switch (type){
            case CURRENT:
                measureBoolean = false;
                break;
            case DAILY:
                dailyMeasureBool = false;
                break;
            case ALL:
                measureBoolean = false;
                dailyMeasureBool = false;
        }
    }

    public synchronized int getTemp(){
        Logger.Log_ln("Kernel api call getTemp(), returned temp: " + temp, Logger.Level.INFO, Logger.Type.SYSTEM);
        return temp;
    }
    public synchronized String measureStartTime(){return startTime.toString();}

}
