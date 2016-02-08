package net.bartzzdev.varguilds.data;

import net.bartzzdev.varguilds.VarGuilds;
import net.bartzzdev.varguilds.util.UtilString;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    private static Messages messages;
    private YamlConfiguration messagesYml;

    public Messages() {
        messages = messages;
        check();
    }

    private void check() {
        File file = new File(VarGuilds.getInstance().getDataFolder(), "messages.yml");
        if (!file.exists()) {
            VarGuilds.getInstance().saveResource("messages.yml", true);
        }

        messagesYml = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(String path) {
        if (messagesYml.getString(path) == null) {
            messagesYml.set(path, "Empty message");
        }

        return UtilString.colors(messagesYml.getString(path));
    }

    public List<String> getList(String path) {
        if (messagesYml.getStringList(path) == null) {
            messagesYml.set(path, new ArrayList<>());
        }

        return UtilString.colors(messagesYml.getStringList(path));
    }

    public static Messages getInstance() {
        if (messages != null) return messages;
        return new Messages();
    }
}
