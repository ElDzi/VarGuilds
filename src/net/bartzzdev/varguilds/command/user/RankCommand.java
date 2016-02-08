package net.bartzzdev.varguilds.command.user;

import net.bartzzdev.varguilds.basic.Rank;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.command.util.Executor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RankCommand implements Executor, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        this.call(commandSender, strings);
        return false;
    }

    @Override
    public void call(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(messages.getMessage("commandUsage").replace("{USAGE}", "/rank [gracz]"));
            return;
        }

        String nickname = args[0];
        final User user = User.get(Bukkit.getPlayer(nickname).getUniqueId());

        if (user == null) {
            sender.sendMessage(messages.getMessage("neverPlayed").replace("{PLAYER}", nickname));
            return;
        }

        Rank rank = user.getRank();

        messages.getList("rank").stream().map((msg) -> msg
                .replace("{PLAYER}", user.getName())
                .replace("{RANK}", Integer.toString(rank.getPoints()))
                .replace("{KILLS}", Integer.toString(rank.getKills()))
                .replace("{DEATHS}", Integer.toString(rank.getDeaths())))
                .forEach(sender::sendMessage);
    }
}
