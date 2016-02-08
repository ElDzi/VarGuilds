package net.bartzzdev.varguilds.util;

import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.data.Settings;

import java.util.HashMap;
import java.util.Map;

public class UtilConfirm {

    private static Map<User, Long> confirmMap = new HashMap<>();

    public static Map<User, Long> getConfirmMap() {
        return confirmMap;
    }

    public static long getStartedTime(User user) {
        return confirmMap.get(user);
    }

    public static long getEndTime(User user) {
        long l = confirmMap.get(user) + Settings.getInstance().guildConfirmTime;
        return l;
    }
}
