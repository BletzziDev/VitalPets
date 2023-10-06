package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalpets.mappers.StoreMapper;
import com.nasquicode.vitalpets.menus.StoreMenu;
import com.nasquicode.vitalpets.objects.Store;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage().replace("/","").split(" ")[0];
        Store store = StoreMapper.getStoreByCommand(command);
        if(store == null) return;
        e.setCancelled(true);
        StoreMenu.open(e.getPlayer(), store);
    }
}