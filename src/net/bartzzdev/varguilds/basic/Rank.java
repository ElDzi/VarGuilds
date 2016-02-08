package net.bartzzdev.varguilds.basic;


import net.bartzzdev.varguilds.data.Settings;

public class Rank implements Comparable<Rank> {

    private User user;
    private Guild guild;
    private int points = 0;
    private int kills;
    private int deaths;
    private RankType rankType;

    public Rank(User user) {
        this.user = user;
        this.points = Settings.getInstance().userStartPoints;
        this.rankType = RankType.USER;
    }

    public Rank(Guild guild) {
        this.guild = guild;
        int x = 0;
        for (User user : guild.getMembers()) {
            x =+ user.getRank().getPoints();
        }
        this.points = x / guild.getMembers().size();
        this.rankType = RankType.GUILD;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public RankType getRankType() {
        return rankType;
    }

    public void setRankType(RankType rankType) {
        this.rankType = rankType;
    }

    public String getOwnerID() {
        if (this.rankType.equals(RankType.GUILD)) {
            return this.guild.getTag();
        } else if (this.rankType.equals(RankType.USER)) {
            return this.user.getName();
        }
        return "";
    }

    @Override
    public String toString() {
        return Integer.toString(this.getPoints());
    }

    @Override
    public int compareTo(Rank o) {
        int i = Integer.compare(o.getPoints(), this.getPoints());
        if (i == 0) i = o.getOwnerID().compareTo(this.getOwnerID());
        return i;
    }

    private enum RankType {

        USER,
        GUILD;
    }
}
