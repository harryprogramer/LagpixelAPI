package com.api.sensors;

public class SystemInfoAPI {
    public static volatile SystemInfo instance;
    private final static double APIVersion = 1.1;


    public static SystemInfo getAPI(){
        if(instance != null){
            return instance;
        }else {
            instance = new SystemInfoImpl();
            return instance;
        }
    }

    public static double getVersion(){
        return APIVersion;
    }
}
