package com.api;

import org.json.JSONArray;
import org.json.JSONObject;

class GetTPS {
    static SocketCore getServer = SocketCore.getServer();
    public static String getTPS(){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("8", null));
        try{
            responseJSON = new JSONObject(message);
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
        }
        if(responseJSON.getString("status").equalsIgnoreCase("OK")){
            JSONArray tps;
            try{
                tps = responseJSON.getJSONObject("body").getJSONArray("tps");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientPaper("3", "incorrect paper response", "6");
            }
            return ResponseJSON.OKResponseToClient(new JSONObject().put("value", tps));
        }else{
            String errcode;
            String errorreason;
            try{
                errcode = responseJSON.getString("errorCode");
                errorreason = responseJSON.getString("reason");
            }catch (Exception ignored){
                return ResponseJSON.ERRORResponseToClientAPI("4", "paper and response structure error", null);
            }
            return ResponseJSON.ERRORResponseToClientPaper(errcode, errorreason, "6");
        }

    }
}
