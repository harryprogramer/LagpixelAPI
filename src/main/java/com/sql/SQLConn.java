package com.sql;

import util.Color;
import util.Logger;

import java.sql.*;

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
                return conn != null;
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

    public boolean checkPassword(String login, String password) throws Exception {
        if(checkDBconn()){
            String sql = "select password from Users where login = '" + login + "';";
            PreparedStatement stm = conn.prepareStatement(sql);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs != null && rs.next()) {
                    return rs.getString(1).equals(password);
                }
                return false;
            } catch (Exception ex){
                Logger.Log_ln("Something from while getting value from database, cause: [" + Color.Red + ex.getMessage() + Color.Reset + "]", Logger.Level.WARN, Logger.Type.SYSTEM);
                return false;
            }
        }else{
            Logger.Log_ln("Trying to get value from database, but connection with database is lost", Logger.Level.INFO, Logger.Type.SYSTEM);
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
