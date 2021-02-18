package com.http;

import com.LagpixelAPI;
import com.api.LagpixelAPI08;
import com.api.ResponseJSON;
import com.sql.API;
import com.sql.SQL;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Parser {
    static LagpixelAPI lagpixelAPI = LagpixelAPI08.getInstance();
    static API sqlapi = SQL.getInstance();
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
            id = Integer.parseInt(jsonObject.getString("code"));
            bodyJSON = jsonObject.getJSONObject("body");
        }catch (Exception e){
            return ResponseJSON.ERRORResponseToClientAPI("8", "invalid id code or body object [" + e.getMessage() + "]", "parser");
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
                String player, message;
                try{
                    player = bodyJSON.getString("player");
                    message = bodyJSON.getString("message");
                }catch (Exception e){
                    return ResponseJSON.ERRORResponseToClientAPI("9", "invalid player or message body key [" + e + "]", "parser");
                }
                return lagpixelAPI.kickPlayer(player, message);
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

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "json structure not valid");
        }
    }
}
