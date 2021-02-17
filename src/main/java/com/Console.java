package com;


import com.api.LagpixelAPI08;
import com.sql.API;
import com.sql.SQL;
import util.Color;
import util.Logger;

import java.util.Scanner;

@SuppressWarnings("InfiniteLoopStatement")
public class Console extends Thread{
    final Scanner scanner = new Scanner(System.in);
    final LagpixelAPI lagPixelApi = LagpixelAPI08.getInstance();
    final API sqlAPI = SQL.getInstance();

    @Override
    public void run() {
        while(true) {
            synchronized (this) {
                System.out.print(">");
                String arg = scanner.nextLine().trim().toLowerCase();
                switch (arg) {
                    case "testconn":
                        if (lagPixelApi.testAPIConn())
                            Logger.Log_ln("Connection OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                        else
                            Logger.Log_ln("Connetion Failed", Logger.Level.CRIT, Logger.Type.SYSTEM);
                        break;
                    case "closeapi":
                        lagPixelApi.closeConnectAPI();
                        break;
                    case "playerlist":
                        Logger.Log_ln(lagPixelApi.getPlayerList(), Logger.Level.INFO, Logger.Type.SYSTEM);
                        break;
                    case "connapi":
                        lagPixelApi.connectToAPI();
                        break;
                    case "functest":
                        Tests.testfunction();
                        break;
                    case "debug":
                        if (Logger.getDebug()) {
                            Logger.Log_ln("Debug logging is now off", Logger.Level.INFO, Logger.Type.SYSTEM);
                            Logger.setDebug(false);
                        } else {
                            Logger.Log_ln("Debug logging is now on", Logger.Level.INFO, Logger.Type.SYSTEM);
                            Logger.setDebug(true);
                        }
                        break;
                    case "dbpass":
                        String login;
                        String password;
                        System.out.print("Login: ");
                        login = scanner.nextLine();
                        System.out.print("Password: ");
                        password = scanner.nextLine();
                        if(sqlAPI.checkPassword(login, password)){
                            Logger.Log_ln("Login and password is ok", Logger.Level.INFO, Logger.Type.SYSTEM);
                        }else{
                            Logger.Log_ln("Incorrect login or password", Logger.Level.CRIT, Logger.Type.SYSTEM);
                        }
                }
                    if (arg.equals("sqltest")) {
                    String status;
                    if (sqlAPI.checkDBConn()) {
                        status = Color.Green + "OK" + Color.Reset;
                        boolean isOk = sqlAPI.checkPassword("admin","admin");
                        Logger.Log_ln("SQL check password for admin: " + isOk, Logger.Level.INFO, Logger.Type.SYSTEM);

                    } else {
                        status = Color.Red + "ERROR" + Color.Reset;
                    }
                    Logger.Log_ln("SQL Status: " + status, Logger.Level.INFO, Logger.Type.SYSTEM);
                }
            }
        }
    }
}
