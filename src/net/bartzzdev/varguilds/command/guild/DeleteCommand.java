package net.bartzzdev.varguilds.command.guild;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.command.util.Executor;
import net.bartzzdev.varguilds.util.UtilConfirm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteCommand implements Executor, CommandExecutor {

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

        if (args.length != 0) {
            sender.sendMessage(messages.getMessage("commandUsage").replace("{USAGE}", "/rozwiaz"));
            return;
        }

        final Player player = (Player) sender;
        final User user = User.get(player.getUniqueId());

        final Guild guild = user.getGuild();

        if (guild == null) {
            sender.sendMessage(messages.getMessage("delete.noGuild"));
            return;
        }

        if (!guild.getLeader().equals(user)) {
            sender.sendMessage(messages.getMessage("delete.notLeader").replace("{LEADER}", guild.getLeader().getName()));
            return;
        }

        messages.getList("delete.confirm").forEach(sender::sendMessage);
        UtilConfirm.getConfirmMap().put(user, System.currentTimeMillis());

        new BukkitRunnable() {
            public void run() {
                UtilConfirm.getConfirmMap().remove(user);
            }
        }.runTaskLaterAsynchronously(plugin, settings.guildConfirmTime);
    }
}
