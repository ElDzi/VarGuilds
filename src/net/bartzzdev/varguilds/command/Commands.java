package net.bartzzdev.varguilds.command;

import net.bartzzdev.varguilds.command.guild.ConfirmCommand;
import net.bartzzdev.varguilds.command.guild.CreateCommand;
import net.bartzzdev.varguilds.command.guild.DeleteCommand;
import net.bartzzdev.varguilds.command.guild.LeaderCommand;
import net.bartzzdev.varguilds.command.user.RankCommand;
import net.bartzzdev.varguilds.command.util.Executor;
import net.bartzzdev.varguilds.command.util.Performer;
import net.bartzzdev.varguilds.data.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Commands {

    private int i;

    public Commands() {
        this.commands();
        Bukkit.getLogger().log(Level.INFO, "Registered " + (this.i) + " commands.");
    }

    private void commands() {
        this.register(new GuildCommand(), "gildia", "guild", Arrays.asList("g", "guild"));
        this.register(new CreateCommand(), "zaloz", "create", Arrays.asList("create"));
        this.register(new DeleteCommand(), "rozwiaz", "delete", Arrays.asList("delete", "usun"));
        this.register(new ConfirmCommand(), "potwierdz", "confirm", Arrays.asList("confirm"));
        this.register(new LeaderCommand(), "lider", "leader", Arrays.asList("leader"));

        this.register(new RankCommand(), "rank", "rank", Arrays.asList("stats", "statystyki", "ranking", "punkty"));
    }

    private void register(Executor executor, String name, String permission, List<String> aliases) {
        try {
            Performer performer = new Performer(name);
            performer.setExecutor(executor);
            if (aliases != null) performer.setAliases(aliases);
            performer.setPermission("varguilds.command." + permission);
            performer.setPermissionMessage(Messages.getInstance().getMessage("permissionMessage"));
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
            commandMap.register("", performer);
            i++;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
