package com;

import com.handle.TestConn;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class HTTPCore {
    /*
    * @param port
    * */
    protected HttpServer server = null;

    public HTTPCore(int port){
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startHTTP(){
        try {
            server.createContext("/test", new TestConn());
            server.setExecutor(null); // creates a default executor
            server.start();
            server.createContext("/api/testConn", new TestConn());

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP server");
            exception.printStackTrace();

        }
    }
}
