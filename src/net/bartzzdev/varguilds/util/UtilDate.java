package net.bartzzdev.varguilds.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDate {

    public static String convertByDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(new Date(time));
    }

    public static String convertByFullDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static String convertByHours(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
