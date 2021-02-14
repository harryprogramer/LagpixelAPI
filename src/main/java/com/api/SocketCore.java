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
    protected static int port = 0x0;
    protected static String host = null;
    protected static SocketCore getServer;
    public Socket socket;
    private BufferedReader in;
    private PrintWriter writer;
    private boolean ConnectBool = true;

    protected static void setPort(int port){
        SocketCore.port = port;
    }

    protected static void setInetAddress(String host){
        SocketCore.host = host;
    }

    protected static SocketCore getServer(){
        return getServer;
    }

    protected String sendMessage(String message){
        Logger.Log_ln("Try to send message > " + message, Logger.Level.DEBUG, Logger.Type.SYSTEM);
        if(!(Objects.isNull(socket))) {
            try {
                socket.setSoTimeout(5000);
                writer.println(message);
                while (true) {
                    try {
                        String data = in.readLine();
                        Logger.Log_ln("Recived message" + data, Logger.Level.DEBUG, Logger.Type.PAPER);
                        return data;
                    } catch (SocketTimeoutException ignored) {
                        return "";
                    } catch (Exception ignored) {
                        return null;
                    }
                }
            } catch (IOException ignored) {
                return null;
            }
        }else
            throw new IllegalStateException("socket is not initialized");
    }

    protected void connectToAPI() {
        synchronized (this) {
            while (true) {
                try {
                    if (!(port == 0x0) && !(host == null)) {
                        Logger.Log_ln("Connecting to API...", Logger.Level.WARN, Logger.Type.PAPER);
                        int i;
                        int left = 0;
                        for(i = 0; i < 6; i++) {
                            String reason;
                            String exception = null;
                            try {
                                left = 5 - i;
                                reason = Color.Green + "Checking..." + Color.Reset;
                                Logger.Log_nt("Attempt: [" + i + "], left: [" + left + "] Status: [" + reason + "]", Logger.Level.DEBUG, Logger.Type.PAPER);
                                socket = new Socket(host, port);
                                try {
                                    this.wait(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }catch (ConnectException e){exception = e.getMessage();}
                            finally {
                                reason = Color.Green + "Checking..." + Color.Reset;
                                if(i == 5){
                                    reason = Color.Red + exception + Color.Reset;
                                }
                                Logger.Log_nt("Attempt: [" + i + "], left: [" + left + "] Status: [" + reason + "]", Logger.Level.DEBUG, Logger.Type.PAPER);
                            }
                            if(i == 5){
                                Logger.Log_nt("Failed to connect to API, cause: [" +  Color.Red + exception + Color.Reset + "]" +
                                        " at " + GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS)
                                        +  "\n", Logger.Level.CRIT, Logger.Type.SYSTEM);
                                this.wait(10000);
                                break;
                            }
                        }
                        if(socket == null) continue;

                        Logger.Log_ln("Connected, API is now available", Logger.Level.INFO, Logger.Type.PAPER);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer = new PrintWriter(socket.getOutputStream(), true);
                        getServer = this;
                        break;
                    } else throw new IllegalStateException("port or inet address is not initialized");
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

    protected void closeConn(){
        if(!(socket.isClosed())){
            try {
                socket.close();
                Logger.Log_ln("Zamknięto połączenie", Logger.Level.INFO, Logger.Type.PAPER);
            } catch (IOException e) {
                Logger.Log_ln("Nie udało się zamknąć połączenia z powodu " + e.getMessage(), Logger.Level.WARN, Logger.Type.PAPER);
            }
        }
    }

}
