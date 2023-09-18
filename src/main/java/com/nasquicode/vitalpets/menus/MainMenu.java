package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, Color.string(Terminal.menuFile.getString("main.name")));

        String[] items = {"pet_storage"};
        for(String key : items) {
            ItemStack stack = CustomStack.get(Terminal.menuFile.getString(String.format("main.items.%s.material", key)));
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Color.string(Terminal.menuFile.getString(String.format("main.items.%s.name", key))));
            meta.setLore(Color.list(Terminal.menuFile.getStringList(String.format("main.items.%s.lore", key))));
            stack.setItemMeta(meta);
            inv.setItem(Terminal.menuFile.getInt(String.format("main.items.%s.slot", key)), stack);
        }

        player.openInventory(inv);
    }
}