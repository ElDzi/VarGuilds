package net.bartzzdev.varguilds.manager;

import net.bartzzdev.varguilds.basic.Region;
import net.bartzzdev.varguilds.util.UtilFile;
import net.bartzzdev.varguilds.util.UtilLocation;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionManager {

    private static List<Region> regions = new ArrayList<Region>();

    public static List<Region> getRegions(){
        return new ArrayList<Region>(regions);
    }

    public static void addRegion(Region r){
        if(!regions.contains(r)) regions.add(r);
    }

    public static void removeRegion(Region r){
        if(regions.contains(r)) regions.remove(r);
    }

    public static boolean isIn(Location l){
        for(Region r : getRegions()){
            if(r.isIn(l)) return true;
        }
        return false;
    }

    public static Region inWhich(Location l){
        for(Region r : getRegions()){
            if(r.isIn(l)) return r;
        }
        return null;
    }

    public static boolean isNear(Location center, int size, int between){
        for(Region r : getRegions()){
            double dist = center.distance(r.getCenter());
            if(dist < 2*size + between) return true;
        }
        return false;
    }

    public static void saveRegion(Region region) throws IOException {
        File file = new File(UtilFile.getRegions(), region.getName() + ".yml");

        if (!file.exists()) file.createNewFile();

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set("name", region.getName());
        yaml.set("guild", region.getGuild().getName());
        yaml.set("center", UtilLocation.convertLocation(region.getCenter()));
        yaml.set("lowerLoc", UtilLocation.convertLocation(region.getLowerLoc()));
        yaml.set("upperLoc", UtilLocation.convertLocation(region.getUpperLoc()));

        yaml.save(file);
    }

    public static void deleteRegion(Region region) throws IOException {
        File file = new File(UtilFile.getRegions(), region.getName() + ".yml");
        if (!file.exists()) return;
        file.delete();
        regions.remove(region);
    }
}
