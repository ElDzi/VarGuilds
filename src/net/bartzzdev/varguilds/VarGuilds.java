package net.bartzzdev.varguilds;

import net.bartzzdev.varguilds.command.Commands;
import net.bartzzdev.varguilds.data.Messages;
import net.bartzzdev.varguilds.data.Settings;
import net.bartzzdev.varguilds.listener.PlayerJoin;
import net.bartzzdev.varguilds.store.Store;
import net.bartzzdev.varguilds.store.StoreType;
import net.bartzzdev.varguilds.util.UtilFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class VarGuilds extends JavaPlugin {

    private static VarGuilds varGuilds;

    public VarGuilds() {
        varGuilds = this;
    }

    public void onEnable() {
        this.saveDefaultConfig();

        new Settings();
        new Messages();
        new Commands();

        UtilFile.check();

        this.register();

        new Store(StoreType.check()).load();

        this.getLogger().log(Level.INFO, "~ Created by © bartzzdev, 2016. ~");
    }

    public void onDisable() {
        new Store(StoreType.check()).save();
    }

    public static VarGuilds getInstance() {
        if (varGuilds != null) return varGuilds;
        return new VarGuilds();
    }

    private void register() {
        new PlayerJoin(this);
    }
}
