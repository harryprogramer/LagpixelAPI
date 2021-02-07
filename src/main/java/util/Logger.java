package util;

import org.jetbrains.annotations.NotNull;

import static org.jetbrains.annotations.NotNull.*;

@SuppressWarnings("unused")
public class Logger {

    //TODO: Obsluga zapisu plikow, dokonczenie kolorow i petl switch


    /*
    * Colors:
    * [INFO] - green
    * [WARN] - yellow
    * [CRIT] - red
    * [DEBUG] - orange
    * [PAPER] - magenta
    * [HTTP] - blue
    * [SYSTEM] - cyan
    * */

    private static boolean debug = false;


    public static void setDebug(boolean debugbool){
        if(debugbool){
            debug = true;
        }
    }

    private enum Level{
        INFO,
        WARN,
        CRIT,
        DEBUG,
        HTTP,
    }

    private enum Type{
        PAPER,
        SYSTEM,
        HTTP
    }


        public static void Log(@NotNull String msg, @NotNull Level level, @NotNull Type type){
        String levelstring, typestring;
        switch(level){
            case INFO:
                levelstring = "[" + Color.Green + level.toString() + Color.Reset + "]";
                break;
            case WARN:
                levelstring = "[" + Color.Yellow + level.toString() + Color.Reset + "]";
                break;
            case CRIT:
                levelstring = "[" + Color.Red + level.toString() + Color.Reset + "]";
                break;
            case DEBUG:
                levelstring = "[" + Color.Orange + level.toString() + Color.Reset + "]";
                break;
            default:
                throw new IllegalArgumentException("Wadliwy level logowania, util/Logger.java 58 line");
        }

        switch(type){
            case HTTP:
                typestring = " [" + Color.Blue + type.toString() + Color.Reset +  "]";
                break;
            case PAPER:
                typestring = " [" + Color.Magenta + type.toString() + Color.Reset + "]";
                break;
            case SYSTEM:
                typestring = " [" + Color.Cyan + type.toString() + Color.Reset + "]";
                break;
            default:
                throw new IllegalArgumentException("Wadliwy typ logowania, util/Logger.java 58 line");
        }

        String logmessage = null;

        if(level == Level.DEBUG){
           if(debug){
               System.out.println(logmessage);
           }
       }else{
           System.out.println(logmessage);
       }
    }

}
