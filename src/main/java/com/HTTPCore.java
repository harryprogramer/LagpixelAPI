package com;

import com.handle.Api;
import com.handle.GenerateToken;
import com.handle.TestConn;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import util.Logger;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class HTTPCore {
    protected HttpServer server = null;

    public HTTPCore(int port){
        try {
            server = HttpServer.create(new InetSocketAddress(port), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startHTTP(){
        try {
            server.setExecutor(null); // creates a default executor
            server.start();
            server.createContext("/api/testConn", new TestConn());
            server.createContext("/api", new Api());
            server.createContext("/api/gentoken", new GenerateToken());
            Logger.Log_ln("HTTP started", Logger.Level.INFO, Logger.Type.HTTP);

        } catch (Exception exception) {
            Logger.Log_ln("Failed to create HTTP server, cause [" + exception.getMessage() + "]", Logger.Level.CRIT, Logger.Type.HTTP);
            exception.printStackTrace();

        }
    }
}
