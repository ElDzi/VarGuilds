package net.bartzzdev.varguilds.command;

import net.bartzzdev.varguilds.command.util.Executor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GuildCommand implements Executor, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        this.call(commandSender, strings);
        return false;
    }

    @Override
    public void call(CommandSender sender, String[] args) {
        if (!sender.hasPermission("varguilds.command.guild")) {
            sender.sendMessage(messages.getMessage("permissionMessage"));
            return;
        }

        if (args.length != 0) {
            sender.sendMessage(messages.getMessage("commandUsage").replace("{COMMAND}", "/gildia"));
            return;
        }

        for (String s : messages.getList("commandList")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
    }
}
