package com.api;

import com.LagpixelAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


class LagpixelAPI08Impl implements LagpixelAPI {
    SocketCore socketCore;

    @Override
    public String getPlayerList() {
        return null;
    }

    @Override
    public void connectToAPI() {
        if(Objects.isNull(socketCore)){
            socketCore = new SocketCore();
            socketCore.connectToAPI();
        }
    }

    @Override
    public boolean closeConnectAPI() {
        return false;
    }

    @Override
    public boolean checkAPIConn() {
        return false;
    }

    @Override
    public void setPort(int port) {
        SocketCore.setPort(port);
    }

    @Override
    public void setInetAddress(String inetAddress) {
        SocketCore.setInetAddress(inetAddress);
    }

    @Override
    public boolean testAPIConn() {
        return TestConn.testAPI();
    }

    @Override
    public int getPort() {
        return SocketCore.port;
    }

    @Override
    public String banPlayer(@NotNull String player, @Nullable String reason, int expires) {
        return null;
    }

    @Override
    public String kickPlayer(@NotNull String player, @Nullable String message) {
        return null;
    }

    @Override
    public String whitelistON() {
        return null;
    }

    @Override
    public String whitelistOFF() {
        return null;
    }

    @Override
    public String removeFromWhitelist(@NotNull String player) {
        return null;
    }

    @Override
    public String addToWhitelist(@NotNull String player) {
        return null;
    }

    @Override
    public String BroadcastMessage(@NotNull String message, @NotNull String user) {
        return null;
    }

    @Override
    public String whitelistStatus() {
        return null;
    }

    @Override
    public String whitelistListPlayer() {
        return null;
    }

    @Override
    public String checkIfPlayerWhitelist(@NotNull String player) {
        return null;
    }

    @Override
    public String getInetAddress() {
        return null;
    }


}
