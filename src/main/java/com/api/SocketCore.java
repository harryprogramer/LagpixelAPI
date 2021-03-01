package com.api;

import util.Color;
import util.GetTime;
import util.Logger;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;


@SuppressWarnings("unused")
class SocketCore {
    private final String startTime = GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS);
    static protected int port = 0;
    static protected String host = null;
    protected static SocketCore getServer;
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter writer;
    private final boolean ConnectBool = true;

    public SocketCore() {
        getServer = this;
    }

    protected static void setPort(int setport){
        Logger.Log_ln("Setting port: " + setport, Logger.Level.DEBUG, Logger.Type.SYSTEM);
        SocketCore.port = setport;
    }

    protected static void setInetAddress(String sethost){
        Logger.Log_ln("Setting ip: " + sethost, Logger.Level.DEBUG, Logger.Type.SYSTEM);
        SocketCore.host = sethost;
    }

    protected static SocketCore getServer() {
        return getServer;
    }

    protected boolean getConnBoolean(){
        if(Objects.isNull(socket)) return false;
        else return (!socket.isConnected());


    }

    protected static String getHost(){
        return SocketCore.host;
    }

    protected static int getPort(){
        return SocketCore.port;
    }


    protected synchronized String sendMessage(String message){
        if(!(Objects.isNull(socket))) {
            try {
                Logger.Log_ln("Processing request", Logger.Level.DEBUG, Logger.Type.SYSTEM);
                socket.setSoTimeout(5000);
                Logger.Log_ln("Sending message > " + message, Logger.Level.DEBUG, Logger.Type.SYSTEM);
                writer.println(message);
                    try {
                        String data = in.readLine();
                        if(data == null){
                            Logger.Log_ln("Lost connection with API", Logger.Level.CRIT, Logger.Type.SYSTEM);
                            if(socket == null) {
                                connectToAPI();
                            }
                            return null;
                        }else{
                            Logger.Log_ln("Recived message > " + data, Logger.Level.DEBUG, Logger.Type.PAPER);
                            return data;
                        }
                    } catch (SocketTimeoutException ignored) {
                        Logger.Log_ln("Paper API timeout", Logger.Level.CRIT, Logger.Type.PAPER);
                        return "&timeout";
                    } catch (Exception ignored) {
                        return null;
                    }
            } catch (IOException ignored) {
                return null;
            }
        }else {
            Logger.Log_ln("No connection with API", Logger.Level.CRIT, Logger.Type.SYSTEM);
            return null;
        }
    }
    protected synchronized void connectToAPI() {
                synchronized (this) {
                    while (true) {
                        try {
                            if (!(port == 0) && !(host == null)) {
                                Logger.Log_ln("Connecting to API...", Logger.Level.WARN, Logger.Type.PAPER);
                                int i;
                                int left = 0;
                                for (i = 0; i < 6; i++) {
                                    String reason;
                                    String exception = null;
                                    try {
                                        left = 5 - i;
                                        reason = Color.Green + "Checking..." + Color.Reset;
                                        Logger.Log_nt("Attempt: [" + i + "], left: [" + left + "] Status: [" + reason + "], Type: CTRL + S to skip ", Logger.Level.DEBUG, Logger.Type.PAPER);
                                        socket = new Socket(host, port);
                                        try {
                                            this.wait(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    } catch (ConnectException e) {
                                        exception = e.getMessage();
                                    } finally {
                                        reason = Color.Green + "Checking..." + Color.Reset;
                                        if (i == 5) {
                                            reason = Color.Red + exception + Color.Reset;
                                        }
                                        Logger.Log_nt("Attempt: [" + i + "], left: [" + left + "] Status: [" + reason + "]", Logger.Level.DEBUG, Logger.Type.PAPER);
                                    }
                                    if (i == 5) {
                                        Logger.Log_nt("Failed to connect to API, cause: [" + Color.Red + exception + Color.Reset + "]" +
                                                " at " + GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS)
                                                + "\n", Logger.Level.CRIT, Logger.Type.SYSTEM);
                                        this.wait(10000);
                                        break;
                                    }
                                }
                                if (socket == null) continue;

                                Logger.Log_ln("Connected, API is now available", Logger.Level.INFO, Logger.Type.PAPER);
                                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                writer = new PrintWriter(socket.getOutputStream(), true);
                            } else {
                                Logger.Log_ln("attempted to run server without setting port or host", Logger.Level.CRIT, Logger.Type.SYSTEM);
                            }
                            break;
                        } catch (IOException e) {
                            Logger.Log_ln("Paper server is not responding, trying again...", Logger.Level.WARN, Logger.Type.PAPER);
                            try {
                                this.wait(10000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            getServer = null;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }


    protected synchronized void closeConn(){
        if(!(socket.isClosed())){
            try {
                socket.close();
                Logger.Log_ln("Zamknięto połączenie", Logger.Level.INFO, Logger.Type.PAPER);
            } catch (Exception e) {
                Logger.Log_ln("Nie udało się zamknąć połączenia z powodu " + e.getMessage(), Logger.Level.WARN, Logger.Type.PAPER);
            }
        }else {
            Logger.Log_ln("Serwer nie jest połączony z API", Logger.Level.INFO, Logger.Type.SYSTEM);
        }
    }

}
