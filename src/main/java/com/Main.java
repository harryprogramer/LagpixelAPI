package com;

import com.api.LagpixelAPI08;
import com.http.HTTPCore;
import com.sql.API;
import com.sql.SQL;
import org.springframework.boot.SpringApplication;
import util.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.Scanner;
public class Main {
    LagpixelAPI lagPixelApi;
    Console console;
    API sqlAPI;
    SpringApplication application;
    Thread serversthread;


    private void InitObject(){
        Logger.setDebug(false);
        serversthread = new Thread(){
            synchronized void runHTTP(){
                Logger.Log_ln("Starting HTTP...", Logger.Level.INFO, Logger.Type.HTTP);
                application.run();
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
                try {
                    consoleRun();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        console = new Console();
        lagPixelApi = LagpixelAPI08.getInstance();
        sqlAPI = SQL.getInstance();
        lagPixelApi.setPort(2800);
        lagPixelApi.setInetAddress("localhost");
        application = new SpringApplication(HTTPCore.class);
        Properties properties = new Properties();
        properties.setProperty("spring.main.banner-mode", "off");
        properties.setProperty("logging.pattern.console", "");
        properties.setProperty("server.port", "80");
        application.setDefaultProperties(properties);
    }

    public void MainProcess(){
        Logger.InitLog();
        Logger.Log_ln("Initializing server...", Logger.Level.INFO, Logger.Type.SYSTEM);
        InitObject();
        serversthread.start();
        Logger.Log_ln("Initializing SQL...", Logger.Level.INFO, Logger.Type.SYSTEM);
        sqlAPI.setDBUrl("jdbc:sqlserver://192.168.0.14;database=API;user=sa;password=LagpixelDB1234;");
        sqlAPI.registrSQL();
    }

    public synchronized void consoleRun() throws InterruptedException {
        console.start();
    }

}
