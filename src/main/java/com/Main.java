package com;

import com.api.LagpixelAPI08;
import util.Logger;

public class Main {
    LagpixelAPI lagPixelApi;
    Console console;
    HTTPCore httpCore;


    public void InitObject(){
        Logger.setDebug(true);
        console = new Console();
        lagPixelApi = LagpixelAPI08.getInstance();
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
        Logger.Log_ln("Starting console...", Logger.Level.INFO, Logger.Type.SYSTEM);
        console.start();
    }
}
