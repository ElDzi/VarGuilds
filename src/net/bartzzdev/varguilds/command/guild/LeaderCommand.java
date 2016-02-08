package net.bartzzdev.varguilds.command.guild;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.command.util.Executor;
import net.bartzzdev.varguilds.util.thread.ThreadCaller;
import net.bartzzdev.varguilds.util.thread.ThreadType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaderCommand implements Executor, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        this.call(commandSender, strings);
        return false;
    }

    @Override
    public void call(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getMessage("needPlayer"));
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(messages.getMessage("commandUsage").replace("{USAGE}", "/lider [nazwa]"));
            return;
        }

        String arg = args[0];

        final Player player = (Player) sender;
        final User user = User.get(player.getUniqueId());
        final Guild guild = user.getGuild();

        if (guild == null) {
            sender.sendMessage(messages.getMessage("leader.noGuild"));
            return;
        }

        final User other = User.get(Bukkit.getPlayer(arg).getUniqueId());
        if (other == null) {
            sender.sendMessage(messages.getMessage("neverPlayed").replace("{PLAYER}", arg));
            return;
        }

        if (!guild.getLeader().equals(user)) {
            sender.sendMessage(messages.getMessage("leader.notLeader").replace("{LEADER}", guild.getLeader().getName()));
            return;
        }

        if (!guild.getMembers().contains(other)) {
            sender.sendMessage(messages.getMessage("leader.notInGuild").replace("{PLAYER}", other.getName()));
            return;
        }

        sender.sendMessage(messages.getMessage("success").replace("{PLAYER}", other.getName()));
        Bukkit.broadcastMessage(messages.getMessage("broadcast").
                replace("{PLAYER}", user.getName()).
                replace("{SECOND}", other.getName()).
                replace("{NAME}", guild.getName()).
                replace("{TAG}", guild.getTag()));

        guild.setLeader(other);

        new ThreadCaller(ThreadType.GUILD_SAVE, guild).run();
        new ThreadCaller(ThreadType.TABLIST_UPDATE).run();
    }
}
