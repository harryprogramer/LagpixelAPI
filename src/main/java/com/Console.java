package com;


import com.api.LagpixelAPI08;
import com.sql.API;
import com.sql.SQL;
import util.Color;
import util.Logger;

import java.util.Scanner;

public class Console extends Thread{
    Scanner scanner = new Scanner(System.in);
    LagpixelAPI lagPixelApi = LagpixelAPI08.getInstance();
    API sqlAPI = SQL.getInstance();

    @Override
    public void run() {
        while(true) {
            synchronized (this) {
                System.out.print(">");
                String arg = scanner.nextLine().trim().toLowerCase();
                if (arg.equals("testconn")) {
                    if (lagPixelApi.testAPIConn())
                        Logger.Log_ln("Connection OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                    else
                        Logger.Log_ln("Connetion Failed", Logger.Level.CRIT, Logger.Type.SYSTEM);
                } else if (arg.equals("closeapi")) {
                    lagPixelApi.closeConnectAPI();
                } else if (arg.equals("playerlist")) {
                    Logger.Log_ln(lagPixelApi.getPlayerList(), Logger.Level.INFO, Logger.Type.SYSTEM);
                } else if (arg.equals("connapi")) {
                    lagPixelApi.connectToAPI();
                } else if (arg.equals("sqltest")) {
                    String status;
                    if (sqlAPI.checkDBConn()) {
                        status = Color.Green + "OK" + Color.Reset;
                    } else {
                        status = Color.Red + "ERROR" + Color.Reset;
                    }
                    Logger.Log_ln("SQL Status: " + status, Logger.Level.INFO, Logger.Type.SYSTEM);
                } else {
                    Logger.Log_ln("Unknown command", Logger.Level.CRIT, Logger.Type.SYSTEM);
                }
            }
        }
    }
}
