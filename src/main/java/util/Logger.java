package util;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Properties;

@SuppressWarnings("unused")
public class Logger {
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Logger.class.getName());
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
        debug = debugbool;
        if(!(debug)) {
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.DEBUG);
        }else{
            org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.DEBUG);
        }
    }

    public static boolean getDebug(){
        return debug;
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

        public static void InitLog(){
            //Properties log4jproperty = new Properties();
            //log4jproperty.setProperty("log4j.rootLogger", "INFO, file");
            //log4jproperty.setProperty("log4j.appender.file", "org.apache.log4j.RollingFileAppender");
            //log4jproperty.setProperty("log4j.appender.file.DatePattern","yyyy-MM-dd-HH-mm'.log");
            //log4jproperty.setProperty("log4j.appender.file.MaxFileSize", "10000MB");
            //log4jproperty.setProperty("log4j.appender.file.MaxBackupIndex", "10");
            //log4jproperty.setProperty("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
            //log4jproperty.setProperty("log4j.appender.file.layout.ConversionPattern", "[%t] %-5p %c %x - %m%n");
            PropertyConfigurator.configure("log4j.properties");
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
                typestring = " [  " + Color.Blue + type.toString() + Color.Reset +  "  ]";
                break;
            case PAPER:
                typestring = " [  " + Color.Magenta + type.toString() + Color.Reset + " ]";
                break;
            case SYSTEM:
                typestring = " [ " + Color.Cyan + type.toString() + Color.Reset + " ]";
                break;
            default:
                throw new IllegalArgumentException("Wadliwy typ logowania, util/Logger.java 58 line");
        }

            return "[" + Color.White +  GetTime.getTimeString(GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES,
                    GetTime.TimeFormat.SECONDS) + Color.Reset + "]" +  typestring + " " +  levelstring + " " + msg;

    }

    public static void Log_ln(@NotNull String msg, @NotNull Level level, @NotNull Type type){
        String logmessage = LogFormat(msg, level, type);
        switch (level){
            case INFO:
                log.info(msg);
                break;
            case WARN:
                log.warn(msg);
                break;
            case CRIT:
                log.fatal(msg);
                break;
            case DEBUG:
                log.debug(msg);
                break;
            default:
                throw new IllegalArgumentException("Wadliwy level logowania, util/Logger.java 99 line");
        }
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
