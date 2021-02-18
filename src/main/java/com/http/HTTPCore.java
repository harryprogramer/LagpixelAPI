package com.http;


import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import util.Logger;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class HTTPCore {

    @GetMapping("/error")
    public String error(@RequestParam(value = "name", defaultValue = "World") String name, HttpEntity<String> httpEntity, HttpServletRequest request) {
        return "error test";
    }

    @PostMapping(path = "/api", produces= MediaType.APPLICATION_JSON_VALUE)
    public String api(@RequestParam(value = "name", defaultValue = "World") String name, HttpEntity<String> httpEntity, HttpServletRequest request) {
        JSONObject recvJSON;
        String json = httpEntity.getBody();
        if(json == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "json structure not valid");
        }
        try{
            recvJSON = new JSONObject(json);
        }catch (Exception ignored){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "json structure not valid");
        }
        Logger.Log_ln("Request from " + request.getRemoteHost(), Logger.Level.DEBUG, Logger.Type.HTTP);
        return Parser.idParser(recvJSON);
    }
}
