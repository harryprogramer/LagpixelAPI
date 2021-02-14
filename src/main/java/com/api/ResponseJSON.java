package com.api;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import util.GetTime;

public class ResponseJSON {
    public static String OKResponseToClient(@Nullable JSONObject body){
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", "OK");
        responseJSON.put("time", GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS));
        responseJSON.put("body", body);
        return responseJSON.toString();
    }

    public static String ERRORResponseToClientAPI(String errcode, String reason, String functionCode){
        JSONObject responseJSON = new JSONObject();
        errcode = "1x" + functionCode + "e"  + errcode;
        responseJSON.put("status", "ERROR");
        responseJSON.put("time", GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS));
        responseJSON.put("reason", reason);
        responseJSON.put("errorcode", errcode);
        return responseJSON.toString();
    }

    public static String ERRORResponseToClientPaper(String errcode, String reason, String functionCode){
        JSONObject responseJSON = new JSONObject();
        errcode = "0x" + functionCode + "e"  + errcode;
        responseJSON.put("status", "ERROR");
        responseJSON.put("time", GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS));
        responseJSON.put("reason", reason);
        responseJSON.put("errorcode", errcode);
        return responseJSON.toString();
    }


    public static String SendQuestionToPaper(@NotNull String code, @Nullable JSONObject bodyJSON){
        JSONObject questionJSON = new JSONObject();
        questionJSON.put("code", code);
        questionJSON.put("body", (bodyJSON == null) ? new JSONObject() : bodyJSON);
        return questionJSON.toString();
    }
}
