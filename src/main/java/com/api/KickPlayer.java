package com.api;

import org.json.JSONObject;

class KickPlayer {
    static SocketCore getServer = SocketCore.getServer();
    public static String kick(String player, String kickReason){
        JSONObject responseJSON;
        String message = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("7", new JSONObject().put("player", player).put("message", kickReason)));
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
}
