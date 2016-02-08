package net.bartzzdev.varguilds.basic;

import net.bartzzdev.varguilds.manager.RankManager;
import net.bartzzdev.varguilds.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final UUID uniqueId;
    private String name;
    private Rank rank;
    private Guild guild;
    private Tablist tablist;

    public User(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.name = (this.asPlayer() == null ? this.getUniqueId().toString() : this.asPlayer().getName());
        this.rank = new Rank(this);
        this.guild = null;
        this.tablist = new Tablist(this);
        RankManager.update(this);
    }

    public static User get(UUID uniqueId) {
        for (User user : UserManager.getUserList()) {
            if (user.getUniqueId().equals(uniqueId)) {
                return user;
            }
        }
        return null;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public Tablist getTablist() {
        return tablist;
    }

    public void setTablist(Tablist tablist) {
        this.tablist = tablist;
    }

    public Player asPlayer() {
        return Bukkit.getPlayer(this.uniqueId);
    }
}
