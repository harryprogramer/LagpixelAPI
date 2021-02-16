package com.sql;

public class SQL {
    private static API instance = null;

    public static synchronized API getInstance(){
        if(instance == null){
            instance = new SQLImpl();
        }
        return instance;
    }

}
