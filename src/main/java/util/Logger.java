package util;

import com.Main;
import com.Server;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@SuppressWarnings("unused")
public class Logger {
    static org.apache.log4j.Logger log;
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
        File logDirectory = new File("./log/");
        if(!(logDirectory.isDirectory())){
            try {
                Files.createDirectories(Paths.get("./log/"));
            } catch (IOException ignored) { }
        }
            PatternLayout layout = new PatternLayout();
            String conversionPattern = "[%d{yyyy-MM-dd HH:mm:ss,SSS}] [%-5p] %t: - %m%n";
            layout.setConversionPattern(conversionPattern);

            // creates daily rolling file appender
            DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
            rollingAppender.setFile("./log/" + GetTime.getTimeString(GetTime.TimeFormat.YEARS, GetTime.TimeFormat.MONTHS,
                    GetTime.TimeFormat.DAYS, GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES, GetTime.TimeFormat.SECONDS) + ".log");
            rollingAppender.setDatePattern("'.'yyyy-MM-dd");
            rollingAppender.setLayout(layout);
            rollingAppender.activateOptions();

            // configures the root logger
            org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
            rootLogger.setLevel(org.apache.log4j.Level.DEBUG);
            rootLogger.addAppender(rollingAppender);

            // creates a custom logger and log messages
            log = org.apache.log4j.Logger.getLogger(Logger.class.getName());
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

    public static void createDumpCore(Exception e){
        FileWriter dumpWriter = null;
        String filename = GetTime.getTimeString(GetTime.TimeFormat.YEARS,
                GetTime.TimeFormat.MONTHS, GetTime.TimeFormat.DAYS,
                GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES,
                GetTime.TimeFormat.SECONDS) + "dump.txt";
        StringWriter sw;
        PrintWriter pw;
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                dumpWriter = new FileWriter(myObj);
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                dumpWriter.write("Program start at: " + Server.startTime + "\n");
                dumpWriter.write("Date: " + GetTime.getTimeString(GetTime.TimeFormat.YEARS, GetTime.TimeFormat.MONTHS,
                        GetTime.TimeFormat.DAYS, GetTime.TimeFormat.HOURS, GetTime.TimeFormat.MINUTES,
                        GetTime.TimeFormat.SECONDS, GetTime.TimeFormat.MILISECONDS ) + "\n");
                dumpWriter.write("Exception message: " + e.getMessage() + "\n");
                dumpWriter.write("Stack Trace: " + sw.toString() + "\n");
                dumpWriter.write("System properties: " + System.getProperties().toString() + "\n");
            }
        } catch (IOException ioException) {
            Logger.Log_ln("Failed to create Dump Core " + ioException.getMessage(), Level.CRIT, Type.SYSTEM);
            e.printStackTrace();
        }
        finally {
            if(dumpWriter != null) {
                try {
                    dumpWriter.close();
                } catch (IOException ioException) {
                    Logger.Log_ln(ioException.toString(), Level.CRIT, Type.SYSTEM);
                }
            }
        }

        System.exit(-1);

    }


}
