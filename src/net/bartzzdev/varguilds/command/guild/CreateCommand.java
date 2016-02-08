package net.bartzzdev.varguilds.command.guild;

import net.bartzzdev.varguilds.basic.Guild;
import net.bartzzdev.varguilds.basic.User;
import net.bartzzdev.varguilds.command.util.Executor;
import net.bartzzdev.varguilds.manager.GuildManager;
import net.bartzzdev.varguilds.manager.RankManager;
import net.bartzzdev.varguilds.manager.RegionManager;
import net.bartzzdev.varguilds.manager.UserManager;
import net.bartzzdev.varguilds.util.UtilLocation;
import net.bartzzdev.varguilds.util.thread.ThreadCaller;
import net.bartzzdev.varguilds.util.thread.ThreadType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements Executor, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        this.call(commandSender, strings);
        return false;
    }

    //zaloz [nazwa] [tag]
    @Override
    public void call(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getMessage("needPlayer"));
            return;
        }

        if (args.length != 2) {
            sender.sendMessage(messages.getMessage("commandUsage").replace("{USAGE}", "/zaloz [nazwa] [tag]"));
            return;
        }

        Player player = (Player) sender;
        User user = User.get(player.getUniqueId());

        if (user.getGuild() != null) {
            sender.sendMessage(messages.getMessage("create.hasGuild"));
            return;
        }

        String name = args[0];
        String tag = args[1].toUpperCase();

        if (GuildManager.tagExists(tag)) {
            sender.sendMessage(messages.getMessage("create.tagExists").replace("{TAG}", tag.toUpperCase()));
            return;
        }

        if (GuildManager.nameExists(name)) {
            sender.sendMessage(messages.getMessage("create.nameExists").replace("{NAME}", name.toUpperCase()));
            return;
        }

        if (tag.length() != settings.guildTagLength || !tag.matches("[a-zA-Z]+")) {
            sender.sendMessage(messages.getMessage("create.invalidTag").replace("{LENGTH}", Integer.toString(settings.guildTagLength)));
            return;
        }

        if (name.length() > settings.guildNameMaxLength || !name.matches("[a-zA-Z]+")) {
            sender.sendMessage(messages.getMessage("create.invalidName").replace("{LENGTH}", Integer.toString(settings.guildNameMaxLength)));
            return;
        }

        Location location = player.getLocation();

        if (RegionManager.isIn(location)) {
            sender.sendMessage(messages.getMessage("create.isIn"));
            return;
        }

        if (RegionManager.isNear(location, settings.guildSize, settings.guildMinDistance)) {
            sender.sendMessage(messages.getMessage("create.isNear").replace("{DISTANCE}", Integer.toString(settings.guildMinDistance)));
            return;
        }

        if (location.distance(player.getWorld().getSpawnLocation()) < settings.guildSpawnDistance) {
            sender.sendMessage(messages.getMessage("create.spawnDistance").replace("{DISTANCE}", Integer.toString(settings.guildSpawnDistance)));
            return;
        }

        if (!UserManager.hasItems(player)) {
            messages.getList("create.noItems").forEach(sender::sendMessage);
            return;
        }

        sender.sendMessage(messages.getMessage("create.success").replace("{NAME}", name).replace("{TAG}", tag));
        Bukkit.broadcastMessage(messages.getMessage("create.broadcast").replace("{PLAYER}", player.getName()).replace("{NAME}", name).replace("{TAG}", tag));

        UtilLocation.createBuilding(location);

        Guild guild = new Guild(name, tag, user, location);
        RankManager.update(guild);
        user.setGuild(guild);

        UserManager.takeItems(player);

        new ThreadCaller(ThreadType.GUILD_SAVE, guild).run();
        new ThreadCaller(ThreadType.USER_SAVE, user).run();
        new ThreadCaller(ThreadType.TABLIST_UPDATE).run();
    }
}
