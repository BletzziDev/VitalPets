package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent e) {
        PlayerDataMapper.getPlayerData().put(
                e.getPlayer().getName(),
                new PlayerData(
                        e.getPlayer()
                )
        );
    }
    @EventHandler
    public void playerQuitListener(PlayerQuitEvent e) {
        PlayerDataMapper.getPlayerData().remove(e.getPlayer().getName());
    }
}