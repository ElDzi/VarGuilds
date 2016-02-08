package net.bartzzdev.varguilds.manager;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.Rank;
import net.bartzzdev.varguilds.basic.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankManager {

    private static List<Rank> userList = new ArrayList<>();
    private static List<Rank> guildList = new ArrayList<>();

    public static int checkUserPosition(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).equals(user.getRank())) {
                return i+1;
            }
        }
        return 0;
    }

    public static int checkGuildPosition(Guild guild) {
        for (int i = 0; i < guildList.size(); i++) {
            if (guildList.get(i).equals(guild.getRank())) {
                return i+1;
            }
        }
        return 0;
    }

    public static String getGuildName(int pos) {
        if (pos-1 < guildList.size()) {
            return guildList.get(pos-1).getGuild().getName();
        }
        return "Brak";
    }

    public static String getGuildTag(int pos) {
        if (pos-1 < guildList.size()) {
            return guildList.get(pos-1).getOwnerID();
        }
        return "";
    }

    public static String getUserName(int pos) {
        if (pos-1 < userList.size()) {
            return userList.get(pos-1).getOwnerID();
        }
        return "Brak";
    }

    public static String getUserPoints(int pos) {
        if (pos-1 < userList.size()) {
            return userList.get(pos-1).getUser().getRank().toString();
        }
        return "";
    }

    public static void update(User user) {
        if (userList.contains(user.getRank())) return;
        userList.add(user.getRank());
        Collections.sort(userList);
    }

    public static void update(Guild guild) {
        if (guildList.contains(guild.getRank())) return;
        guildList.add(guild.getRank());
        Collections.sort(guildList);
    }

    public static void update() {
        Collections.sort(guildList);
        Collections.sort(userList);
    }

    public static List<Rank> getGuildList() {
        return guildList;
    }

    public static List<Rank> getUserList() {
        return userList;
    }
}
