package com;

import com.api.BanPlayer;
import com.api.PlayerList;
import org.json.JSONObject;
import util.Color;
import util.Logger;

public class Tests {

    protected static void testfunction(){
        boolean[] booleantest = new boolean[] {false, false};
        boolean testScore = true;
        JSONObject testsJSON;
        final String testPlayer = "cotusierobimany";
        Logger.Log_ln("Testing list player...", Logger.Level.INFO, Logger.Type.SYSTEM);
        testsJSON = new JSONObject(PlayerList.getPlayerArray());
        if(testsJSON.getString("status").equalsIgnoreCase("ok")){
            Logger.Log_ln("Player List, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
            booleantest[0] = true;
        }else{
            Logger.Log_ln("Player List, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
        }
        Logger.Log_ln("Testing ban, target player: " + testPlayer, Logger.Level.INFO, Logger.Type.SYSTEM);
        testsJSON = new JSONObject(BanPlayer.ban(testPlayer, "test", "1"));
        if(testsJSON.getString("status").equalsIgnoreCase("ok")){
            Logger.Log_ln("Ban Players, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
            booleantest[1] = true;
        }else {
            Logger.Log_ln("Ban players, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.INFO, Logger.Type.SYSTEM);
        }

        for (boolean i: booleantest) {
            if (!(i)) {
                testScore = false;
                break;
            }
        }
        if(testScore){
            Logger.Log_ln("All function looks " + Color.Green + "well" + Color.Reset, Logger.Level.INFO, Logger.Type.SYSTEM);
        }else{
            Logger.Log_ln("Some features need" + Color.Yellow + "improvement" + Color.Reset, Logger.Level.INFO, Logger.Type.SYSTEM);
        }

    }
}
