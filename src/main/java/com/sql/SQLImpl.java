package com.sql;

import util.Logger;

class SQLImpl implements API {
    SQLConn sqlConn = SQLConn.getInstance();

    @Override
    public void connectToDB() {
    }

    @Override
    public void setDBUrl(String dbUrl) {
        sqlConn.setDbUrl(dbUrl);
    }

    @Override
    public void registrSQL() {
        sqlConn.registrSQL();
    }

    @Override
    public boolean checkDBConn() {
        if (sqlConn != null) {
            return sqlConn.checkDBconn();
        } else {
            Logger.Log_ln("An attempt was made to operate on a sql server without registration", Logger.Level.CRIT, Logger.Type.SYSTEM);
            return false;
        }
    }

    @Override
    public boolean checkPassword(String login, String password) {
        try {
            return sqlConn.checkPassword(login, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
