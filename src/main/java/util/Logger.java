package util;

import org.jetbrains.annotations.NotNull;

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

    public enum Level{
        INFO,
        WARN,
        CRIT,
        DEBUG,
        HTTP,
    }

    public enum Type{
        PAPER,
        SYSTEM,
        HTTP
    }


        private static String LogFormat(@NotNull String msg, @NotNull Level level, @NotNull Type type){
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

            return "[" + Color.White +  GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES,
                    GetTime.TimeFormat.SECONDS) + Color.Reset + "]" +  typestring + " " +  levelstring + " " + msg;

    }

    public static void Log_ln(@NotNull String msg, @NotNull Level level, @NotNull Type type){
        String logmessage = LogFormat(msg, level, type);
        if(level == Level.DEBUG){
            if(debug){
                System.out.println(logmessage);
            }
        }else{
            System.out.println(logmessage);
        }
    }

    public static void Log_nt(@NotNull String msg, @NotNull Level level, @NotNull Type type){
        String logmessage = LogFormat(msg, level, type);
        if(level == Level.DEBUG){
            if(debug){
                System.out.print("\r" + logmessage + "\r");
                System.out.flush();
            }
        }else{
            System.out.print(logmessage);
        }
    }


}
