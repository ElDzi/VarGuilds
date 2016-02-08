package net.bartzzdev.varguilds.listener;

import net.bartzzdev.varguilds.VarGuilds;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.manager.UserManager;
import net.bartzzdev.varguilds.util.thread.ThreadCaller;
import net.bartzzdev.varguilds.util.thread.ThreadType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoin implements Listener {

    public PlayerJoin(VarGuilds varGuilds) {
        varGuilds = VarGuilds.getInstance();
        Bukkit.getPluginManager().registerEvents(this, varGuilds);
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        User user = User.get(event.getUniqueId());

        if (user == null) {
            user = new User(event.getUniqueId());
            user.setName(event.getName());
            UserManager.getUserList().add(user);
            final User finalUser = user;
            Bukkit.getScheduler().runTaskAsynchronously(VarGuilds.getInstance(), new Runnable() {
               public void run() {
                   try {
                       UserManager.saveUser(finalUser);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            });

            new ThreadCaller(ThreadType.TABLIST_UPDATE);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        User user = User.get(event.getPlayer().getUniqueId());
        new ThreadCaller(ThreadType.TABLIST_SEND, user).run();
    }
}
