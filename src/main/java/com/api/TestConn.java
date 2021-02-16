package com.api;

import org.json.JSONObject;
import util.Logger;

class TestConn {
    static SocketCore getServer = SocketCore.getServer();
    protected static boolean testAPI(){
        JSONObject response;
        String status;
        String messageresponse = getServer.sendMessage(ResponseJSON.SendQuestionToPaper("0", null));
        if(messageresponse != null && !messageresponse.equals("")){
            if(messageresponse.equals("&timeout")){
                return false;
            }
            response = new JSONObject(messageresponse);
            status = response.getString("status");
            Logger.Log_ln("Status : " + status, Logger.Level.DEBUG, Logger.Type.PAPER);
            if(status.equals("OK"))
                 return true;
            else if(status.equals("ERROR"))
                return false;
            else
                return false;
        } else {
            return false;
        }
    }
}
