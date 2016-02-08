package net.bartzzdev.varguilds.util.thread;

import net.bartzzdev.varguilds.VarGuilds;
import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.data.Settings;
import net.bartzzdev.varguilds.manager.GuildManager;
import net.bartzzdev.varguilds.manager.RegionManager;
import net.bartzzdev.varguilds.manager.UserManager;
import net.bartzzdev.varguilds.store.Store;
import net.bartzzdev.varguilds.store.StoreType;
import org.bukkit.Bukkit;

import java.io.IOException;

public class ThreadCaller extends Thread {

    private ThreadType type;
    private Object[] values;

    public ThreadCaller(ThreadType type) {
        this.type = type;
    }

    public ThreadCaller(ThreadType type, Object... values) {
        this.type = type;
        this.values = values;
    }

    @Override
    public void run() {
        switch (type) {
            case TABLIST_SEND:
                try {
                    ((User) values[0]).getTablist().send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TABLIST_UPDATE:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    User user = User.get(player.getUniqueId());
                    try {
                        user.getTablist().send();
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case DATA_AUTOSAVE:
                Bukkit.getScheduler().runTaskTimerAsynchronously(VarGuilds.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        new Store(StoreType.check()).save();
                    }
                }, 20, 20 * Settings.getInstance().autoSaveTime);
                break;
            case GUILD_SAVE:
                try {
                    GuildManager.saveGuild((Guild) values[0]);
                    RegionManager.saveRegion(((Guild) values[0]).getRegion());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case GUILD_DELETE:
                try {
                    GuildManager.deleteGuild((Guild) values[0]);
                    RegionManager.deleteRegion(((Guild) values[0]).getRegion());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case USER_SAVE:
                try {
                    UserManager.saveUser((User) values[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}