package net.bartzzdev.varguilds.util;

import net.bartzzdev.varguilds.VarGuilds;

import java.io.File;

public class UtilFile {

    private static File dataFolder = VarGuilds.getInstance().getDataFolder();

    private static File users = new File(dataFolder, "users");
    private static File guilds = new File(dataFolder, "guilds");
    private static File regions = new File(dataFolder, "regions");

    public static void check() {
        if (!users.exists()) {
            users.mkdir();
        }

        if (!guilds.exists()) {
            guilds.mkdir();
        }

        if (!regions.exists()) {
            regions.mkdir();
        }
    }

    public static File getUsers() {
        return users;
    }

    public static File getGuilds() {
        return guilds;
    }

    public static File getRegions() {
        return regions;
    }
}
