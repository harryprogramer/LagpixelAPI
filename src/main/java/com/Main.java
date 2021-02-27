package com;

import com.api.LagpixelAPI08;
import com.http.HTTPCore;
import com.api.sensors.SystemInfo;
import com.api.sensors.SystemInfoAPI;
import com.sql.API;
import com.sql.SQL;
import org.springframework.boot.SpringApplication;
import util.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class Main {
    LagpixelAPI lagPixelApi;
    Console console;
    API sqlAPI;
    SpringApplication application;
    Thread serversthread;
    SystemInfo systemapi;
    CPUTelemetry cpuTelemetry;


    private void InitObject(){
        Logger.setDebug(false);

        serversthread = new Thread(){
            synchronized void runHTTP(){
                Logger.Log_ln("Starting HTTP...", Logger.Level.INFO, Logger.Type.HTTP);
                try {
                    application.run();
                }catch (Exception e){
                    Logger.Log_ln("Is http server already run? ", Logger.Level.WARN, Logger.Type.SYSTEM);
                    System.exit(-1);
                }
            }
            synchronized void runAPISocket(){
                Logger.Log_ln("Starting Socket...", Logger.Level.INFO, Logger.Type.HTTP);
                lagPixelApi.connectToAPI();
            }

            @Override
            public void run() {
                runHTTP();
                runAPISocket();
                Logger.Log_ln("Done!, in " + ChronoUnit.MILLIS.between(Server.startTime, LocalDateTime.now()) / 1000.0 + " s", Logger.Level.INFO, Logger.Type.SYSTEM);
                consoleRun();
            }
        };
        console = new Console();
        systemapi = SystemInfoAPI.getAPI();
        lagPixelApi = LagpixelAPI08.getInstance();
        sqlAPI = SQL.getInstance();
        lagPixelApi.setPort(2800);
        lagPixelApi.setInetAddress("localhost");
        cpuTelemetry = CPUTelemetry.getInstance();
        application = new SpringApplication(HTTPCore.class);
        Properties properties = new Properties();
        properties.setProperty("spring.main.banner-mode", "off");
        properties.setProperty("logging.pattern.console", "");
        properties.setProperty("server.port", "190");
        application.setDefaultProperties(properties);
    }

    public void MainProcess() {
        Logger.InitLog();
        Logger.Log_ln("Initializing server...", Logger.Level.INFO, Logger.Type.SYSTEM);
        InitObject();
        Logger.Log_ln("Initializing SQL...", Logger.Level.INFO, Logger.Type.SYSTEM);
        sqlAPI.registrSQL();
        sqlAPI.setDBUrl("jdbc:sqlserver://192.168.0.14;database=API;user=sa;password=LagpixelDB1234;");
        Logger.Log_ln("Loading sensors...", Logger.Level.INFO, Logger.Type.SYSTEM);
        systemapi.loadSensors();
        cpuTelemetry.startMeasure(CPUTelemetry.TYPE.ALL);
        serversthread.start();
        }

    public synchronized void consoleRun() {
        try {
            console.start();
        }catch (Exception e){
            Logger.createDumpCore(e);
        }
    }

}
