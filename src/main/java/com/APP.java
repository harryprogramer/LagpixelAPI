package com;

import com.api.LagpixelAPI08;

public class APP {
    LagpixelAPI lagpixelAPI = LagpixelAPI08.getInstance();

    public void closeApp(){
        lagpixelAPI.closeConnectAPI();
        System.exit(0);
    }
}
