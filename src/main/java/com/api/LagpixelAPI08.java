package com.api;

import com.LagpixelAPI;

public class LagpixelAPI08 {

    private static LagpixelAPI instance = null;

    public static synchronized LagpixelAPI getInstance(){
        if(instance == null){
            instance = new LagpixelAPI08Impl();
        }
        return instance;
    }

}
