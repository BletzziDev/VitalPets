package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerConnectionListener implements Listener {
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent e) {
        Terminal.dao.playerSetup(e.getPlayer());
        PlayerData playerData = Terminal.dao.getPlayerData(e.getPlayer());
        PlayerDataMapper.getMapper().put(e.getPlayer().getName(), playerData);
        for(String key : playerData.getActive_pets().values()) {
            playerData.getPet(key).spawn();
        }
    }
    @EventHandler
    public void playerQuitListener(PlayerQuitEvent e) {
        PlayerData playerData = PlayerDataMapper.getMapper().get(e.getPlayer().getName());
        for(Pet pet : playerData.getPets()) {
            pet.remove();
        }
        Terminal.dao.savePlayerData(playerData);
        PlayerDataMapper.getMapper().remove(e.getPlayer().getName());
    }
}