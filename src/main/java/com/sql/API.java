package com.sql;

public interface API {
    void connectToDB();

    void setDBUrl(String dbUrl);

    void registrSQL();

    boolean checkDBConn();
}
