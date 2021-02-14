package com;


import com.api.LagpixelAPI08;
import util.Logger;

import java.util.Scanner;

public class Console extends Thread{
    Scanner scanner = new Scanner(System.in);
    LagpixelAPI lagPixelApi = LagpixelAPI08.getInstance();
    @Override
    public void run() {
        while(true){
            String arg = scanner.nextLine().trim().toLowerCase();
            if(arg.equals("testconn")){
                if(lagPixelApi.testAPIConn())
                    Logger.Log_ln("Connection OK", Logger.Level.INFO, Logger.Type.SYSTEM);
                else
                    Logger.Log_ln("Connetion Failed", Logger.Level.CRIT, Logger.Type.SYSTEM);
            }else{
                Logger.Log_ln("Unknown command", Logger.Level.CRIT, Logger.Type.SYSTEM);
            }
        }
    }
}
