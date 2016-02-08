package net.bartzzdev.varguilds.basic;

import com.mojang.authlib.GameProfile;
import net.bartzzdev.varguilds.data.Settings;
import net.bartzzdev.varguilds.manager.RankManager;
import net.bartzzdev.varguilds.util.UtilDate;
import net.bartzzdev.varguilds.util.UtilString;
import net.bartzzdev.varguilds.util.element.Reflection;
import net.bartzzdev.varguilds.util.element.Reflection.FieldAccessor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.PlayerInfoData;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.*;

public class Tablist {

    private final User user;
    private Map<Integer, GameProfile> profileMap;
    private char[] alphabet;

    private Class<?> playerInfoClass = Reflection.getClass("{nms}.PacketPlayOutPlayerInfo");
    private FieldAccessor<Enum> playerInfoAction = Reflection.getField(playerInfoClass, Enum.class, 0);
    private FieldAccessor<List> playerInfoDataList = Reflection.getField(playerInfoClass, List.class, 0);

    private List<String> values = new ArrayList<>();

    public Tablist(User user) {
        this.user = user;
        this.profileMap = new HashMap<>();
        this.alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'};
        for (int i = 0; i <= 79; i++) {
            this.profileMap.put(i, new GameProfile(UUID.randomUUID(), "$$$$$$$" + this.getCharID(i)));
        }

        values.add("{NAME}");
        values.add("{TAG}");
        values.add("{LEADER}");
        values.add("{DEPUTY}");
        values.add("{SIZE}");
        values.add("{GPOS}");
        values.add("{POINTS}");
        values.add("{LIVES}");
        values.add("{MONEY}");
        values.add("{PVP}");
        values.add("CREATED");

        user.setTablist(this);
    }

    public void send() throws IllegalAccessException, InstantiationException {
        Object playerInfo = playerInfoClass.newInstance();
        this.playerInfoAction.set(playerInfo, EnumPlayerInfoAction.ADD_PLAYER);

        final List<PlayerInfoData> playerInfoDatas = new ArrayList<>();
        final ConfigurationSection section = Settings.getInstance().tablistCells;
        String message;
        for (int i = 1; i < 81; i++) {
            message = section.getString(Integer.toString(i));
            if (message == null) message = "";
            IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + this.replaceVariables(message) + "\"}");
            playerInfoDatas.add((PlayerInfoData) Reflection.getConstructor("{nms}.PacketPlayOutPlayerInfo$PlayerInfoData", Reflection.getClass("{nms}.PacketPlayOutPlayerInfo$PlayerInfoData").getConstructors()[0].getParameterTypes())
            .invoke(null, profileMap.get(i-1), 1500, WorldSettings.EnumGamemode.NOT_SET, comp));
            this.playerInfoDataList.set(playerInfo, playerInfoDatas);
        }

        ((CraftPlayer)user.asPlayer()).getHandle().playerConnection.sendPacket((PacketPlayOutPlayerInfo) playerInfo);
    }

    private String getCharID(final int id) {
        if (id < 20) {
            return "A" + alphabet[id];
        }

        if (id < 40) {
            return "B" + alphabet[id-20];
        }

        if (id < 60) {
            return "C" + alphabet[id-40];
        }

        if (id < 80) {
            return "D" + alphabet[id-60];
        }
        return "";
    }

    /*  # Player: {PLAYER}, {RANK}, {KILLS}, {DEATHS}, {POSITION}
    *   # Guild: {NAME}, {TAG}, {LEADER}, {DEPUTY}, {SIZE}, {GPOS}, {POINTS}, {LIVES}, {MONEY}, {PVP}, {CREATED}
    */
    private String replaceVariables(String s) {
        s = ChatColor.translateAlternateColorCodes('&', s);
        s = UtilString.replace(s, "{PLAYER}", () -> user.getName());
        for (int x = 1; x <= 10; x++) {
            final int i = x;
            s = UtilString.replace(s, "{G" + i + "NAME}", () -> RankManager.getGuildName(i));
            s = UtilString.replace(s, "{G" + i + "TAG}", () -> RankManager.getGuildTag(i));
            s = UtilString.replace(s, "{P" + i + "NAME}", () -> RankManager.getUserName(i));
            s = UtilString.replace(s, "{P" + i + "RANK}", () -> RankManager.getUserPoints(i));
        }

        Rank rank = user.getRank();
        s = UtilString.replace(s, "{PLAYER}", () -> user.getName());
        s = UtilString.replaceWithInt(s, "{RANK}", () -> rank.getPoints());
        s = UtilString.replaceWithInt(s, "{KILLS}", () -> rank.getKills());
        s = UtilString.replaceWithInt(s, "{DEATHS}", () -> rank.getDeaths());
        s = UtilString.replaceWithInt(s, "{POSITION}", () -> RankManager.checkUserPosition(user));

        Guild guild = user.getGuild();
        for (String ss : values) {
            if (guild == null) {
                for (int i = 0; i < values.size(); i++) {
                    s = UtilString.replace(s, values.get(i), () -> "Brak");
                }
            } else {
                s = UtilString.replace(s, "{NAME}", () -> guild.getName());
                s = UtilString.replace(s, "{TAG}", () -> guild.getTag());
                s = UtilString.replace(s, "{LEADER}", () -> guild.getLeader().getName());
                s = UtilString.replace(s, "{DEPUTY}", () -> (guild.getDeputy() == null ? "Brak" : guild.getDeputy().getName()));
                s = UtilString.replace(s, "{SIZE}", () -> Integer.toString(guild.getRegion().getSize()));
                s = UtilString.replace(s, "{GPOS}", () -> Integer.toString(RankManager.checkGuildPosition(guild)));
                s = UtilString.replace(s, "{POINTS}", () -> Integer.toString(guild.getRank().getPoints()));
                s = UtilString.replace(s, "{LIVES}", () -> Integer.toString(guild.getLives()));
                s = UtilString.replace(s, "{PVP}", () -> (guild.isPvp() ? "wlaczone" : "wylaczone"));
                s = UtilString.replace(s, "{CREATED}", () -> UtilDate.convertByDate(guild.getTimeCreated()));
            }
        }
        return s;
    }
}
