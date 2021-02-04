package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetTime {
    static DateTimeFormatter dtf;
    static LocalDateTime now;
    static {
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        now = LocalDateTime.now();
    }

    public static String getTime(){
        return dtf.format(now);
    }
}
