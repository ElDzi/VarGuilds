package net.bartzzdev.varguilds.manager;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.Rank;
import net.bartzzdev.varguilds.util.UtilFile;
import net.bartzzdev.varguilds.util.UtilLocation;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuildManager {

    private static List<Guild> guildList = new ArrayList<>();

    public static List<Guild> getGuildList() {
        return guildList;
    }

    public static boolean nameExists(String name) {
        for (Guild guild : guildList) {
            if (guild.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tagExists(String tag) {
        for (Guild guild : guildList) {
            if (guild.getTag().equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    public static void saveGuild(Guild guild) throws IOException {
        File file = new File(UtilFile.getGuilds(), guild.getTag() + ".yml");
        if (!file.exists()) file.createNewFile();

        Rank rank = guild.getRank();
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set("name", guild.getName());
        yaml.set("tag", guild.getTag());
        yaml.set("leader", guild.getLeader().getUniqueId().toString());
        yaml.set("deputy", (guild.getDeputy() == null ? "null" : guild.getDeputy().getUniqueId().toString()));
        yaml.set("rank.points", rank.getPoints());
        yaml.set("rank.kills", rank.getKills());
        yaml.set("rank.deaths", rank.getDeaths());
        yaml.set("home", UtilLocation.convertLocation(guild.getHome()));
        yaml.set("timeCreated", guild.getTimeCreated());
        yaml.set("members", guild.getMembers().stream().map(user -> user.getUniqueId().toString()).collect(Collectors.toList()));
        yaml.set("invited", guild.getInvited().stream().map(user -> user.getUniqueId().toString()).collect(Collectors.toList()));
        yaml.set("allies", guild.getAllies().stream().map(ally -> ally.getTag()).collect(Collectors.toList()));
        yaml.set("guildsInvited", guild.getGuildsInvited().stream().map(invited -> invited.getTag()).collect(Collectors.toList()));
        yaml.set("enemies", guild.getEnemies().stream().map(enemy -> enemy.getTag()).collect(Collectors.toList()));

        yaml.save(file);
    }

    public static void deleteGuild(Guild guild) throws IOException {
        File file = new File(UtilFile.getGuilds(), guild.getTag() + ".yml");
        if (!file.exists()) return;

        file.delete();

        for (File f : UtilFile.getUsers().listFiles()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            if (yaml.getString("guild").equalsIgnoreCase(guild.getTag())) {
                yaml.set("guild", "null");
                yaml.save(f);
            }
        }

        guildList.remove(guild);
    }
}
