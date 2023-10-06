package com.nasquicode.vitalpets.listeners;

import com.edwardbelt.edprison.events.EdPrisonAddMultiplierCurrency;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetPowerListener implements Listener {
    @EventHandler
    public void edpCurrencyBonus(EdPrisonAddMultiplierCurrency e) {
        e.setMultiplier(e.getMultiplier()+PlayerDataMapper.getMapper().get(Bukkit.getPlayer(e.getUUID()).getName()).getMultipliers().get(e.getCurrency()));
        //Console.log(String.format("DEBUG LISTENER -> The multiplier has been setted: %s", String.valueOf(e.getMultiplier())));
    }
}