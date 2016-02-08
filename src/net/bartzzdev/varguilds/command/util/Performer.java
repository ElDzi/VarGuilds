package net.bartzzdev.varguilds.command.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Performer extends Command {

    private String name;
    private Executor executor;

    public Performer(String name) {
        super(name);
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        this.executor.call(commandSender, strings);
        return false;
    }
}
