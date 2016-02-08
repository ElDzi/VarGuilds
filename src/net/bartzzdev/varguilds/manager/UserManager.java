package net.bartzzdev.varguilds.manager;

import net.bartzzdev.varguilds.basic.Rank;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.data.Settings;
import net.bartzzdev.varguilds.util.UtilFile;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    public static boolean hasItems(Player player) {
        List<String> items = Settings.getInstance().guildItems;
        for (String s : items) {
            String[] splitted = s.split("@");
            Material material = Material.getMaterial(splitted[0].toUpperCase());
            if (material == null) return false;
            int amount = Integer.parseInt(splitted[1]);
            int vipAmount = amount / 2;
            ItemStack item = new ItemStack(material, 1);
            if (player.hasPermission("varguilds.vip")) {
                if (!player.getInventory().containsAtLeast(item, vipAmount)) return false;
            } else {
                if (!player.getInventory().containsAtLeast(item, amount)) return false;
            }
        }
        return true;
    }

    public static void takeItems(Player player) {
        if (!hasItems(player)) return;
        List<String> items = Settings.getInstance().guildItems;
        for (String s : items) {
            String[] splitted = s.split("@");
            Material material = Material.matchMaterial(splitted[0].toUpperCase());
            if (material == null) return;
            int amount = Integer.parseInt(splitted[1]);
            ItemStack item = new ItemStack(material, amount);
            player.getInventory().remove(item);
        }
    }

    public static void saveUser(User user) throws IOException {
        File file = new File(UtilFile.getUsers(), user.getUniqueId().toString() + ".yml");
        if (!file.exists()) file.createNewFile();

        Rank rank = user.getRank();

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set("uuid", user.getUniqueId().toString());
        yaml.set("name", user.getName());
        yaml.set("rank.points", rank.getPoints());
        yaml.set("rank.kills", rank.getKills());
        yaml.set("rank.deaths", rank.getDeaths());
        yaml.set("guild", (user.getGuild() == null ? "null" : user.getGuild().getTag()));

        yaml.save(file);
    }
}
