package com;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface LagpixelAPI {

    String getPlayerList();

    String banPlayer(@NotNull String player, @Nullable String reason, String expires);

    String kickPlayer(@NotNull String player, @Nullable String message, @NotNull String fromUser);

    String whitelistON();

    String whitelistOFF();

    String removeFromWhitelist(@NotNull String player);

    String addToWhitelist(@NotNull String player);

    String BroadcastMessage(@NotNull String message, @NotNull String user);

    String whitelistStatus();

    String whitelistListPlayer();

    String checkIfPlayerWhitelist(@NotNull String player);

    String getInetAddress();

    String getTPS();

    boolean testAPIConn();

    boolean checkAPIConn();

    void connectToAPI();

    void setInetAddress(String inetAddress);

    void closeConnectAPI();

    void setPort(int port);

    int getPort();

}
