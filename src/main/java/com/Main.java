package com;

import com.api.LagpixelAPI08;
import com.sql.API;
import com.sql.SQL;
import util.Logger;

public class Main {
    LagpixelAPI lagPixelApi;
    Console console;
    HTTPCore httpCore;
    API sqlAPI;

    public void InitObject(){
        Logger.setDebug(true);
        console = new Console();
        lagPixelApi = LagpixelAPI08.getInstance();
        sqlAPI = SQL.getInstance();
        httpCore = new HTTPCore(190);
        lagPixelApi.setPort(2800);
        lagPixelApi.setInetAddress("localhost");
    }

    public void MainProcess(){
        Logger.Log_ln("Initializing server...", Logger.Level.INFO, Logger.Type.SYSTEM);
        InitObject();
        Logger.Log_ln("Starting HTTP...", Logger.Level.INFO, Logger.Type.SYSTEM);
        httpCore.startHTTP();
        lagPixelApi.connectToAPI();
        Logger.Log_ln("Initializing SQL...", Logger.Level.INFO, Logger.Type.SYSTEM);
        sqlAPI.setDBUrl("jdbc:sqlserver://192.168.0.14;user=sa;password=LagpixelDB1234");
        sqlAPI.registrSQL();
        Logger.Log_ln("Starting console...", Logger.Level.INFO, Logger.Type.SYSTEM);
        console.start();
    }

}
