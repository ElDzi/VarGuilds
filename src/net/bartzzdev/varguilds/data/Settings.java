package net.bartzzdev.varguilds.data;

import net.bartzzdev.varguilds.VarGuilds;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Settings {

    private static Settings settings;
    private FileConfiguration config;

    public Settings() {
        settings = this;
        this.config = VarGuilds.getInstance().getConfig();
        this.load();
    }

    public boolean tablistEnabled;
    public boolean autoSaveEnabled;
    public boolean storeMysql;
    public boolean storeFlatfile;
    public boolean userProtectEnabled;

    public int tablistRefresh;
    public int guildStartLives;
    public int autoSaveTime;
    public int userStartPoints;
    public int guildStartPoints;
    public int guildTagLength;
    public int guildNameMaxLength;
    public int guildSize;
    public int guildMinDistance;
    public int guildSpawnDistance;
    public int guildConfirmTime;

    public long userProtectTime;

    public List<String> guildItems;

    public ConfigurationSection tablistCells;

    private void load() {
        this.tablistEnabled = config.getBoolean("tablist.enabled");
        this.tablistRefresh = config.getInt("tablist.refresh");
        this.tablistCells = config.getConfigurationSection("tablist.cells");
        this.guildStartLives = config.getInt("guild.start-lives");
        this.storeMysql = config.getBoolean("store.mysql;");
        this.storeFlatfile = config.getBoolean("store.flatfile");
        this.autoSaveTime = config.getInt("autosave.time");
        this.autoSaveEnabled = config.getBoolean("autosave.enabled");
        this.userStartPoints = config.getInt("user.start-points");
        this.guildStartPoints = config.getInt("guild.start-points");
        this.guildItems = config.getStringList("guild.items");
        this.guildTagLength = config.getInt("guild.tag-length");
        this.guildNameMaxLength = config.getInt("guild.name-max-length");
        this.guildSize = config.getInt("guild.size");
        this.guildMinDistance = config.getInt("guild.minDistance");
        this.guildSpawnDistance = config.getInt("guild.spawnDistance");
        this.userProtectEnabled = config.getBoolean("user.protect-enabled");
        this.userProtectTime = config.getLong("user.protect-time") * 1000 * 60 * 60;
        this.guildConfirmTime = config.getInt("guild.confirm-time") * 1000;
    }

    public static Settings getInstance() {
        if (settings != null) return settings;
        return new Settings();
    }
}
