package com;


import com.api.LagpixelAPI08;
import com.api.sensors.SystemInfo;
import com.api.sensors.SystemInfoAPI;
import com.sql.API;
import com.sql.SQL;
import util.Color;
import util.Logger;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("InfiniteLoopStatement")
public class Console extends Thread{
    final LagpixelAPI lagPixelApi = LagpixelAPI08.getInstance();
    final API sqlAPI = SQL.getInstance();
    final Tests tests = new Tests();
    final Scanner authorizationInput = new Scanner(System.in);
    SystemInfo systemInfo = SystemInfoAPI.getAPI();
    Telemetry telemetry = Telemetry.getInstance();
    AtomicBoolean consoleBoolean = new AtomicBoolean(true);

    @Override
    public void run() {
        while (true) {
            int i = 0;
            int timeout;
            synchronized (this) {
                while (true) {
                    String login;
                    String password;
                    System.out.print(Color.Green + "Type login: " + Color.Reset);
                    login = authorizationInput.nextLine().trim();
                    System.out.print(Color.Green + "Type password: " + Color.Reset);
                    password = authorizationInput.nextLine().trim();
                    Logger.Log_ln("Authorizing...", Logger.Level.INFO, Logger.Type.SYSTEM);
                    if(sqlAPI.checkDBConn()) {
                        if (sqlAPI.checkPassword(login, password)) {
                            Logger.Log_ln("OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                            consoleBoolean.set(true);
                            break;
                        } else {
                            Logger.Log_ln("Incorrect password, or login", Logger.Level.WARN, Logger.Type.SYSTEM);
                            i++;
                        }
                    }else {
                        Logger.Log_ln("Database is not responding", Logger.Level.WARN, Logger.Type.SYSTEM);
                    }
                    if (i > 2) {
                        timeout = 10000 * i;
                        Logger.Log_ln("Too many incorrect probes", Logger.Level.INFO, Logger.Type.SYSTEM);
                        try {
                            this.wait(timeout);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Logger.Log_ln("Starting console...", Logger.Level.INFO, Logger.Type.SYSTEM);
                String arg;
                while (consoleBoolean.get()) {
                    synchronized (this) {
                        Scanner scanner = new Scanner(System.in);
                        try {
                            arg = scanner.nextLine().trim().toLowerCase();
                            Logger.Log_ln("Used command: " + arg, Logger.Level.DEBUG, Logger.Type.SYSTEM);
                            switch (arg) {
                                case "testconn": {
                                    if (lagPixelApi.testAPIConn())
                                        Logger.Log_ln("Connection OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                                    else
                                        Logger.Log_ln("Connetion Failed", Logger.Level.CRIT, Logger.Type.SYSTEM);
                                    break;
                                }
                                case "closeapi": {
                                    lagPixelApi.closeConnectAPI();
                                    break;
                                }
                                case "playerlist": {
                                    Logger.Log_ln(lagPixelApi.getPlayerList(), Logger.Level.INFO, Logger.Type.SYSTEM);
                                    break;
                                }
                                case "connapi": {
                                    lagPixelApi.connectToAPI();
                                    break;
                                }
                                case "functest": {
                                    tests.testfunction();
                                    break;
                                }
                                case "debug": {
                                    if (Logger.getDebug()) {
                                        Logger.Log_ln("Debug logging is now off", Logger.Level.INFO, Logger.Type.SYSTEM);
                                        Logger.setDebug(false);
                                    } else {
                                        Logger.Log_ln("Debug logging is now on", Logger.Level.INFO, Logger.Type.SYSTEM);
                                        Logger.setDebug(true);
                                    }
                                    break;
                                }
                                case "dbpass": {
                                    String login;
                                    String password;
                                    System.out.print("Login: ");
                                    login = scanner.nextLine();
                                    System.out.print("Password: ");
                                    password = scanner.nextLine();
                                    if (sqlAPI.checkPassword(login, password)) {
                                        Logger.Log_ln("Login and password is ok", Logger.Level.INFO, Logger.Type.SYSTEM);
                                    } else {
                                        Logger.Log_ln("Incorrect login or password", Logger.Level.CRIT, Logger.Type.SYSTEM);
                                    }
                                }

                                case "logout": {
                                    Logger.Log_ln("Logging out...", Logger.Level.INFO, Logger.Type.SYSTEM);
                                    consoleBoolean.set(false);
                                }

                                case "cputemp": {
                                    Logger.Log_ln(Integer.toString(telemetry.getTemp()), Logger.Level.INFO, Logger.Type.SYSTEM);
                                }

                                case "dailytemp": {
                                    Logger.Log_ln(Arrays.toString(telemetry.getDayTemp()), Logger.Level.INFO, Logger.Type.SYSTEM);
                                }

                                case "cpu": {
                                }

                                case "speed": {
                                    Logger.Log_ln("Executing...", Logger.Level.INFO, Logger.Type.SYSTEM);
                                    double ping = systemInfo.getInternetPing();
                                    Logger.Log_ln("Final return: " + ping, Logger.Level.INFO, Logger.Type.SYSTEM);
                                }
                            }
                            if (arg.equals("sqltest")) {
                                String status;
                                if (sqlAPI.checkDBConn()) {
                                    status = Color.Green + "OK" + Color.Reset;
                                    boolean isOk = sqlAPI.checkPassword("admin", "admin");
                                    Logger.Log_ln("SQL check password for admin: " + isOk, Logger.Level.INFO, Logger.Type.SYSTEM);

                                } else {
                                    status = Color.Red + "ERROR" + Color.Reset;
                                }
                                Logger.Log_ln("SQL Status: " + status, Logger.Level.INFO, Logger.Type.SYSTEM);
                            }
                        }catch (Exception ignored){
                        }
                    }
                }
            }
        }
    }
}
