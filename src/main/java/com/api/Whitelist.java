package com.api;

import org.json.JSONArray;
import org.json.JSONObject;

class Whitelist {
    static SocketCore getServer = SocketCore.getServer();
    public static String whitelistON(){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("9", null));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            return ResponseJSON.OKResponseToClient(null);
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }

    }
    public static String whitelistOFF(){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("10", null));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            return ResponseJSON.OKResponseToClient(null);
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }

    }

    public static String removeFromWhitelist(String player){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("11", new JSONObject().put("player", player)));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            return ResponseJSON.OKResponseToClient(null);
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }
    }

    public static String addWhitelist(String player){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("12", new JSONObject().put("player", player)));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            return ResponseJSON.OKResponseToClient(null);
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }

    }

    public static String whitelistBool(){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("15", null));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            boolean whitelistStatus;
            try{
                whitelistStatus = responseJSON.getJSONObject("body").getBoolean("bool");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "15");
            }
            return ResponseJSON.OKResponseToClient(new JSONObject().put("bool", whitelistStatus));
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }
    }

    public static String whitelistList(){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("16", null));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            JSONArray list;
            try{
                list = responseJSON.getJSONObject("body").getJSONArray("list");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "16");
            }
            return ResponseJSON.OKResponseToClient(new JSONObject().put("list", list));
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }
    }

    public static String checkPlayerWhitelist(String player){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("16", null));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            boolean playerBool;
            try{
                playerBool = responseJSON.getBoolean("bool");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "16");
            }
            return ResponseJSON.OKResponseToClient(new JSONObject().put("bool", playerBool));
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }
    }

}
