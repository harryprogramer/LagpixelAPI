package com.http;

import com.Telemetry;
import com.LagpixelAPI;
import com.api.LagpixelAPI08;
import com.api.ResponseJSON;
import com.api.sensors.SystemInfo;
import com.api.sensors.SystemInfoAPI;
import com.sql.API;
import com.sql.SQL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Parser {
    static LagpixelAPI lagpixelAPI = LagpixelAPI08.getInstance();
    static API sqlapi = SQL.getInstance();
    static SystemInfo systemInfo = SystemInfoAPI.getAPI();
    protected static String idParser(JSONObject jsonObject){
        String login, password;
        try{
            login = jsonObject.getJSONObject("auth").getString("login");
            password = jsonObject.getJSONObject("auth").getString("password");
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientAPI("9", "invalid auth keys", "auth");
        }

        if(!(sqlapi.checkPassword(login, password))){
            return ResponseJSON.ERRORResponseToClientAPI("10", "invalid auth data", "auth");
        }

        JSONObject bodyJSON;
        int id;

        try {
            id = Integer.parseInt(jsonObject.getString("id"));
            bodyJSON = jsonObject.getJSONObject("body");
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientAPI("8", "invalid id packet [" + e.getMessage() + "]", "parser");
        }

        switch (id){
            case 5: {
                return lagpixelAPI.getPlayerList();
            }
            case 6: {
                String player, reason, expires;
                try {
                    player = bodyJSON.getString("player");
                    reason = bodyJSON.getString("reason");
                    expires = bodyJSON.getString("expires");
                } catch (Exception e) {
                    return ResponseJSON.ERRORResponseToClientAPI("9", "incorrect player, reason or body key [" + e.getMessage() + "]", "parser");
                }
                return lagpixelAPI.banPlayer(player, reason, expires);
            }
            case 7: {
                String player, message, fromUser;
                try{
                    player = bodyJSON.getString("player");
                    message = bodyJSON.getString("message");
                    fromUser = bodyJSON.getString("user");
                }catch (Exception e){
                    return ResponseJSON.ERRORResponseToClientAPI("9", "invalid player or message body key [" + e + "]", "parser");
                }
                return lagpixelAPI.kickPlayer(player, message, fromUser);
            }

            case 8: {
                return lagpixelAPI.getTPS();
            }

            case 9: {
                return lagpixelAPI.whitelistON();
            }

            case 10: {
                return lagpixelAPI.whitelistOFF();
            }

            case 11: {
                String player;
                try{
                    player = bodyJSON.getString("player");
                }catch (Exception e){
                    return ResponseJSON.ERRORResponseToClientAPI("9", "invalid player key [" + e.getMessage() + "]", "parser");
                }
                return lagpixelAPI.removeFromWhitelist(player);
            }

            case 12: {
                String player;
                try{
                    player = bodyJSON.getString("player");
                }catch (Exception e){
                    return ResponseJSON.ERRORResponseToClientAPI("9", "invalid player key [" + e.getMessage() + "]", "parser");
                }
                return lagpixelAPI.addToWhitelist(player);
            }

            case 14: {
                String player, message;
                try{
                    player = bodyJSON.getString("player");
                    message = bodyJSON.getString("message");
                }catch (Exception e){
                    return ResponseJSON.ERRORResponseToClientAPI("9", "invalid player or message key [" + e.getMessage() + "]", "parser");
                }
                return lagpixelAPI.BroadcastMessage(message, player);
            }

            case 15: {
                return lagpixelAPI.whitelistStatus();
            }

            case 16: {
                return lagpixelAPI.whitelistListPlayer();
            }

            case 17: {
                String player;
                try{
                    player = bodyJSON.getString("player");
                }catch (Exception e){
                    return ResponseJSON.ERRORResponseToClientAPI("9", "invalid player key [" + e.getMessage() + "]", "parser");
                }
                return lagpixelAPI.checkIfPlayerWhitelist(player);
            }

            case 18: {
                return ResponseJSON.OKResponseToClient(null);
            }

            case 19: {
                JSONObject responseJSON = new JSONObject();
                JSONObject cpuobject = new JSONObject();
                JSONObject memoryobject = new JSONObject();
                JSONArray temparray = new JSONArray();
                double[] temp = Telemetry.getInstance().getDayTemp();
                for(double temps : temp){
                    temparray.put(temps);
                }
                // obiekt odpowiedzi
                responseJSON.put("cpu", cpuobject);
                responseJSON.put("memory", memoryobject);
                // obiekt cpu
                cpuobject.put("bufftemp", temparray);
                cpuobject.put("temp", Telemetry.getInstance().getTemp());
                cpuobject.put("measure_time", new JSONArray().put(Telemetry.getInstance().getBuffTempFirstIndexTime())
                        .put(Telemetry.getInstance().getLastTempUpdate()));
                //obiekt memory
                memoryobject.put("total", systemInfo.getTotalMemory());
                memoryobject.put("free", systemInfo.getFreeMemory());
                memoryobject.put("using", systemInfo.getUsedMemory());

                return ResponseJSON.OKResponseToClient(new JSONObject().put("data", responseJSON));
            }
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad id packet");
        }
    }
}
