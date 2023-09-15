package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalpets.Terminal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenu {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, Terminal.menuFile.getString("main.name"));

        player.openInventory(inv);
    }
}
