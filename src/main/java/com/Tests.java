package com;

import com.api.LagpixelAPI08;
import com.api.PlayerList;
import com.api.ResponseJSON;
import org.json.JSONObject;
import util.Color;
import util.Logger;

public class Tests {
    JSONObject testsJSON;
    protected void testfunction() {
        synchronized (this) {
            boolean[] booleantest = new boolean[12];
            for (int i = 0; i < 11; i++) {
                booleantest[i] = false;
            }

            boolean testScore = true;
            final String testPlayer = "cotusierobimany";
            LagpixelAPI lagpixelAPI = LagpixelAPI08.getInstance();
            Logger.Log_ln("Testing list player...", Logger.Level.INFO, Logger.Type.SYSTEM);

            // List player test
            testsJSON = new JSONObject(PlayerList.getPlayerArray());
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Player List, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[0] = true;
            } else {
                Logger.Log_ln("Player List, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Ban player test
            Logger.Log_ln("Testing ban, target player: " + testPlayer, Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.banPlayer(testPlayer, "test", "1"));
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Ban Players, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[1] = true;
            } else {
                Logger.Log_ln("Ban players, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Kicking player test
            Logger.Log_ln("Testing kick, target player: " + testPlayer, Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.kickPlayer(testPlayer, "test kick", "test"));
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Kick Players, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[2] = true;
            } else {
                Logger.Log_ln("Kick players, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Testing tps result
            Logger.Log_ln("Testing TPS result", Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.getTPS());
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("TPS status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[3] = true;
            } else {
                Logger.Log_ln("TPS value, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }


            // Testing on whitelist
            lagpixelAPI.whitelistOFF();
            try {
                this.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.Log_ln("Testing Whitelist on", Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.whitelistON());
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Whitelist ON test status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[4] = true;
            } else {
                Logger.Log_ln("Whitelist test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Testing off whitelist
            try {
                this.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.Log_ln("Testing Whitelist off", Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.whitelistOFF());
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Whitelist OFF test status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[5] = true;
            } else {
                Logger.Log_ln("Whitelist test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Remove whitelist
            Logger.Log_ln("Testing remove Whitelist, player: " + testPlayer, Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.addToWhitelist(testPlayer));
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Whitelist add test status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[6] = true;
            } else {
                Logger.Log_ln("Whitelist test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Add whitelist
            Logger.Log_ln("Testing add Whitelist, player: " + testPlayer, Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.removeFromWhitelist(testPlayer));
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Whitelist remove test status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[7] = true;
            } else {
                Logger.Log_ln("Whitelist test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Broadcast test
            Logger.Log_ln("Broadcast test", Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.BroadcastMessage("test message", "System"));
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Broadcast status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[8] = true;
            } else {
                Logger.Log_ln("Broadcast test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Whitelist bool test
            Logger.Log_ln("Testing whitelist bool ", Logger.Level.INFO, Logger.Type.SYSTEM);
            lagpixelAPI.whitelistON();
            testsJSON = new JSONObject(lagpixelAPI.whitelistStatus());
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                boolean whitelistbool;
                try {
                    whitelistbool = testsJSON.getJSONObject("body").getBoolean("bool");
                    Logger.Log_ln("Status whitelist: " + whitelistbool, Logger.Level.INFO, Logger.Type.SYSTEM);
                    booleantest[9] = true;
                    Logger.Log_ln("Whitelist bool test status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                } catch (Exception ignored) {
                    Logger.Log_ln("Status whitelist, bool not found", Logger.Level.WARN, Logger.Type.SYSTEM);
                    booleantest[10] = false;
                }
            } else {
                Logger.Log_ln("Whitelist test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Whitelist list
            lagpixelAPI.whitelistON();
            Logger.Log_ln("Whitelist list test", Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.whitelistListPlayer());
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Whitelist list test status, OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                booleantest[10] = true;
            } else {
                Logger.Log_ln("Whitelist list test, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.WARN, Logger.Type.SYSTEM);
            }

            // Whitelist player check whitelist
            Logger.Log_ln("Testing whitelist player check, player: " + testPlayer, Logger.Level.INFO, Logger.Type.SYSTEM);
            testsJSON = new JSONObject(lagpixelAPI.checkIfPlayerWhitelist(testPlayer));
            if (testsJSON.getString("status").equalsIgnoreCase("ok")) {
                Logger.Log_ln("Whitelist player check test status, OK", Logger.Level.WARN, Logger.Type.SYSTEM);
                booleantest[11] = true;
            } else {
                Logger.Log_ln("Whitelist player check error, ERROR, reason: " + testsJSON.getString("reason"), Logger.Level.INFO, Logger.Type.SYSTEM);
            }


            for (boolean i : booleantest) {
                if (!(i)) {
                    testScore = false;
                    break;
                }
            }
            if (testScore) {
                Logger.Log_ln("All function looks " + Color.Green + " well" + Color.Reset, Logger.Level.INFO, Logger.Type.SYSTEM);
            } else {
                Logger.Log_ln("Some features need" + Color.Yellow + " improvement" + Color.Reset, Logger.Level.INFO, Logger.Type.SYSTEM);
            }

        }
    }
}
