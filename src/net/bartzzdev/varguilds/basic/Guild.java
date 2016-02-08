package net.bartzzdev.varguilds.basic;

import net.bartzzdev.varguilds.data.Settings;
import net.bartzzdev.varguilds.manager.GuildManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Guild {

    private String name;
    private String tag;
    private User leader;
    private User deputy;
    private Rank rank;
    private Region region;
    private Location home;
    private int lives;
    private boolean pvp;
    private long timeCreated;
    private boolean changed;

    private final List<User> members = new ArrayList<>();
    private final List<User> invited = new ArrayList<>();

    private final List<Guild> allies = new ArrayList<>();
    private final List<Guild> guildsInvited = new ArrayList<>();
    private final List<Guild> enemies = new ArrayList<>();

    public Guild(String name, String tag, User leader, Location home) {
        this.members.add(leader);
        this.name = name;
        this.tag = tag;
        this.leader = leader;
        this.deputy = null;
        this.rank = new Rank(this);
        this.region = new Region(this, home, Settings.getInstance().guildSize);
        this.home = home;
        this.lives = Settings.getInstance().guildStartLives;
        this.pvp = false;
        this.timeCreated = System.currentTimeMillis();
        this.changed = false;
    }

    public static Guild get(String tag) {
        for (Guild guild : GuildManager.getGuildList()) {
            if (guild.getTag().equalsIgnoreCase(tag)) {
                return guild;
            }
        }
        return null;
    }

    public static Guild byName(String name) {
        for (Guild guild : GuildManager.getGuildList()) {
            if (guild.getName().equalsIgnoreCase(name)) {
                return guild;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public User getLeader() {
        return leader;
    }

    public User getDeputy() {
        return deputy;
    }

    public Rank getRank() {
        return rank;
    }

    public Region getRegion() {
        return region;
    }

    public Location getHome() {
        return home;
    }

    public int getLives() {
        return lives;
    }

    public boolean isPvp() {
        return pvp;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public boolean isChanged() {
        return changed;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<User> getInvited() {
        return invited;
    }

    public List<Guild> getAllies() {
        return allies;
    }

    public List<Guild> getGuildsInvited() {
        return guildsInvited;
    }

    public List<Guild> getEnemies() {
        return enemies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public void setDeputy(User deputy) {
        this.deputy = deputy;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
