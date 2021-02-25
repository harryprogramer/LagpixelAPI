package com.sensors;

public class SystemInfoAPI {
    public static volatile SystemInfo instance;

    public static SystemInfo getAPI(){
        if(instance != null){
            return instance;
        }else {
            instance = new SystemInfo() {

                @Override
                public byte getCPUTemp() {
                    return 120;
                }

                @Override
                public byte getDiskTemp() {
                    return 0;
                }
            };

            return instance;
        }
    }
}
