package com.handle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.GetTime;

import java.io.IOException;
import java.io.OutputStream;

/*
* HTTPS
* GET api.lagpixel.pl/api/testconn
* JSON:
* password
* user
* //TODO token_access
* */

public class TestConn implements HttpHandler {

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "{\"status\":\"ok\", \"body\":{\"server-time\":\"" + GetTime.getTimeString() + "\"}}";
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}