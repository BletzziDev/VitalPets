package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent e) {
        Terminal.dao.playerSetup(e.getPlayer());
        PlayerData playerData = Terminal.dao.getPlayerData(e.getPlayer());
        PlayerDataMapper.getMapper().put(e.getPlayer().getName(), playerData);
    }
    @EventHandler
    public void playerQuitListener(PlayerQuitEvent e) {
        Terminal.dao.savePlayerData(PlayerDataMapper.getMapper().get(e.getPlayer().getName()));
        PlayerDataMapper.getMapper().remove(e.getPlayer().getName());
    }
}