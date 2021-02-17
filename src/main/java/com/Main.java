package com;

import com.api.LagpixelAPI08;
import com.sql.API;
import com.sql.SQL;
import util.Color;
import util.Logger;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    LagpixelAPI lagPixelApi;
    Console console;
    HTTPCore httpCore;
    API sqlAPI;
    Scanner authorizationInput;

    public void InitObject(){
        Logger.setDebug(true);
        console = new Console();
        authorizationInput = new Scanner(System.in);
        lagPixelApi = LagpixelAPI08.getInstance();
        sqlAPI = SQL.getInstance();
        httpCore = new HTTPCore(190);
        lagPixelApi.setPort(2800);
        lagPixelApi.setInetAddress("localhost");
    }

    public void MainProcess() throws InterruptedException {
        int i = 0;
        int timeout;
        Logger.Log_ln("Initializing server...", Logger.Level.INFO, Logger.Type.SYSTEM);
        InitObject();
        Logger.Log_ln("Starting HTTP...", Logger.Level.INFO, Logger.Type.SYSTEM);
        httpCore.startHTTP();
        lagPixelApi.connectToAPI();
        Logger.Log_ln("Initializing SQL...", Logger.Level.INFO, Logger.Type.SYSTEM);
        sqlAPI.setDBUrl("jdbc:sqlserver://192.168.0.14;database=API;user=sa;password=LagpixelDB1234;");
        sqlAPI.registrSQL();
        Logger.Log_ln("Initializing console...", Logger.Level.INFO, Logger.Type.SYSTEM);
        synchronized (this) {
            while (true) {
                String login;
                String password;
                System.out.print(Color.Green + "Login: " + Color.Reset);
                login = authorizationInput.nextLine().trim();
                System.out.print(Color.Green + "Password: " + Color.Reset);
                password = authorizationInput.nextLine().trim();
                Logger.Log_ln("Authorizing...", Logger.Level.INFO, Logger.Type.SYSTEM);
                if (sqlAPI.checkPassword(login, password)) {
                    Logger.Log_ln("OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                    break;
                } else {
                    Logger.Log_ln("Incorrect password, or login", Logger.Level.WARN, Logger.Type.SYSTEM);
                    i++;
                }
                if (i > 2) {
                    timeout = 10000 * i;
                    Logger.Log_ln("Too many incorrect probes", Logger.Level.INFO, Logger.Type.SYSTEM);
                    this.wait(timeout);
                }
            }
            console.start();
        }
    }

}
