package com.sql;

import util.Color;
import util.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConn {
    private boolean registrSQL = false;
    public String dbUrl = null;
    static SQLConn instance;
    Connection conn;

    public static SQLConn getInstance(){
        if(instance == null) instance = new SQLConn();
        return instance;
    }
    protected boolean checkDBconn(){
        if(registrSQL) {
            if(!(dbUrl == null)) {
                try {
                    conn = DriverManager.getConnection(dbUrl);
                } catch (SQLException throwable) {
                    Logger.Log_ln("Something wrong while trying to connect to database, cause: [" +
                                    Color.Red + throwable.getMessage() + Color.Reset + "]"
                            , Logger.Level.INFO, Logger.Type.SYSTEM);
                    return false;
                }
                if (conn != null) {
                    Logger.Log_ln("SQL connect succesful", Logger.Level.INFO, Logger.Type.SYSTEM);
                    return true;
                } else {
                    Logger.Log_ln("SQL connect unsuccessful", Logger.Level.INFO, Logger.Type.SYSTEM);
                    return false;
                }
            }else{
                Logger.Log_ln("Trying to test SQL Server without set dbUrl", Logger.Level.CRIT, Logger.Type.SYSTEM);
                return false;
            }
        }else{
            Logger.Log_ln("An attempt was made to operate on the database without registration"
                    , Logger.Level.CRIT, Logger.Type.SYSTEM);
            return false;
        }
    }

    protected void setDbUrl(String url){
        this.dbUrl = url;
    }

    protected void registrSQL(){
        if(registrSQL) {
            Logger.Log_ln("The sql database has already been registered", Logger.Level.WARN, Logger.Type.SYSTEM);
        }else {
            try {
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                registrSQL = true;
            } catch (SQLException throwables) {
                Logger.Log_ln("Something wrong while trying to registr SQL Database, cause: [" +
                        Color.Red + throwables.getMessage() + Color.Reset + "]", Logger.Level.INFO,
                        Logger.Type.SYSTEM);
            }
        }
    }
}
