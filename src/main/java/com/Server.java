package com;

import java.time.LocalDateTime;

public class Server {
    static LocalDateTime startTime = LocalDateTime.now();
    public static final String PROTOCOL_VERSION = "08";

    public static void main(String[] args) {
        new Main().MainProcess();
    }
}
