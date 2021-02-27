package util;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/*
* JSON date format - yyyy/MM/dd HH:mm:ss
* yyyy - rok
* MM - miesiac
* dd - dni
* HH - godzina
* mm - minuta
* ss - sekundy
* SSS - milisekundy
* */
public class GetTime {

    public enum TimeFormat{
        YEARS,
        MONTHS,
        DAYS,
        HOURS,
        MINUTES,
        SECONDS,
        MILISECONDS,
        VERTICALBAR,
        SLASH
    }

    public static DateTimeFormatter DateTimeFormat(@NotNull TimeFormat... timeFormats){
        StringBuilder dateformatter = new StringBuilder();
        for(TimeFormat timeFormat : timeFormats) {
            switch (timeFormat) {
                case SLASH:
                    dateformatter.append(" / ");
                    break;
                case VERTICALBAR:
                    dateformatter.append(" | ");
                    break;
                case YEARS:
                    dateformatter.append("yyyy-");
                    break;
                case MONTHS:
                    dateformatter.append("MM-");
                    break;
                case DAYS:
                    dateformatter.append("dd_");
                    break;
                case HOURS:
                    dateformatter.append("HH.");
                    break;
                case MINUTES:
                    dateformatter.append("mm.");
                    break;
                case SECONDS:
                    dateformatter.append("ss");
                    break;
                case MILISECONDS:
                    dateformatter.append(",SSS");
                    break;
                default:
                    throw new IllegalStateException("Nieznany format klasy enum TimeFormat util/GetTime.java 50 line");
            }
        }
        return DateTimeFormatter.ofPattern(dateformatter.toString());
    }

    public static DateTimeFormatter getTimeFormat(@NotNull TimeFormat... timeFormats){
        return DateTimeFormat(timeFormats);
    }

    public static String getTimeString(@NotNull TimeFormat... timeFormats){
        LocalDateTime now = LocalDateTime.now();
        return getTimeFormat(timeFormats).format(now);
    }

}
