package net.bartzzdev.varguilds.store;

import net.bartzzdev.varguilds.data.Settings;

public enum StoreType {

    FLAT,
    MYSQL;

    public static StoreType check() {
        if (Settings.getInstance().storeFlatfile) return FLAT;
        else return MYSQL;
    }
}
