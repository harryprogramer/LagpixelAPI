package com;

import util.Logger;

import java.time.LocalDateTime;

public class Server {
    public static LocalDateTime startTime = LocalDateTime.now();
    public static final String PROTOCOL_VERSION = "08";

    public static void main(String[] args) {
        try {
            new Main().MainProcess();
        }catch (Exception e){
            Logger.Log_ln("Shutting down...", Logger.Level.INFO, Logger.Type.SYSTEM);
            Logger.createDumpCore(e);
        }
    }
}

