package com;

import com.api.sensors.SystemInfo;
import com.api.sensors.SystemInfoAPI;
import util.Logger;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class Telemetry {
    private final Thread measureThread;
    private final Thread tempDayMeasureThread;
    private static Telemetry instance;
    private boolean measureBoolean;
    private boolean dailyMeasureBool;
    private boolean networkTelemetry;
    private int temp = 0;
    private volatile double[] tempDay;
    LocalDateTime buffFirstIndexTime;
    LocalDateTime lastTempUpdate;
    SystemInfo systemAPI = SystemInfoAPI.getAPI();

    private Telemetry() {
        tempDayMeasureThread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    while (dailyMeasureBool) {
                        if (tempDay == null) {
                            tempDay = new double[100];
                        }
                        buffFirstIndexTime = LocalDateTime.now();
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
                        lastTempUpdate = LocalDateTime.now();
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
        TEMPTELEMETRY,
        TEMPBUFFTELEMETRY,
        ALL
    }

    public synchronized static Telemetry getInstance(){
        if(instance == null){
            instance = new Telemetry();
        }
        return instance;
    }

    public synchronized void startMeasure(TYPE type){
        switch (type){
            case TEMPTELEMETRY:
                measureBoolean = true;
                measureThread.start();
                break;
            case TEMPBUFFTELEMETRY:
                dailyMeasureBool = true;
                tempDayMeasureThread.start();
                break;
            case ALL:
                measureBoolean = true;
                measureThread.start();
                dailyMeasureBool = true;
                tempDayMeasureThread.start();
                break;
        }
    }

    public synchronized double[] getDayTemp(){
       return tempDay;
    }

    public synchronized void stopMeasure(TYPE type){
        switch (type){
            case TEMPTELEMETRY:
                measureBoolean = false;
                break;
            case TEMPBUFFTELEMETRY:
                dailyMeasureBool = false;
                break;
            case ALL:
                measureBoolean = false;
                dailyMeasureBool = false;
        }
    }

    public synchronized int getTemp(){
        return temp;
    }
    
    public synchronized String measureStartTime(){return buffFirstIndexTime.toString();}
    
    
    public LocalDateTime getBuffTempFirstIndexTime(){
        return buffFirstIndexTime;
    }
    
    public LocalDateTime getLastTempUpdate(){
        return lastTempUpdate;
    }

}
