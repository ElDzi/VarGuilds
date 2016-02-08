package net.bartzzdev.varguilds.util;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UtilString {

    public static String colors(String text) {
        if (text.isEmpty()) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> colors(List<String> array) {
        List<String> stringList = new ArrayList<>();
        for (String s : array) stringList.add(ChatColor.translateAlternateColorCodes('&', s));
        return stringList;
    }

    public static String replace(String inWhat, String toReplace, Supplier<String> newText) {
        inWhat = StringUtils.replace(inWhat, toReplace, newText.get());
        return inWhat;
    }

    public static String replaceWithInt(String inWhat, String toReplace, Supplier<Integer> newText) {
        inWhat = StringUtils.replace(inWhat, toReplace, Integer.toString(newText.get()));
        return inWhat;
    }
}
