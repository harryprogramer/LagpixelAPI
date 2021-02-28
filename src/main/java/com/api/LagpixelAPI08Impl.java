package com.api;

import com.LagpixelAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Logger;

import java.util.Objects;


class LagpixelAPI08Impl implements LagpixelAPI {
    SocketCore socketCore;

    @Override
    public String getPlayerList() {
        return PlayerList.getPlayerArray();
    }

    @Override
    public boolean checkAPIConn() {
        if(Objects.isNull(socketCore)){
            return false;
        }else{
            return socketCore.getConnBoolean();
        }
    }

    @Override
    public void connectToAPI() {
        if(Objects.isNull(socketCore)){
            socketCore = new SocketCore();
            if(!(socketCore.getConnBoolean())) {
                socketCore.connectToAPI();
            }
        }else{
            if(!(socketCore.getConnBoolean()))
                socketCore.connectToAPI();
            else
                Logger.Log_ln("Połączenie z API już istnieje", Logger.Level.WARN, Logger.Type.SYSTEM);
        }
    }

    @Override
    public void closeConnectAPI() {
        if(socketCore != null) {
            socketCore.closeConn();
        }
    }

    @Override
    public void setPort(int port) {
        SocketCore.setPort(port);
        Logger.Log_ln("Port is now: " + SocketCore.getPort(), Logger.Level.INFO, Logger.Type.SYSTEM);
    }

    @Override
    public void setInetAddress(String inetAddress) {
        SocketCore.setInetAddress(inetAddress);
        Logger.Log_ln("IP address is now: " + SocketCore.getHost(), Logger.Level.INFO, Logger.Type.SYSTEM);
    }

    @Override
    public boolean testAPIConn() {
        return TestConn.testAPI();
    }

    @Override
    public int getPort() {
        if(!(Objects.isNull(socketCore))) {
            return SocketCore.getPort();
        }else{
            Logger.Log_ln("Attempted to get an port without creating a server instance", Logger.Level.CRIT, Logger.Type.SYSTEM);
            return 0;
        }
    }

    @Override
    public String banPlayer(@NotNull String player, @Nullable String reason, String expires) {
        return BanPlayer.ban(player, reason, expires);
    }

    @Override
    public String kickPlayer(@NotNull String player, @Nullable String message, @NotNull String fromUser) {
        return KickPlayer.kick(player, message, fromUser);
    }

    @Override
    public String whitelistON() {
        return Whitelist.whitelistON();
    }

    @Override
    public String whitelistOFF() {
        return Whitelist.whitelistOFF();
    }

    @Override
    public String removeFromWhitelist(@NotNull String player) {
        return Whitelist.removeFromWhitelist(player);
    }

    @Override
    public String addToWhitelist(@NotNull String player) {
        return Whitelist.addWhitelist(player);
    }

    @Override
    public String BroadcastMessage(@NotNull String message, @NotNull String user) {
        return Broadcast.sayToServer(user, message);
    }

    @Override
    public String whitelistStatus() {
        return Whitelist.whitelistBool();
    }

    @Override
    public String getTPS() {
        return GetTPS.getTPS();
    }

    @Override
    public String whitelistListPlayer() {
        return Whitelist.whitelistList();
    }

    @Override
    public String checkIfPlayerWhitelist(@NotNull String player) {
        return Whitelist.checkPlayerWhitelist(player);
    }

    @Override
    public String getInetAddress() {
        if(!(Objects.isNull(socketCore))) {
            return SocketCore.getHost();
        }else{
            Logger.Log_ln("Tried to get ip without earlier setting", Logger.Level.WARN, Logger.Type.SYSTEM);
            return null;
        }
    }


}
