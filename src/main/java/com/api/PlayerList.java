package com.api;


import org.json.JSONArray;
import org.json.JSONObject;

public class PlayerList{
    static SocketCore server = SocketCore.getServer();
    public static String getPlayerArray(){
        String data = server.sendMessage(ResponseJSON.SendQuestionToPaper("5", null));
        if(data == null){
            return ResponseJSON.ERRORResponseToClientAPI("2", "no connection with api", "socket");
        }else{
            if(data.equals("&timeout")){
                return ResponseJSON.ERRORResponseToClientPaper("5", "paper timeout", "5");
            }
        }

        JSONObject recvJSON = new JSONObject(data);
        JSONObject bodyrecvJSON;
        JSONArray playerArray;
        String status = recvJSON.getString("status").toUpperCase();
        if(status.equals("OK")) {
            bodyrecvJSON = recvJSON.getJSONObject("body");
            playerArray = bodyrecvJSON.getJSONArray("players");
            return ResponseJSON.OKResponseToClient(new JSONObject().put("players", playerArray));
        }else if(status.equals("ERROR")){
            String errorcode = recvJSON.getString("errorCode");
            String reason = recvJSON.getString("cause");
            return ResponseJSON.ERRORResponseToClientPaper(errorcode, reason, "6");
        }else{
            return ResponseJSON.ERRORResponseToClientAPI("e1", "unknown api error", "6");
        }
    }
}
