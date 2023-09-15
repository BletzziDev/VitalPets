package com.nasquicode.vitalpets.api;

import com.edwardbelt.edprison.EdPrison;
import org.bukkit.Bukkit;

public class EdPrisonAPI {
    private static EdPrison api;
    public static EdPrison getApi() {
        if(api == null) {
            api = (EdPrison) Bukkit.getPluginManager().getPlugin("EdPrison");
            return api;
        }
        return api;
    }
}
