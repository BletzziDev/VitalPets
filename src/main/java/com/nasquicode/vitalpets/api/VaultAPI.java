package com.nasquicode.vitalpets.api;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultAPI {
    public static Economy economy;

    public Economy getEconomy() {
        if(economy != null) return economy;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return null;
        economy = rsp.getProvider();
        return economy;
    }
}