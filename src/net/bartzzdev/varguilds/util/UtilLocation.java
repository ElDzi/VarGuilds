package net.bartzzdev.varguilds.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class UtilLocation {

    public static String convertLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }

    public static Location convertString(String location) {
        String[] splitted = location.split(";");
        return new Location(Bukkit.getWorld(splitted[0]), Double.valueOf(splitted[1]), Double.valueOf(splitted[2]), Double.valueOf(splitted[3]));
    }

    public static void createBuilding(Location location) {
        location.subtract(0, 1, 0).getBlock().setType(Material.BEDROCK);
    }
}
