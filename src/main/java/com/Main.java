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
    Telemetry telemetry;


    private void InitObject(){
        Logger.setDebug(false);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.Log_ln("Shutting down...", Logger.Level.INFO, Logger.Type.SYSTEM);
            lagPixelApi.closeConnectAPI();
            application = null;
        }));
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

            synchronized void runTempTelemetry(){
                Logger.Log_ln("Starting system telemetry", Logger.Level.INFO, Logger.Type.SYSTEM);
                telemetry.startMeasure(Telemetry.TYPE.ALL);
                try {
                    this.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(telemetry.getTemp() != 0) {
                    Logger.Log_ln("Telemetry OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                }else{
                    Logger.Log_ln("Telemetry Failed", Logger.Level.INFO, Logger.Type.SYSTEM);
                }
            }

            @Override
            public void run() {
                runHTTP();
                runAPISocket();
                runTempTelemetry();
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
        telemetry = Telemetry.getInstance();
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
        Logger.Log_ln("Done, detected e.g processor: " + systemapi.getCPUName(), Logger.Level.INFO, Logger.Type.SYSTEM);
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
