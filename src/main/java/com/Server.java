package com;

import util.Logger;

import java.time.LocalDateTime;

public class Server extends Main {
    public static LocalDateTime startTime = LocalDateTime.now();
    public static final String PROTOCOL_VERSION = "09";

    public static void main(String[] args) {
        try {
            new Main().MainProcess();
        }catch (Exception e){
            Logger.createDumpCore(e);
        }
    }
}

