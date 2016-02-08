package net.bartzzdev.varguilds.command.guild;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.command.util.Executor;
import net.bartzzdev.varguilds.manager.RankManager;
import net.bartzzdev.varguilds.util.UtilConfirm;
import net.bartzzdev.varguilds.util.thread.ThreadCaller;
import net.bartzzdev.varguilds.util.thread.ThreadType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfirmCommand implements CommandExecutor, Executor {

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
            sender.sendMessage(messages.getMessage("commandUsage").replace("{USAGE}", "/potwierdz"));
            return;
        }

        final Player player = (Player) sender;
        final User user = User.get(player.getUniqueId());

        if (!UtilConfirm.getConfirmMap().containsKey(user)) {
            sender.sendMessage(messages.getMessage("delete.noConfirm"));
            return;
        }

        final Guild guild = user.getGuild();

        UtilConfirm.getConfirmMap().remove(user);
        sender.sendMessage(messages.getMessage("delete.success").replace("{NAME}", guild.getName()).replace("{TAG}", guild.getTag()));
        Bukkit.broadcastMessage(messages.getMessage("delete.broadcast").replace("{PLAYER}", player.getName()).replace("{NAME}", guild.getName()).replace("{TAG}", guild.getTag()));

        guild.getMembers().forEach(member -> member.setGuild(null));

        RankManager.getGuildList().remove(guild.getRank());

        RankManager.update();

        new ThreadCaller(ThreadType.GUILD_DELETE, guild).run();
        new ThreadCaller(ThreadType.USER_SAVE, user).run();
        new ThreadCaller(ThreadType.TABLIST_UPDATE).run();
    }
}
