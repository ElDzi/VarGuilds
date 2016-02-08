package net.bartzzdev.varguilds.store;

import net.bartzzdev.varguilds.VarGuilds;
import net.bartzzdev.varguilds.data.Settings;

public class Store {

    private VarGuilds varGuilds;
    private Settings settings;
    private StoreType storeType;

    public Store(StoreType storeType) {
        this.varGuilds = VarGuilds.getInstance();
        this.settings = Settings.getInstance();
        this.storeType = storeType;
    }

    public void load() {
        switch (storeType) {
            case FLAT:
                new StoreFile().loadPlayers();
                break;
            case MYSQL:
                //TODO
        }
    }

    public void save() {
        switch (storeType) {
            case FLAT:
                new StoreFile().savePlayers();
                break;
            case MYSQL:
                //TODO
        }
    }

    public VarGuilds getPlugin() {
        return varGuilds;
    }

    public Settings getSettings() {
        return settings;
    }

    public StoreType getStoreType() {
        return storeType;
    }
}
