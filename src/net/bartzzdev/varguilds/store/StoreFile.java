package net.bartzzdev.varguilds.store;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.Rank;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.manager.UserManager;
import net.bartzzdev.varguilds.util.UtilFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StoreFile extends Store {

    public StoreFile() {
        super(StoreType.FLAT);
    }

    public void savePlayers() {
        UserManager.getUserList().forEach(user -> {
            File file = new File(user.getUniqueId() + ".yml");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Rank rank = user.getRank();

            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            yaml.set("uuid", user.getUniqueId().toString());
            yaml.set("name", user.getName());
            yaml.set("rank.points", rank.getPoints());
            yaml.set("rank.kills", rank.getKills());
            yaml.set("rank.deaths", rank.getDeaths());
            yaml.set("guild", (user.getGuild() == null ? "null" : user.getGuild().getTag()));
            try {
                yaml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadPlayers() {
        UserManager.getUserList().clear();
        for (File file : UtilFile.getUsers().listFiles()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            User user = new User(UUID.fromString(yaml.getString("uuid")));
            user.setName(yaml.getString("name"));
            user.setGuild((yaml.getString("guild").equals("null") ? null : Guild.get(yaml.getString("guild"))));
            Rank rank = user.getRank();
            rank.setPoints(yaml.getInt("rank.points"));
            rank.setKills(yaml.getInt("rank.kills"));
            rank.setDeaths(yaml.getInt("rank.deaths"));
            UserManager.getUserList().add(user);
        }
    }
}
