package net.bartzzdev.varguilds.command.util;

import net.bartzzdev.varguilds.VarGuilds;
import net.bartzzdev.varguilds.data.Messages;
import net.bartzzdev.varguilds.data.Settings;
import org.bukkit.command.CommandSender;

public interface Executor {

    final VarGuilds plugin = VarGuilds.getInstance();
    final Messages messages = Messages.getInstance();
    final Settings settings = Settings.getInstance();

    void call(CommandSender sender, String[] args);
}
