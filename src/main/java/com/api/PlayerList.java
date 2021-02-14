package com.api;


import org.json.JSONArray;
import org.json.JSONObject;

public class PlayerList{
    static SocketCore server = SocketCore.getServer();
    public static String JSON(){
        String data = server.sendMessage(ResponseJSON.SendQuestionToPaper("5", null));
        JSONObject recvJSON = new JSONObject(data);
        JSONArray playerArray;
        String status = recvJSON.getString("status").toUpperCase();
        if(status.equals("OK")) {
            playerArray = recvJSON.getJSONArray("players");
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
